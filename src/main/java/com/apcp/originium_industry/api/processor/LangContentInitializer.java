package com.apcp.originium_industry.api.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@Deprecated
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.apcp.originium_industry.api.util.oi.OILangContentInitializer")
public class LangContentInitializer extends AbstractProcessor {

    @SuppressWarnings("CommentedOutCode")
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;

//        var elements = roundEnv.getElementsAnnotatedWith(OILangContentInitializer.class);
//        for(var element : elements){
//            if(element.getKind() != ElementKind.CLASS){
//                continue;
//            }
//            if(element.getModifiers().contains(Modifier.ABSTRACT)){
//                continue;
//            }
//            try{
//                OILangContentInitializer.Initializer.registry.register((Class<OILangContentInitializer>) Class.forName(element.getSimpleName().toString()));
//            }catch (Exception e){
//                /// Do nothing...Too.
//            }
//        }
//        return false;
    }
}
