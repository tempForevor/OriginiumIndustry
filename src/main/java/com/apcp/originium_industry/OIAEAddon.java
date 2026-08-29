package com.apcp.originium_industry;

import appeng.api.storage.StorageCells;
import com.apcp.originium_industry.commmon.item.me.VirtualItemCellHandler;

public class OIAEAddon {
    public static void onCellRegistry(){
        StorageCells.addCellHandler(VirtualItemCellHandler.INSTANCE);
    }
}
