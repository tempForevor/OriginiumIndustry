package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.LangModel;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import net.minecraft.resources.ResourceLocation;


public class OIMaterialBuilder extends Material.Builder {

    public OIMaterialBuilder(ResourceLocation location){
        super(location);
    }

}
