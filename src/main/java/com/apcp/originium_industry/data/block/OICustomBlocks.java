package com.apcp.originium_industry.data.block;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.gtaddon.OIBlock;

public class OICustomBlocks {
    public static OIBlock ORIGINIUM_CASING_t1 = new OIBlock("originium_casing_t1");

    public static void init(){
        ORIGINIUM_CASING_t1.setBlock(
                ORIGINIUM_CASING_t1.build().createCasingBlock(
                        OIMod.id("block/machines/casings/solid/machine_casing_originium")
                )
        );
    }

    public static void initTranslation(){
        ORIGINIUM_CASING_t1.lang.setLang("Primitive Originium Casing")
                .setLang("zh_cn","简易源石外壳");
        ORIGINIUM_CASING_t1.applyLang();
    }
}
