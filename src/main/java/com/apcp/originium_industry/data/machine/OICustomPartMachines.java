package com.apcp.originium_industry.data.machine;

import com.apcp.originium_industry.api.gtaddon.OIMachineDef;
import com.apcp.originium_industry.commmon.machine.part.SuperMEPatternBufferPart;
import com.apcp.originium_industry.commmon.machine.part.SuperMEPatternBufferProxyPart;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import net.minecraft.network.chat.Component;

public class OICustomPartMachines {
    public static OIMachineDef SUPER_ME_PATTERN_BUFFER = new OIMachineDef("super_me_pattern_buffer");
    public static OIMachineDef SUPER_ME_PATTERN_BUFFER_PROXY = new OIMachineDef("super_me_pattern_buffer_proxy");

    public static void init(){
        SUPER_ME_PATTERN_BUFFER.register(
                SuperMEPatternBufferPart::new, builder-> builder
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS,
                                PartAbility.EXPORT_ITEMS)
                        .tier(GTValues.MAX)
                        .colorOverlayTieredHullModel(GTCEu.id("block/overlay/appeng/me_buffer_hatch"))
                        .tooltips(Component.translatable("block.gtceu.pattern_buffer.desc.0"),
                                Component.translatable("block.gtceu.pattern_buffer.desc.1"),
                                Component.translatable("block.gtceu.pattern_buffer.desc.2"),
                                Component.translatable("gtceu.part_sharing.enabled"),
                                Component.translatableWithFallback(SUPER_ME_PATTERN_BUFFER.generateTooltipId(),
                                        " Max Slot : %d", SuperMEPatternBufferPart.getMaxPatternCount())
                        )
        );
        SUPER_ME_PATTERN_BUFFER_PROXY.register(
                SuperMEPatternBufferProxyPart::new, builder-> builder
                        .rotationState(RotationState.ALL)
                        .abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS,
                                PartAbility.EXPORT_ITEMS)
                        .tier(GTValues.MAX)
                        .colorOverlayTieredHullModel(GTCEu.id("block/overlay/appeng/me_buffer_hatch_proxy"))
                        .tooltips(
                                Component.translatable("block.gtceu.pattern_buffer_proxy.desc.0"),
                                Component.translatable("block.gtceu.pattern_buffer_proxy.desc.1"),
                                Component.translatable("block.gtceu.pattern_buffer_proxy.desc.2"),
                                Component.translatable("gtceu.part_sharing.enabled"),
                                Component.translatableWithFallback(SUPER_ME_PATTERN_BUFFER.generateTooltipId(),
                                        " Max Slot : %d", SuperMEPatternBufferPart.getMaxPatternCount())
                        )
        );
    }
    public static void initTranslation(){
        SUPER_ME_PATTERN_BUFFER.tooltipLang.setLang("Max Slot : %d")
                .setLang("zh_cn","最大槽位 : %d");
        SUPER_ME_PATTERN_BUFFER.setLang("Super ME Pattern Buffer")
                .setLang("zh_cn","超级ME样板总成")
                .applyLang();
        SUPER_ME_PATTERN_BUFFER_PROXY.setLang("Super ME Pattern Buffer Proxy")
                .setLang("zh_cn","超级ME样板总成镜像")
                .applyLang();
    }
}
