package com.apcp.originium_industry.api.datagen.langgen;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class LangModel {
    public HashMap<String, String> langs = new HashMap<>();

    public void setLang(String value){
        setLang("en_us",value);
    }

    public void setLang(String locale,String value) {
        langs.remove(locale);
        langs.put(locale,value);
    }

    public void apply(BiConsumer<String,String> consumer){
        langs.forEach(consumer);
    }
}
