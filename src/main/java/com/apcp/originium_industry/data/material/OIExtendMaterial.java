package com.apcp.originium_industry.data.material;

import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialEvent;

public class OIExtendMaterial {

    public static void init(MaterialEvent event){
        OIElementMaterial.init();
        OIOriProcMaterial.init();
    }

    public static void initTranslation(){
        OIElementMaterial.initTranslation();
        OIOriProcMaterial.initTranslation();
    }
}
