package com.apcp.originium_industry.commmon.item.me;

import appeng.api.stacks.GenericStack;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import appeng.core.AEConfig;
import appeng.items.contents.CellConfig;
import appeng.items.storage.StorageCellTooltipComponent;
import appeng.me.cells.CreativeCellHandler;
import com.apcp.originium_industry.commmon.item.VirtualItemCell;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/// @see CreativeCellHandler
public class VirtualItemCellHandler implements ICellHandler{
    public static final VirtualItemCellHandler INSTANCE = new VirtualItemCellHandler();

    @Override
    public boolean isCell(ItemStack is) {
        return (!is.isEmpty()) && (is.getItem() instanceof VirtualItemCell);
    }

    @Override
    public StorageCell getCellInventory(ItemStack is, ISaveProvider container) {
        if (isCell(is)) {
            return new VirtualItemCellInventory(is);
        }
        return null;
    }

    public Optional<TooltipComponent> getTooltipImage(ItemStack is) {
        var handler = getCellInventory(is, null);
        if (handler == null) { // Check that this is a creative cell
            return Optional.empty();
        }

        var cc = CellConfig.create(is);

        boolean hasMoreContent;
        List<GenericStack> content;
        if (AEConfig.instance().isTooltipShowCellContent()) {
            content = new ArrayList<>();

            var maxCountShown = AEConfig.instance().getTooltipMaxCellContentShown();

            for (var key : cc.keySet()) {
                content.add(new GenericStack(key, 1));
            }

            hasMoreContent = content.size() > maxCountShown;
            if (content.size() > maxCountShown) {
                content.subList(maxCountShown, content.size()).clear();
            }
        } else {
            hasMoreContent = false;
            content = Collections.emptyList();
        }

        return Optional.of(new StorageCellTooltipComponent(
                List.of(),
                content,
                hasMoreContent,
                false));
    }
}
