package com.apcp.originium_industry.data.machine;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.gtaddon.OICombTieredMachineDef;
import com.apcp.originium_industry.data.machine.single.generator.OIProcGenerator;
import com.apcp.originium_industry.data.recipe_type.OICustomRecipeType;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;


public class OICustomMachines {

    public static OICombTieredMachineDef ORIGINIUM_RELEASER = new OICombTieredMachineDef("originium_releaser");
    public static OICombTieredMachineDef ORIGINIUM_DEACTIVER = new OICombTieredMachineDef("originium_deactivator");

    public static void init(){
        ORIGINIUM_RELEASER.setMachines(GTMachineUtils.registerTieredMachines(
                OIMod.OIREGISTRATE,
                ORIGINIUM_RELEASER.id,
                OIProcGenerator::new,
                (tier, builder) -> builder
                    .editableUI(OIProcGenerator.EDITABLE_UI_CREATOR.apply(OIMod.id(ORIGINIUM_RELEASER.id), OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeType))
                    .rotationState(RotationState.ALL)
                    .recipeType(OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeType)
                    .simpleGeneratorModel(GTCEu.id("block/generators/"))
                    .tooltips(GTMachineUtils.workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeType,
                            GTMachineUtils.genericGeneratorTankSizeFunction.applyAsInt(tier), false))
                    .register(),
            GTValues.tiersBetween(GTValues.ULV,GTValues.EV)
        ));
//        ORIGINIUM_RELEASER.setMachines(GTMachineUtils.registerSimpleGenerator(
//                OIMod.OIREGISTRATE,
//                ORIGINIUM_RELEASER.id,
//                OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeType,
//                GTMachineUtils.genericGeneratorTankSizeFunction,
//                0.1f,
//                GTValues.tiersBetween(GTValues.ULV,GTValues.EV)
//        ));
        ORIGINIUM_DEACTIVER.setMachines(GTMachineUtils.registerTieredMachines(
                OIMod.OIREGISTRATE,
                ORIGINIUM_DEACTIVER.id,
                OIProcGenerator::new,
                (tier, builder) -> builder
                        .editableUI(OIProcGenerator.EDITABLE_UI_CREATOR.apply(OIMod.id(ORIGINIUM_DEACTIVER.id), OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeType))
                        .rotationState(RotationState.ALL)
                        .recipeType(OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeType)
                        .simpleGeneratorModel(GTCEu.id("block/generators/"))
                        .tooltips(GTMachineUtils.workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeType,
                                GTMachineUtils.genericGeneratorTankSizeFunction.applyAsInt(tier), false))
                        .register(),
                GTValues.tiersBetween(GTValues.IV,GTValues.UV)
        ));
//        ORIGINIUM_DEACTIVER.setMachines(GTMachineUtils.registerSimpleGenerator(
//                OIMod.OIREGISTRATE,
//                ORIGINIUM_DEACTIVER.id,
//                OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeType,
//                GTMachineUtils.genericGeneratorTankSizeFunction,
//                0.1f,
//                GTValues.tiersBetween(GTValues.IV,GTValues.UV)
//        ));
    }

    public static void initTranslation(){
        ORIGINIUM_RELEASER.setLang("Originium Releaser")
                .setLang("zh_cn","简易源石能量释放装置")
                .langApply(GTValues.tiersBetween(GTValues.ULV,GTValues.EV));
        ORIGINIUM_DEACTIVER.setLang("Industry Originium Deactivator")
                .setLang("zh_cn","工业源石催化装置")
                .langApply(GTValues.tiersBetween(GTValues.IV,GTValues.UV));
    }
}
