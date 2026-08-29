package com.apcp.originium_industry.commmon.machine.trait;

import com.apcp.originium_industry.commmon.item.VirtualItemBehavior;
import com.apcp.originium_industry.commmon.machine.part.SuperMEPatternBufferPart;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerGroupDistinctness;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public final class InternalSlotRecipeHandler {

    @Getter
    public final List<RecipeHandlerList> slotHandlers;

    public InternalSlotRecipeHandler(SuperMEPatternBufferPart buffer, SuperMEPatternBufferPart.InternalSlot[] slots) {
        this.slotHandlers = new ArrayList<>(slots.length);
        for (int i = 0; i < slots.length; i++) {
            var rhl = new SlotRHL(buffer, slots[i], i);
            slotHandlers.add(rhl);
        }
    }

    @Getter
    public static class SlotRHL extends RecipeHandlerList {

        private final SlotItemRecipeHandler itemRecipeHandler;
        private final SlotFluidRecipeHandler fluidRecipeHandler;

        public SlotRHL(SuperMEPatternBufferPart buffer, SuperMEPatternBufferPart.InternalSlot slot, int idx) {
            super(IO.IN);
            itemRecipeHandler = new SlotItemRecipeHandler(buffer, slot, idx);
            fluidRecipeHandler = new SlotFluidRecipeHandler(buffer, slot, idx);
            addHandlers(buffer.getCircuitInventory(), buffer.getShareInventory(), buffer.getShareTank(),
                    itemRecipeHandler, fluidRecipeHandler);
            this.setGroup(RecipeHandlerGroupDistinctness.BUS_DISTINCT);
        }

        @Override
        public boolean isDistinct() {
            return true;
        }

        @Override
        public void setDistinct(boolean ignored, boolean notify) {
        }
    }

    @SuppressWarnings({"NullableProblems"})
    @Getter
    public static class SlotFluidRecipeHandler extends NotifiableRecipeHandlerTrait<FluidIngredient> {

        private final SuperMEPatternBufferPart.InternalSlot slot;
        private final int priority;

        private final int size = 81;
        private final RecipeCapability<FluidIngredient> capability = FluidRecipeCapability.CAP;
        private final IO handlerIO = IO.IN;
        private final boolean isDistinct = true;

        private SlotFluidRecipeHandler(SuperMEPatternBufferPart buffer, SuperMEPatternBufferPart.InternalSlot slot, int index) {
            super(buffer);
            this.slot = slot;
            this.priority = IFilteredHandler.HIGH + index + 1;
            slot.setOnContentsChanged(this::notifyListeners);
        }

        @Override
        public List<FluidIngredient> handleRecipeInner(IO io, GTRecipe recipe, List<FluidIngredient> left,
                                                       boolean simulate) {
            if (io != IO.IN || slot.isFluidEmpty()) return left;
            return (slot.handleFluidInternal(left, simulate));
        }

        @Override
        public List<Object> getContents() {
            return new ArrayList<>(slot.getFluids());
        }

        @Override
        public double getTotalContentAmount() {
            return slot.getFluids().stream().mapToLong(FluidStack::getAmount).sum();
        }
    }

    @SuppressWarnings({"NullableProblems"})
    @Getter
    public static class SlotItemRecipeHandler extends NotifiableRecipeHandlerTrait<Ingredient> {

        private final SuperMEPatternBufferPart.InternalSlot slot;
        private final int priority;

        private final int size = 81;
        private final RecipeCapability<Ingredient> capability = ItemRecipeCapability.CAP;
        private final IO handlerIO = IO.IN;
        private final boolean isDistinct = true;

        private SlotItemRecipeHandler(SuperMEPatternBufferPart buffer, SuperMEPatternBufferPart.InternalSlot slot, int index) {
            super(buffer);
            this.slot = slot;
            this.priority = IFilteredHandler.HIGH + index + 1;
            slot.setOnContentsChanged(this::notifyListeners);
        }


        @Override
        public List<Ingredient> handleRecipeInner(IO io, GTRecipe recipe, List<Ingredient> left, boolean simulate) {
            if (io != IO.IN || slot.isItemEmpty()) return left;
            var realLeft = VirtualItemBehavior.patchIngredientsVirtualItems(left,recipe);
            if(!simulate){
                for(var patches : realLeft.rawPatches()){
                    slot.pushRemovingVirtualItem(patches.virtualItems());
                }
            }
//            var realLeft = left;
            return (slot.handleItemInternal(realLeft.modified(), simulate));
        }

        @Override
        public List<Object> getContents() {
            var res = new ArrayList<>();
            for(var itemStack : slot.getItems()) {
                res.add(itemStack);
                if(VirtualItemBehavior.isVirtualItem(itemStack)){
                    res.add(VirtualItemBehavior.getVirtualItemStack(itemStack));
                }
            }
            return res;
        }

        @Override
        public double getTotalContentAmount() {
            return slot.getItems().stream().mapToLong(ItemStack::getCount).sum();
        }
    }

}
