package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.config.OIConfigHolder;
import com.gregtechceu.gtceu.api.GTValues;

public class OIParallelUtil {

    public static int normalMaxParallel(int tier){
        int tierGap = tier - GTValues.ULV;
        double parallel = Math.pow(OIConfigHolder.INSTANCE.parallelScale, tierGap);
        if(parallel > (double)Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }
        return (int) parallel;
    }
}
