package com.apcp.originium_industry.api.datagen.langgen.collector;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class LangDataCollector{

    public String locale;
    public HashMap<String, String> langs = new HashMap<>();

    public LangDataCollector(String v_locale) {
        locale = v_locale;
    }

    public static LangDataCollector of(String v_locale){
        return new LangDataCollector(v_locale);
    }

    public void addTranslation(String key,String value){
        if(langs.containsKey(key)) return;
        langs.put(key,value);
    }

    public void foreach(BiConsumer<String, String> consumer){
        langs.forEach(consumer);
    }
}