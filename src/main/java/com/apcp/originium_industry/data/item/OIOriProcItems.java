package com.apcp.originium_industry.data.item;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.gtaddon.OIItem;

public class OIOriProcItems {
    public static OIItem ACTIVE_ORIGINIUM_MIXED_UV_FIELD = new OIItem("active_originium_mixed_uv_field");
    public static OIItem ORIGINIUM_PROTECTIVE_UV_FIELD = new OIItem("originium_protective_uv_field");
    public static OIItem ORIGINIUM_WELL_EXTRACTOR = new OIItem("originium_well_extractor");

    public static void init(){
        ACTIVE_ORIGINIUM_MIXED_UV_FIELD.register(builder->
                builder.tab(OIItems.creativeModeTab.getKey()));
        ORIGINIUM_PROTECTIVE_UV_FIELD.register(builder->
                builder.tab(OIItems.creativeModeTab.getKey()));
        ORIGINIUM_WELL_EXTRACTOR.register(builder->
                builder.tab(OIItems.creativeModeTab.getKey()));
    }

    public static void initTranslation(){
        ACTIVE_ORIGINIUM_MIXED_UV_FIELD.setLang("UV Field Generator mixed with Active Originium")
                .setLang("zh_cn","活性源石渗透的UV立场发生器").langApply();
        ORIGINIUM_PROTECTIVE_UV_FIELD.setLang("Originium Protective UV Field Generator")
                .setLang("zh_cn","具有源石防护能力的UV立场发生器").langApply();
        ORIGINIUM_WELL_EXTRACTOR.setLang("Originium Well Extractor")
                .setLang("zh_cn","源石相性分离芯").langApply();
    }

}
