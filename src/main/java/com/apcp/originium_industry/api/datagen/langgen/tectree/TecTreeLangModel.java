package com.apcp.originium_industry.api.datagen.langgen.tectree;

import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.tectree.OITecInfo;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class TecTreeLangModel {
    public HashMap<String, Pair<String,String>> langs = new HashMap<>();

    public TecTreeLangModel setLang(Pair<String,String> value){
        return setLang("en_us",value);
    }

    public TecTreeLangModel setLang(String locale,Pair<String,String> value) {
        langs.remove(locale);
        langs.put(locale,value);
        return this;
    }

    public void apply(BiConsumer<String,Pair<String,String>> consumer){
        langs.forEach(consumer);
    }
    public void defaultApply(OITecInfo tecInfo){
        apply(
                (k,v)->
                        LangDataGenerator.tectree.getCollector(k).addTranslation(tecInfo,v.getKey(),v.getValue())
        );
    }
}
