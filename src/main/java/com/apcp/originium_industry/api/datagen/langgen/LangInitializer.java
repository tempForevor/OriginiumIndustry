package com.apcp.originium_industry.api.datagen.langgen;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.commmon.machine.part.SuperMEPatternBufferPart;
import com.apcp.originium_industry.config.OIConfigHolder;
import com.apcp.originium_industry.data.block.OICustomBlocks;
import com.apcp.originium_industry.data.item.OIItems;
import com.apcp.originium_industry.data.machine.OIMachines;
import com.apcp.originium_industry.data.material.OIExtendMaterial;
import com.apcp.originium_industry.commmon.recipe_type.OICustomRecipeType;
import com.apcp.originium_industry.data.tectree.OITecTreeItems;

public class LangInitializer {
    // TODO : DataScanner

    public static void specialInit(){
        /// SuperMEPatternBufferPart ///
        {
            LangModel SUPER_ME_PATTERN_BUFFER_PART = new LangModel();
            LangModel SUPER_ME_PATTERN_BUFFER_PROXY = new LangModel();
            LangModel CONN_ALL_DIR = new LangModel();
            LangModel CONN_FRONT_DIR = new LangModel();

            SUPER_ME_PATTERN_BUFFER_PART.setLang("Set buffer into the data.")
                    .setLang("zh_cn", "已将总成信息保存至数据中。");
            SUPER_ME_PATTERN_BUFFER_PROXY.setLang("Set data into the buffer.")
                    .setLang("zh_cn", "已将数据信息保存至镜像中。");
            CONN_ALL_DIR.setLang("Set Connection Mode To : All Sides")
                    .setLang("zh_cn","设置ME链接模式为：全方向");
            CONN_FRONT_DIR.setLang("Set Connection Mode To : Front Sides")
                    .setLang("zh_cn","设置ME链接模式为：仅前方");

            SUPER_ME_PATTERN_BUFFER_PART.normalApply(SuperMEPatternBufferPart.dataStickUseInfoId);
            SUPER_ME_PATTERN_BUFFER_PROXY.normalApply(SuperMEPatternBufferPart.dataStickMoveInfoId);
            CONN_ALL_DIR.normalApply(SuperMEPatternBufferPart.changeConnModeAllId);
            CONN_FRONT_DIR.normalApply(SuperMEPatternBufferPart.changeConnModeFrontId);
        }/// ------------------------ ///

        /// Jade Plugin ///
        {
            LangModel JADE_SME_BUFFER_PART = new LangModel();
            LangModel JADE_SME_BUFFER_PROXY = new LangModel();

            JADE_SME_BUFFER_PART.setLang("Super ME Pattern Buffer Part Machine.")
                    .setLang("zh_cn", "超级ME样板总成仓室");
            JADE_SME_BUFFER_PROXY.setLang("Super ME Pattern Buffer Proxy Part Machine.")
                    .setLang("zh_cn", "超级ME样板总成镜像仓室");

            JADE_SME_BUFFER_PART.normalApply("config.jade.plugin_originium_industry.super_me_pattern_buffer_proxy");
            JADE_SME_BUFFER_PROXY.normalApply("config.jade.plugin_originium_industry.super_me_pattern_buffer");
        }/// ----------- ///
    }

    public static void init(){
        OIMachines.initTranslation();
        OIExtendMaterial.initTranslation();
        OIConfigHolder.initTranslation();
        OICustomRecipeType.initTranslation();
        OIItems.initTranslation();
        OICustomBlocks.initTranslation();
        OITecTreeItems.initTranslation();
        specialInit();
        OIMod.LOGGER.info("Successfully initialized language data!");
    }
}
