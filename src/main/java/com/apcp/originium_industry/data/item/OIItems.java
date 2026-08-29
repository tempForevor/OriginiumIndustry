package com.apcp.originium_industry.data.item;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.gtaddon.OIItem;
import com.apcp.originium_industry.commmon.item.VirtualItemBehavior;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Locale;

public class OIItems {

    static {
        OIMod.OIREGISTRATE.registerRegistrate();
    }

    public static RegistryEntry<CreativeModeTab> creativeModeTab = OIMod.OIREGISTRATE
            .defaultCreativeTab("originium_industry", builder -> builder
            .title(Component.translatable("originium_industry.creative_tab"))
            .withSearchBar()).register();;

    public static OIItem VIRTUAL_ITEM = new OIItem("virtual_item");

    public static OIItem[] UNIVERSAL_CIRCUIT = Arrays.stream(GTValues.ALL_TIERS)
                    .mapToObj(tier ->
                            new OIItem(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_universal_circuit").rawRegister(Item::new,builder->builder
                                    .tab(creativeModeTab.getKey())
                                    .tag(CustomTags.CIRCUITS_ARRAY[tier])
                            )
                    ).toArray(OIItem[]::new);

    public static void init(){
        OIMod.LOGGER.info("Initializing OI Items");

        VIRTUAL_ITEM.register(
                builder -> builder
                        .tab(creativeModeTab.getKey())
                        .onRegister(item -> item.attachComponents(new VirtualItemBehavior()))
        );

        OIOriProcItems.init();
        OITechTreeDataItems.init();
        OIAEItems.init();
    }


    public static void initTranslation(){
        OIOriProcItems.initTranslation();
        OITechTreeDataItems.initTranslation();
        OIAEItems.initTranslation();
        LangDataGenerator.normal.getCollector("zh_cn").addTranslation("originium_industry.creative_tab","源石工业");
        LangDataGenerator.normal.getCollector("en_us").addTranslation("originium_industry.creative_tab","Originium Industry");

        VIRTUAL_ITEM.tooltipLang.setLang("Item inside : %s")
                .setLang("zh_cn","内部物品 : %s");
        VIRTUAL_ITEM.setLang("Virtual Item")
                .setLang("zh_cn","虚拟物品")
                .langApply();

        for(var tier : GTValues.ALL_TIERS){
            UNIVERSAL_CIRCUIT[tier].tooltipLang.setLang(GTValues.VNF[tier] + " Tier Circuit")
                    .setLang("zh_cn",GTValues.VNF[tier] + " 级 通用电路");
            UNIVERSAL_CIRCUIT[tier].setLang(GTValues.VNF[tier] + " Universal Circuit")
                    .setLang("zh_cn",GTValues.VNF[tier] + " 通用电路")
                    .langApply();
        }

    }

    public static void initModel(){
        OIOriProcItems.initModel();
        OITechTreeDataItems.initModel();
    }
}
