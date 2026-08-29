package com.apcp.originium_industry.commmon.item;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.client.VirtualItemRenderer;
import com.apcp.originium_industry.config.OIConfigHolder;
import com.apcp.originium_industry.data.item.OIItems;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.item.component.IAddInformation;
import com.gregtechceu.gtceu.api.item.component.ICustomRenderer;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VirtualItemBehavior implements IAddInformation, ICustomRenderer {

    public static String key = "virtual_item";
    public static String tooltip = "originium_industry.virtual_item.tooltip";

    public static ItemStack getVirtualItemStack(ItemStack itemStack) {
        if(!isVirtualItem(itemStack)) return itemStack;
        var ct = itemStack.getOrCreateTag().getCompound(key);
        return ItemStack.of(ct);
    }

    public static ItemStack setVirtualItemStack(ItemStack itemStack, ItemStack virtualItem) {
        if(!isVirtualItem(itemStack)) return itemStack;
        itemStack.getOrCreateTag().put(key,virtualItem.serializeNBT());
        return itemStack;
    }

    public static ItemStack generateVirtualItemStack(ItemStack virtualItem){
        ItemStack res = OIItems.VIRTUAL_ITEM.item.asStack();
        setVirtualItemStack(res,virtualItem);
        return res;
    }

    public static AEKey getVirtualItemFromAEKey(AEItemKey key){
        return AEItemKey.of(getVirtualItemStack(key.toStack()));
    }

    public static AEKey generateVirtualItemAEKey(AEItemKey key) {
        var virtualItem = OIItems.VIRTUAL_ITEM.item.asStack();
        return AEItemKey.of(setVirtualItemStack(virtualItem,key.toStack()));
    }

    public static Ingredient extractIngredientVirtualItem(Ingredient ingredient){
        List<ItemStack> res = new ArrayList<>();
        for(var itemStack : ingredient.getItems()){
            res.add(itemStack);
            if(isVirtualItem(itemStack)){
                res.add(getVirtualItemStack(itemStack));
            }
        }
        return Ingredient.of(res.toArray(new ItemStack[0]));
    }

    public record VirtualIngredientPatch(Ingredient modified,List<ItemStack> virtualItems){}

    public static VirtualIngredientPatch patchIngredientVirtualItem(Ingredient ingredient, GTRecipe recipe){
        var virtualPatches = new ArrayList<ItemStack>();
        var com = new ArrayList<ItemStack>(ingredient.getItems().length);
        Collections.addAll(com, ingredient.getItems());
        var arr = recipe.getInputContents(ItemRecipeCapability.CAP);
        for(var content : arr) {
            if (content.chance != 0) continue;
            try {
                var test = ItemRecipeCapability.CAP.compressIngredients(List.of(content.getContent()));
//            var test = (Ingredient) (content.getContent());
                for (var temp : test){
                    if(temp instanceof Ingredient testIngredient){
                        for (var itemStack : com) {
                            if (testIngredient.test(itemStack)) {
                                var rawStack = generateVirtualItemStack(itemStack);
                                var amount = (testIngredient.getItems().length > 0)?testIngredient.getItems()[0].getCount():0;
                                rawStack.setCount(amount);
                                com.add(rawStack);
                                virtualPatches.add(rawStack);
                            }
                        }
                    }
                    if(temp instanceof ItemStack testStack){
                        for (var itemStack : com) {
                            if(itemStack.equals(testStack,true)){
                                var rawStack = generateVirtualItemStack(itemStack);
                                rawStack.setCount(testStack.getCount());
                                com.add(rawStack);
                                virtualPatches.add(rawStack);
                            }
                        }
                    }
                }

            }catch (Exception e){
                /// Do nothing.
            }
        }
        var res = com.toArray(new ItemStack[0]);
        if(OIConfigHolder.INSTANCE.debug.outputExtractedVirtualIngredients){
            OIMod.LOGGER.info("Starting logging patched Ingredients...");
            for(var stack : res){
                OIMod.LOGGER.info("Patched Ingredient : {}",stack.toString());
            }
        }
        return new VirtualIngredientPatch(Ingredient.of(res),virtualPatches);
    }

    public static List<Ingredient> extractIngredientsVirtualItems(List<Ingredient> ingredients){
        var res = new ArrayList<Ingredient>(ingredients.size());
        for(var ingredient : ingredients){
            res.add(extractIngredientVirtualItem(ingredient));
        }
        return res;
    }

    public record VirtualIngredientsPatch(List<Ingredient> modified,List<VirtualIngredientPatch> rawPatches){}

    public static VirtualIngredientsPatch patchIngredientsVirtualItems(List<Ingredient> ingredients,GTRecipe recipe){
        var res = new ArrayList<Ingredient>(ingredients.size());
        var patches = new ArrayList<VirtualIngredientPatch>(ingredients.size());
        for(var ingredient : ingredients){
            var patch = patchIngredientVirtualItem(ingredient,recipe);
            res.add(patch.modified);
            patches.add(patch);
        }
        return new VirtualIngredientsPatch(res,patches);
    }

    public static boolean isVirtualItem(ItemStack itemStack) {
        if(itemStack.getOrCreateTag().contains(key)) {
            return true;
        }
        var res = OIItems.VIRTUAL_ITEM.item.isIn(itemStack);
        res = res || itemStack.is(OIItems.VIRTUAL_ITEM.item.get());
        itemStack.getOrCreateTag().put(key, new ItemStack(Items.AIR).serializeNBT());
        return res;
    }

    public static boolean isCircuitVirtualItem(ItemStack itemStack) {
        if(!VirtualItemBehavior.isVirtualItem(itemStack)){
            return false;
        }
        var item = VirtualItemBehavior.getVirtualItemStack(itemStack);
        return IntCircuitBehaviour.isIntegratedCircuit(item);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        var virtualItem = getVirtualItemStack(stack);
        tooltipComponents.add(Component.translatableWithFallback(tooltip, "Item inside : %s", Component.translatable(virtualItem.getDescriptionId()).getString()));
    }

    @Override
    public @NotNull IRenderer getRenderer() {
        return VirtualItemRenderer.INSTANCE;
    }
}
