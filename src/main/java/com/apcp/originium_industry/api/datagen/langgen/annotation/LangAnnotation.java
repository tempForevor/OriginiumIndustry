package com.apcp.originium_industry.api.datagen.langgen.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LangAnnotations.class)
public @interface LangAnnotation{
    String locale() default "en_us";
    String value();
}