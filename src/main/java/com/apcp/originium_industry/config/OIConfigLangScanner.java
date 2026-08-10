package com.apcp.originium_industry.config;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.annotation.LangAnnotation;
import com.apcp.originium_industry.api.datagen.langgen.annotation.LangAnnotations;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;

public class OIConfigLangScanner {
    public static boolean deepDebug = true;

    public static <T> void scan(Class<T> configClass,String parent){
        for(var i : configClass.getDeclaredFields()){
            if(i.isAnnotationPresent(LangAnnotations.class)&&i.isAnnotationPresent(Configurable.class)){
                var annotations = i.getAnnotation(LangAnnotations.class);
                var configurable = i.getAnnotation(Configurable.class);
                for(var annotation : annotations.value()){
                    var key = "config." + OIMod.CONFIG_ID + ".option." + parent + i.getName();
                    LangDataGenerator.normal.getCollector(annotation.locale()).addTranslation(key,annotation.value());
                }
            }else if(i.isAnnotationPresent(LangAnnotation.class)&&i.isAnnotationPresent(Configurable.class)){
                var annotation = i.getAnnotation(LangAnnotation.class);
                var configurable = i.getAnnotation(Configurable.class);
                var key = "config." + OIMod.CONFIG_ID + ".option." + parent + i.getName();
                LangDataGenerator.normal.getCollector(annotation.locale()).addTranslation(key,annotation.value());
            }
            else if (deepDebug) {
                OIMod.LOGGER.info("Skip non-configurable filed {} at {}", i.getName(), configClass.getSimpleName());
            }
        }
    }
    public static <T> void scan(Class<T> configClass){
        scan(configClass,"");
    }
    public static <T> void scanDeep(Class<T> configClass,String parent){
        if(!configClass.isAnnotationPresent(Config.class)){
            OIMod.LOGGER.warn("{} is not annotated with @Config!", configClass.getSimpleName());
            return;
        }
        scan(configClass,parent);
        for(var i : configClass.getDeclaredClasses()){
            scanDeep(i,parent.isEmpty()?i.getSimpleName()+".":parent+i.getSimpleName()+".");
        }
    }
    public static <T> void scanDeep(Class<T> configClass){
        scanDeep(configClass,"");
    }
}
