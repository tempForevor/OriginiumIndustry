package com.apcp.originium_industry.data.material;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.gtaddon.OIMaterial;
import com.apcp.originium_industry.data.element.OIExtendElement;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

public class OIOriProcMaterial {
    public static OIMaterial ORIGINIUM_CATALYZED_FLUORINATED_MIXTURE = new OIMaterial(OIMod.id("originium_catalyzed_fluorinated_mixture"));
    public static OIMaterial IMPURE_ORIGINIUM = new OIMaterial(OIMod.id("proc_impure_originium"));
    public static OIMaterial HALF_IMPURE_ORIGINIUM = new OIMaterial(OIMod.id("half_impure_originium"));


    public static void init(){
        ORIGINIUM_CATALYZED_FLUORINATED_MIXTURE.buildProcLiquid(builder->builder
                .formula(OIExtendElement.Originium.formula+"3"+GTMaterials.Fluorine.getChemicalFormula()+"12?")
                .color(0x122312+0x000046).secondaryColor(0x122312c+0x0000460));



        IMPURE_ORIGINIUM.buildProcDust(builder->builder
                .formula(OIExtendElement.Originium.formula+"?")
                .color(0x122312-0x050505).secondaryColor(0x122312c-0x0505050));

        HALF_IMPURE_ORIGINIUM.buildProcDust(builder->builder
                .formula(OIExtendElement.Originium.formula+"?")
                .color(0x122312-0x020202).secondaryColor(0x122312c-0x0202020));
    }
    public static void initTranslation(){
        ORIGINIUM_CATALYZED_FLUORINATED_MIXTURE.setLang("Fluorinated mixture catalyzed by Originium")
                .setLang("zh_cn","源石催化的氟混合物").langApply();
        IMPURE_ORIGINIUM.setLang("Industry Productional Impure Originium")
                .setLang("zh_cn","工业生产含杂源石").langApply();
        HALF_IMPURE_ORIGINIUM.setLang("Industry Productional Half Impure Originium")
                .setLang("zh_cn","工业生成半含杂源石").langApply();
    }
}
