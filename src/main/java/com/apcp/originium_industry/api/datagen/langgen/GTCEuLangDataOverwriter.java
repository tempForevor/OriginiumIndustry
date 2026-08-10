package com.apcp.originium_industry.api.datagen.langgen;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.collector.LangDataCollector;
import com.apcp.originium_industry.api.datagen.langgen.collector.MaterialLangCollector;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class GTCEuLangDataOverwriter extends LanguageProvider {

    public static LangLocaleData<MaterialLangCollector> materials = new LangLocaleData<>(MaterialLangCollector.class);
    public static LangLocaleData<LangDataCollector> normal = new LangLocaleData<>(LangDataCollector.class);

    public String public_locale;

    public GTCEuLangDataOverwriter(PackOutput output, String locale) {
        super(output, OIMod.MOD_ID,locale);
        public_locale = locale;
    }

    @Override
    protected void addTranslations() {
        materials.apply(public_locale, this::add);
        normal.apply(public_locale, this::add);
        OIMod.LOGGER.info("Successfully overwrite language data!");
    }
}
