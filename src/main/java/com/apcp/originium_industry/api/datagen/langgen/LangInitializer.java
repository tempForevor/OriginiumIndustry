package com.apcp.originium_industry.api.datagen.langgen;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.config.OIConfigHolder;
import com.apcp.originium_industry.data.block.OICustomBlocks;
import com.apcp.originium_industry.data.item.OIItems;
import com.apcp.originium_industry.data.machine.OIMachines;
import com.apcp.originium_industry.data.material.OIExtendMaterial;
import com.apcp.originium_industry.data.recipe_type.OICustomRecipeType;
import com.apcp.originium_industry.data.tectree.OITecTreeItems;

public class LangInitializer {
    // TODO : DataScanner

    public static void init(){
        OIMachines.initTranslation();
        OIExtendMaterial.initTranslation();
        OIConfigHolder.initTranslation();
        OICustomRecipeType.initTranslation();
        OIItems.initTranslation();
        OICustomBlocks.initTranslation();
        OITecTreeItems.initTranslation();
        OIMod.LOGGER.info("Successfully initialized language data!");
    }
}
