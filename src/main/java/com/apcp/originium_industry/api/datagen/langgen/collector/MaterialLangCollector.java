package com.apcp.originium_industry.api.datagen.langgen.collector;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import net.minecraft.resources.ResourceLocation;

public class MaterialLangCollector extends LangDataCollector{

    public MaterialLangCollector(String v_locale) {
        super(v_locale);
    }

    public void addTranslation(ResourceLocation location, String v){
        String real = "material." + location.getNamespace() + "." + location.getPath();
        addTranslation(real,v);
    }

    @SuppressWarnings("removal")
    public void addTranslation(Material material, String value){
        addTranslation(new ResourceLocation(material.getModid(),material.getName()),value);
    }

    public static MaterialLangCollector of(String v_locale) {
        return new MaterialLangCollector(v_locale);
    }
}
