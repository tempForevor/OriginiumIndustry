package com.apcp.originium_industry.data.machine;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.modelgen.collector.OIBlockModelUtil;
import com.apcp.originium_industry.api.gtaddon.OIMachineDef;
import com.apcp.originium_industry.commmon.machine.single.uiholder.TecTreeHolder;
import com.gregtechceu.gtceu.api.GTValues;
import net.minecraft.network.chat.Component;

public class OIUIMachines {
    public static OIMachineDef TECH_TREE_UI_HOLDER = new OIMachineDef("tech_tree_ui_holder");

    public static void init(){
        TECH_TREE_UI_HOLDER.register((holder)-> new TecTreeHolder(holder, OIMod.TEC_TREE), builder-> builder
                .tooltips(Component.translatableWithFallback("block.originium_industry.tech_tree_ui_holder.tooltip","TechTree and Info Displayer."))
                .model((ctx,prov,bdr)
                        ->OIBlockModelUtil.createPlaceHolderModel(ctx,prov,bdr,GTValues.MAX))
        );
    }
    public static void initTranslation(){
        TECH_TREE_UI_HOLDER.tooltipLang
                .setLang("The first step towards the truth...")
                .setLang("zh_cn","向真相迈出的第一步...");
        TECH_TREE_UI_HOLDER.setLang("[Ori-Eps] Runic Terminal")
                .setLang("zh_cn","[Ori-Eps] 符文式终端")
                .applyLang();
    }
    @Deprecated
    public static void initModel(){
        TECH_TREE_UI_HOLDER.initModels();
    }
}
