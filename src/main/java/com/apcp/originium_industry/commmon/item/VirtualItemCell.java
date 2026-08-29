package com.apcp.originium_industry.commmon.item;

import appeng.api.client.AEKeyRendering;
import appeng.api.config.FuzzyMode;
import appeng.api.stacks.GenericStack;
import appeng.api.storage.StorageCells;
import appeng.api.storage.cells.ICellWorkbenchItem;
import appeng.core.localization.GuiText;
import appeng.core.localization.Tooltips;
import appeng.items.AEBaseItem;
import appeng.items.contents.CellConfig;
import appeng.items.storage.CreativeCellItem;
import appeng.util.ConfigInventory;
import com.apcp.originium_industry.commmon.item.me.VirtualItemCellHandler;
import com.apcp.originium_industry.data.item.OIAEItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/// @see CreativeCellItem
public class VirtualItemCell extends AEBaseItem implements ICellWorkbenchItem {
    public VirtualItemCell(Properties properties){
        super(properties.stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public ConfigInventory getConfigInventory(ItemStack is) {
        return CellConfig.create(is);
    }

    public static ConfigInventory getStaticConfigInventory(ItemStack is){
        return CellConfig.create((is));
    }

    @Override
    public FuzzyMode getFuzzyMode(ItemStack is) {
        return FuzzyMode.IGNORE_ALL;
    }

    @Override
    public void setFuzzyMode(ItemStack is, FuzzyMode fzMode) {
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> lines, @NotNull TooltipFlag advancedTooltips) {
        var inventory = StorageCells.getCellInventory(stack, null);

        if (inventory != null) {
            var cc = getConfigInventory(stack);
            if (!cc.isEmpty()) {
                if (Screen.hasShiftDown()) {
                    for (var key : cc.keySet()) {
                        lines.add(Tooltips.of(AEKeyRendering.getDisplayName(key)));
                    }
                } else {
                    lines.add(Tooltips.of(GuiText.PressShiftForFullList));
                }
            }
        }
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        return VirtualItemCellHandler.INSTANCE.getTooltipImage(stack);
    }

    public static ItemStack ofItems(ItemLike... items) {
        var cell = OIAEItems.VIRTUAL_ITEM_CELL.item.asStack();
        var configInv = getStaticConfigInventory(cell);
        for (int i = 0; i < items.length; i++) {
            configInv.setStack(i, GenericStack.fromItemStack(new ItemStack(items[i])));
        }
        return cell;
    }
}
