package com.apcp.originium_industry.api.tectree;

import com.apcp.originium_industry.api.datagen.langgen.tectree.TecTreeLangModel;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class OITecInfo {
    public static int max_id = 0;

    public int id;
    public ResourceLocation location;
    public List<Integer> dependencies;

    public TecTreeLangModel lang = new TecTreeLangModel();

    public OITecInfo(ResourceLocation location){
        this.id = max_id;
        max_id++;
        this.location = location;
        this.dependencies = new ArrayList<>();
    }

    public OITecInfo addDependencies(int id){
        dependencies.add(id);
        return this;
    }

    public OITecInfo addDependencies(OITecInfo info){
        return addDependencies(info.id);
    }

    public void register(TecTree tecTree){
        tecTree.register(this);
    }

    public String generateLocalizeNameId(){
        return "tectree." + location.toLanguageKey() + ".name";
    }
    public String generateLocalizeInfoId(){
        return "tectree." + location.toLanguageKey() + ".info";
    }

    public void applyLang(){
        lang.defaultApply(this);
    }
}
