package com.apcp.originium_industry.api.datagen.langgen.tectree;

import com.apcp.originium_industry.api.datagen.langgen.collector.LangDataCollector;
import com.apcp.originium_industry.api.tectree.OITecInfo;

import java.util.function.BiConsumer;

public class TecTreeLangDataCollector extends LangDataCollector {
    public TecTreeLangDataCollector(String v_locale) {
        super(v_locale);
    }

    public void addTranslation(OITecInfo tecInfo,String name,String information) {
        addTranslation(tecInfo.generateLocalizeNameId(),name);
        addTranslation(tecInfo.generateLocalizeInfoId(),information);
    }

    public void addTranslation(String key, String value) {
        super.addTranslation(key, value);
    }

    public void foreach(BiConsumer<String, String> consumer) {
        super.foreach(consumer);
    }
}
