package com.apcp.originium_industry.data.machine;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.gtaddon.OIParallelUtil;
import com.apcp.originium_industry.commmon.machine.part.SuperParallelHatch;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.common.data.machines.GCYMMachines;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ParallelHatchPartMachine;
import net.minecraft.network.chat.Component;

import java.util.Locale;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.GTValues.MAX;
import static com.gregtechceu.gtceu.api.GTValues.VN;
import static com.gregtechceu.gtceu.api.GTValues.VNF;
import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FORMED;
import static com.gregtechceu.gtceu.common.data.machines.GTMachineUtils.registerTieredMachines;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.createWorkableTieredHullMachineModel;

public class OIGCYMMachines {
    public static MachineDefinition[] PARALLEL_HATCH;
    public static MachineDefinition SUPER_PARALLEL_HATCH;

    public static void init() {
        if(OIMod.isDataGen())return;
        GCYMMachines.init();

        PARALLEL_HATCH = registerTieredMachines(OIMod.OIREGISTRATE,"parallel_hatch",
                ParallelHatchPartMachine::new,
                (tier, builder) -> builder
                        .langValue(switch (tier) {
                            case 5 -> "IV";
                            case 6 -> "LuV";
                            case 7 -> "ZPM";
                            case 8 -> "UV";
                            case 9 -> "UHV";
                            case 10 -> "UEV";
                            case 11 -> "UIV";
                            case 12 -> "UXV";
                            case 13 -> "OPV";
                            case 14 -> "MAX";
                            default -> "Super"; // Should never be hit.
                        } + " Parallel Control Hatch")
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.PARALLEL_HATCH)
                        .modelProperty(IS_FORMED, false)
                        .modelProperty(GTMachineModelProperties.RECIPE_LOGIC_STATUS, RecipeLogic.Status.IDLE)
                        .model(createWorkableTieredHullMachineModel(
                                OIMod.id("block/machine/part/" + VN[tier].toLowerCase(Locale.ROOT) + "_parallel_hatch"))
                                .andThen((ctx, prov, model) -> model
                                        .addReplaceableTextures("bottom", "top", "side")))
                        .tooltips(Component.translatable("gtceu.machine.parallel_hatch_mk_" + tier + ".tooltip",OIParallelUtil.normalMaxParallel(tier)),
                                Component.translatable("gtceu.part_sharing.disabled"))
                        .register(),
                GTValues.tiersBetween(UHV,MAX));

        for(int i=0;i<GCYMMachines.PARALLEL_HATCH.length;i++){
            var machine = GCYMMachines.PARALLEL_HATCH[i];
            if(machine!=null){
                int finalI = i;
                machine.setTooltipBuilder(
                        (item,builder) -> {
                            builder.add(
                                    Component.translatable("gtceu.machine.parallel_hatch_mk_" + (finalI) + ".tooltip",OIParallelUtil.normalMaxParallel(finalI))
                            );
                            builder.add(Component.translatable("gtceu.part_sharing.disabled"));
                        }
                );
            }
        }

        SUPER_PARALLEL_HATCH = OIMod.OIREGISTRATE
                .machine("super_parallel_hatch", SuperParallelHatch::new)
                .rotationState(RotationState.ALL)
                .abilities(PartAbility.PARALLEL_HATCH)
                .modelProperty(IS_FORMED, false)
                .tooltips(Component.translatable("originium_industry.machine.super_parallel_hatch.tooltip"),Component.translatable("gtceu.part_sharing.disabled"))
                .model(createWorkableTieredHullMachineModel(
                        OIMod.id("block/machine/part/super_parallel_hatch"))
                        .andThen((ctx, prov, model) -> model.addReplaceableTextures("bottom", "top", "side")))
                .tier(MAX)
                .register();
    }

    public static void initTranslation(){
        for(var tier : GTValues.tiersBetween(IV,MAX)){
            LangDataGenerator.normal.getCollector("en_us")
                    .addTranslation("block.originium_industry." + VN[tier].toLowerCase(Locale.ROOT) + "_parallel_hatch",
                            VNF[tier] + " Parallel Hatch");
            LangDataGenerator.normal.getCollector("en_us")
                    .addTranslation("gtceu.machine.parallel_hatch_mk_"+tier+".tooltip",
                            "Max Parallel : %d");
            LangDataGenerator.normal.getCollector("zh_cn")
                    .addTranslation("block.originium_industry." + VN[tier].toLowerCase(Locale.ROOT) + "_parallel_hatch",
                            VNF[tier] + " 并行控制仓");
            LangDataGenerator.normal.getCollector("zh_cn")
                    .addTranslation("gtceu.machine.parallel_hatch_mk_"+tier+".tooltip",
                            "最大并行数 : %d");

        }
        LangDataGenerator.normal.getCollector("en_us")
                .addTranslation("block.originium_industry.super_parallel_hatch","Super Parallel Hatch");
        LangDataGenerator.normal.getCollector("zh_cn")
                .addTranslation("block.originium_industry.super_parallel_hatch","超级并行控制仓");
        LangDataGenerator.normal.getCollector("zh_cn")
                .addTranslation("originium_industry.machine.super_parallel_hatch.tooltip","最大并行数 : "+ Integer.MAX_VALUE);
        LangDataGenerator.normal.getCollector("en_us")
                .addTranslation("originium_industry.machine.super_parallel_hatch.tooltip","Max Parallel : "+ Integer.MAX_VALUE);
    }

}
