package com.apcp.originium_industry.data.machine;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.modelgen.collector.OIBlockModelUtil;
import com.apcp.originium_industry.api.gtaddon.OICombTieredMachineDef;
import com.apcp.originium_industry.api.gtaddon.OIMachineDef;
import com.apcp.originium_industry.data.machine.part.SuperMEPatternBufferPart;
import com.apcp.originium_industry.data.machine.single.generator.OIProcGenerator;
import com.apcp.originium_industry.data.recipe_type.OICustomRecipeType;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import net.minecraft.network.chat.Component;


public class OICustomMachines {

    public static OICombTieredMachineDef ORIGINIUM_RELEASER = new OICombTieredMachineDef("originium_releaser");
    public static OICombTieredMachineDef ORIGINIUM_DEACTIVER = new OICombTieredMachineDef("originium_deactivator");
    public static OIMachineDef SUPER_ME_PATTERN_BUFFER = new OIMachineDef("super_me_pattern_buffer");

    public static void init(){
        ORIGINIUM_RELEASER.setMachines(GTMachineUtils.registerTieredMachines(
                OIMod.OIREGISTRATE,
                ORIGINIUM_RELEASER.id,
                OIProcGenerator::new,
                (tier, builder) -> builder
                    .editableUI(OIProcGenerator.EDITABLE_UI_CREATOR.apply(OIMod.id(ORIGINIUM_RELEASER.id), OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeType))
                    .rotationState(RotationState.ALL)
                    .recipeType(OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeType)
//                    .simpleGeneratorModel(GTCEu.id("block/generators/"))
                    .model((ctx,prov,bdr)
                                ->OIBlockModelUtil.createPlaceHolderModel(ctx,prov,bdr,tier))
                    .tooltips(GTMachineUtils.workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeType,
                            GTMachineUtils.genericGeneratorTankSizeFunction.applyAsInt(tier), false))
                    .register(),
            GTValues.tiersBetween(GTValues.ULV,GTValues.EV)
        ));
        ORIGINIUM_DEACTIVER.setMachines(GTMachineUtils.registerTieredMachines(
                OIMod.OIREGISTRATE,
                ORIGINIUM_DEACTIVER.id,
                OIProcGenerator::new,
                (tier, builder) -> builder
                        .editableUI(OIProcGenerator.EDITABLE_UI_CREATOR.apply(OIMod.id(ORIGINIUM_DEACTIVER.id), OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeType))
                        .rotationState(RotationState.ALL)
                        .recipeType(OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeType)
//                        .simpleGeneratorModel(GTCEu.id("block/generators/"))
                        .model((ctx,prov,bdr)
                                ->OIBlockModelUtil.createPlaceHolderModel(ctx,prov,bdr,tier))
                        .tooltips(GTMachineUtils.workableTiered(tier, GTValues.V[tier], GTValues.V[tier] * 64, OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeType,
                                GTMachineUtils.genericGeneratorTankSizeFunction.applyAsInt(tier), false))
                        .register(),
                GTValues.tiersBetween(GTValues.IV,GTValues.UV)
        ));
        SUPER_ME_PATTERN_BUFFER.register(
                SuperMEPatternBufferPart::new,builder-> builder
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS,
                                PartAbility.EXPORT_ITEMS)
                        .rotationState(RotationState.ALL)
                        .colorOverlayTieredHullModel(GTCEu.id("block/overlay/appeng/me_buffer_hatch"))
                        .tooltips(Component.translatable("block.gtceu.pattern_buffer.desc.0"),
                                Component.translatable("block.gtceu.pattern_buffer.desc.1"),
                                Component.translatable("block.gtceu.pattern_buffer.desc.2"),
                                Component.translatable("gtceu.part_sharing.enabled"),
                                Component.translatableWithFallback(SUPER_ME_PATTERN_BUFFER.generateTooltipId(),
                                        " Max Slot : %d", SuperMEPatternBufferPart.getMaxPatternCount())
                        )
        );
    }

    public static void initTranslation(){
        ORIGINIUM_RELEASER.setLang("Originium Releaser")
                .setLang("zh_cn","简易源石能量释放装置")
                .langApply(GTValues.tiersBetween(GTValues.ULV,GTValues.EV));
        ORIGINIUM_DEACTIVER.setLang("Industry Originium Deactivator")
                .setLang("zh_cn","工业源石催化装置")
                .langApply(GTValues.tiersBetween(GTValues.IV,GTValues.UV));
        SUPER_ME_PATTERN_BUFFER.tooltipLang.setLang("Max Slot : %d%n")
                        .setLang("zh_cn","最大槽位 : %d%n");
        SUPER_ME_PATTERN_BUFFER.setLang("Super ME Pattern Buffer")
                .setLang("zh_cn","超级ME样板总成")
                .applyLang();
    }
    @Deprecated
    public static void initModel(){
        ORIGINIUM_RELEASER.initModels();
        ORIGINIUM_DEACTIVER.initModels();

    }
}
