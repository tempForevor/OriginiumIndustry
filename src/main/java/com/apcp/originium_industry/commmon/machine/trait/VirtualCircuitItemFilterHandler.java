package com.apcp.originium_industry.commmon.machine.trait;

import com.apcp.originium_industry.commmon.item.VirtualItemBehavior;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class VirtualCircuitItemFilterHandler extends NotifiableItemStackHandler{
    public VirtualCircuitItemFilterHandler(MetaMachine machine) {
        super(machine,1, IO.IN,IO.IN,size->new ItemStackHandler(size,machine));
    }

    @Override
    public @NotNull List<Object> getContents() {
        return List.of(storage.getStackInSlot(0));
    }

    @Override
    public double getTotalContentAmount() {
        return storage.getStackInSlot(0).getCount();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return storage.getStackInSlot(slot);
    }

    public static class ItemStackHandler extends CustomItemStackHandler{
        public MetaMachine machine;

        public ItemStackHandler(int size,MetaMachine machine){
            super(size);
            this.machine = machine;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            return super.extractItem(slot, amount, true);
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if(VirtualItemBehavior.isCircuitVirtualItem(stack)){
                var inner = VirtualItemBehavior.getVirtualItemStack(stack);
                setStackInSlot(slot, inner);
                return ItemStack.EMPTY;
            }
            return stack;
        }
    }
}
