package com.apcp.originium_industry.data.item;

import com.apcp.originium_industry.api.gtaddon.OIItem;
import com.apcp.originium_industry.commmon.item.VirtualItemCell;

public class OIAEItems {
    public static OIItem VIRTUAL_ITEM_CELL = new OIItem("virtual_item_cell");

    public static void init(){
        VIRTUAL_ITEM_CELL.rawRegister(
                VirtualItemCell::new,
                builder->builder
                        .tab(OIItems.creativeModeTab.getKey())
        );
    }
    public static void initTranslation(){
        VIRTUAL_ITEM_CELL.lang.setLang("Virtual Item Cell")
                .setLang("zh_cn","虚拟物品元件");
        VIRTUAL_ITEM_CELL.tooltipLang.setLang("Provides infinity virtual items.")
                .setLang("zh_cn","提供无限的虚拟物品");
        VIRTUAL_ITEM_CELL.langApply();
    }
}
