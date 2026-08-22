package com.apcp.originium_industry.api.datagen;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.modelgen.OIBlockModelGenerator;
import com.apcp.originium_industry.api.datagen.modelgen.OIItemModelGenerator;
import com.apcp.originium_industry.api.datagen.modelgen.OIModelInitializer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = OIMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class OIGeneralDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(),new LangDataGenerator(output, "en_us"));
        generator.addProvider(event.includeClient(),new LangDataGenerator(output, "zh_cn"));

        OIModelInitializer.init();

        generator.addProvider(event.includeClient(),new OIBlockModelGenerator(generator,helper));
        generator.addProvider(event.includeClient(),new OIItemModelGenerator(generator,helper));
    }
}
