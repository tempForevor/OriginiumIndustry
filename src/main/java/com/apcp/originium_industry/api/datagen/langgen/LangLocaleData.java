package com.apcp.originium_industry.api.datagen.langgen;

import com.apcp.originium_industry.api.datagen.langgen.collector.LangDataCollector;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.function.BiConsumer;

public class LangLocaleData <T extends LangDataCollector> {

    public Class<T> collectorClass;
    public HashMap<String, T> langs = new HashMap<>();

    public LangLocaleData(Class<T> v_collectorClass){
        this.collectorClass = v_collectorClass;
    }

    public void addCollector(String locale,T collector){
        langs.put(locale,collector);
    }

    public T getCollector(String locale){
        if(langs.containsKey(locale)){return langs.get(locale);}
        try {
            Constructor<T> constructor = collectorClass.getConstructor(String.class);
            T new_collector = constructor.newInstance(locale);
            addCollector(locale,new_collector);
            return new_collector;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void apply(String locale, BiConsumer<String,String> consumer){
        if(langs.containsKey(locale)){
            langs.get(locale).foreach(consumer);
        }
    }

}
