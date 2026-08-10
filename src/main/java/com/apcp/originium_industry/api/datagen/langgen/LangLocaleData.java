package com.apcp.originium_industry.api.datagen.langgen.collector;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class LangLocaleData <T extends LangDataCollector> {

    public HashMap<String, T> langs;

    public void addCollector(String locale,T collector){
        langs.put(locale,collector);
    }

    public T getCollector(String locale){
        if(langs.containsKey(locale)){return langs.get(locale);}
        //noinspection unchecked
        addCollector(locale, (T) T.of(locale));
        return getCollector(locale);
    }

    public void apply(String locale, BiConsumer<String,String> consumer){
        if(langs.containsKey(locale)){
            langs.get(locale).foreach(consumer);
        }
    }

}
