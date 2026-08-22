package com.apcp.originium_industry.api.datagen.modelgen;

import com.apcp.originium_industry.data.item.OIItems;
import com.apcp.originium_industry.data.machine.OIMachines;

public class OIModelInitializer {
    public static void init(){
        // GTCEu 本体 ModelGenerator 可用
//        OIMachines.initModel();
        OIItems.initModel();
    }
}
