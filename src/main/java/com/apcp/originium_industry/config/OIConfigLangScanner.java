package com.apcp.originium_industry.config;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.annotation.LangAnnotations;
import dev.toma.configuration.config.Configurable;

public class OIConfigLangScaner {
    public static <T> void scan(Class<T> configClass,String parent){
        for(var i : configClass.getDeclaredFields()){
            if(i.isAnnotationPresent(LangAnnotations.class)&&i.isAnnotationPresent(Configurable.class)){
                var annotations = i.getAnnotation(LangAnnotations.class);
                var configurable = i.getAnnotation(Configurable.class);
                for(var annotation : annotations.value()){
                    var key = "config." + OIMod.CONFIG_ID + ".option." + parent + i.getName();
                    LangDataGenerator.normal.getCollector(annotation.locale()).addTranslation(key,annotation.value());
                }
            }
        }
    }
    public static <T> void scan(Class<T> configClass){
        scan(configClass,"");
    }
    public static <T> void scanDeep(Class<T> configClass,String parent){
        scan(configClass);
        for(var i : configClass.getDeclaredClasses()){
            scanDeep(i,parent+"."+i.getName());
        }
    }
}
