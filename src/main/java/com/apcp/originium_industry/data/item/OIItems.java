package com.apcp.originium_industry.data.item;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class OIItems {

    public static RegistryEntry<CreativeModeTab> creativeModeTab;

    public static void init(){
        creativeModeTab = OIMod.OIREGISTRATE
                .defaultCreativeTab("originium_industry",builder -> {
                    builder.title(Component.translatable("originium_industry.creative_tab"))
                            .withSearchBar();
                }).register();
        OIOriProcItems.init();
        OITechTreeDataItems.init();
    }


    public static void initTranslation(){
        OIOriProcItems.initTranslation();
        OITechTreeDataItems.initTranslation();
        LangDataGenerator.normal.getCollector("zh_cn").addTranslation("originium_industry.creative_tab","源石工业");
        LangDataGenerator.normal.getCollector("en_us").addTranslation("originium_industry.creative_tab","Originium Industry");
    }

    public static void initModel(){
        OIOriProcItems.initModel();
        OITechTreeDataItems.initModel();
    }
}
