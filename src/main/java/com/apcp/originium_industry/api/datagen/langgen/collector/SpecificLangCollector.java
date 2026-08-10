package com.apcp.originium_industry.api.datagen.langgen.collector;

import java.util.function.Function;

public class SpecificLangCollector <T> extends LangDataCollector{

    public Function<T,String> keyConsumer;

    public SpecificLangCollector(String public_locale,Function<T,String> keyConsumer) {
        super(public_locale);
        this.keyConsumer = keyConsumer;
    }

    public void addTranslation(T key,String value) {
        addTranslation(keyConsumer.apply(key),value);
    }
}
