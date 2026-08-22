package com.apcp.originium_industry.mixin.gtceu;

import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;
import com.gregtechceu.gtceu.integration.ae2.machine.MEPatternBufferPartMachine;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import lombok.Getter;
import lombok.Setter;
import org.spongepowered.asm.mixin.*;

@Mixin(MEPatternBufferPartMachine.class)
public class OIMixinMEPatternBufferPartMachine {

    @Setter
    @Getter
    @Unique
    private static int OI$MaxPatternCount = 27;

    @Final
    @Mutable
    @Shadow(remap = false)
    @Setter
    @Getter
    protected static int MAX_PATTERN_COUNT = OI$MaxPatternCount;

    @Final
    @Mutable
    @Shadow(remap = false)
    @Getter
    @Persisted
    @DescSynced
    private final CustomItemStackHandler patternInventory = new CustomItemStackHandler(OI$MaxPatternCount);

}
