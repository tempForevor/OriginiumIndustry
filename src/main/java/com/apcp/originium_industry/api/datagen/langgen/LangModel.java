package com.apcp.originium_industry.api.datagen.langgen;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class LangModel {
    public HashMap<String, String> langs = new HashMap<>();

    public LangModel setLang(String value){
        return setLang("en_us",value);
    }

    public LangModel setLang(String locale,String value) {
        langs.remove(locale);
        langs.put(locale,value);
        return this;
    }

    public void apply(BiConsumer<String,String> consumer){
        langs.forEach(consumer);
    }
    public void normalApply(String key){
        apply(
                (k,v)->LangDataGenerator.normal.getCollector(k).addTranslation(key,v)
        );
    }
}
