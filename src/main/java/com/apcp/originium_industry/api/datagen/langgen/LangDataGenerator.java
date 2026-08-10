package com.apcp.originium_industry.api.datagen;

import com.apcp.originium_industry.OIMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LangDataGenerator extends LanguageProvider {

    public LangDataGenerator(PackOutput output, String locale) {
        super(output, OIMod.MOD_ID,locale);
    }

    @Override
    protected void addTranslations() {

    }
}
