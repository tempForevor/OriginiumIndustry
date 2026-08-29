package com.apcp.originium_industry.commmon.item.me;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.StorageCell;
import appeng.items.contents.CellConfig;
import com.apcp.originium_industry.commmon.item.VirtualItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

/// @see appeng.me.cells.CreativeCellInventory
@SuppressWarnings("JavadocReference")
public class VirtualItemCellInventory implements StorageCell {
    private final Set<AEKey> configured;
    private final ItemStack stack;

    protected VirtualItemCellInventory(ItemStack o) {
        this.configured = new HashSet<>();
        this.stack = o;
        var cc = CellConfig.create(o);
        for(var key : cc.keySet()){
            if(key instanceof AEItemKey itemKey){
                configured.add(VirtualItemBehavior.generateVirtualItemAEKey(itemKey));
            }else{
                configured.add(key);
            }
        }
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        return configured.contains(what) ? amount : 0;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        return configured.contains(what) ? amount : 0;
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        for (AEKey key : this.configured) {
            out.add(key, Integer.MAX_VALUE);
        }
    }

    @Override
    public KeyCounter getAvailableStacks() {
        KeyCounter out = new KeyCounter();
        getAvailableStacks(out);
        return out;
    }

    @Override
    public boolean isPreferredStorageFor(AEKey input, IActionSource source) {
        return this.configured.contains(input);
    }

    @Override
    public CellState getStatus() {
        return CellState.TYPES_FULL;
    }

    @Override
    public double getIdleDrain() {
        return 0;
    }

    @Override
    public boolean canFitInsideCell() {
        return configured.isEmpty();
    }

    @Override
    public Component getDescription() {
        return stack.getHoverName();
    }

    @Override
    public void persist() {
    }
}
