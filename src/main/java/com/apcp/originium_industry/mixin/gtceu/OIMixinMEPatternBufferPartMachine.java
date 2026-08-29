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

    @Unique
    private static final int OI$MaxPatternCount = 27;

    @Final
    @Mutable
    @Shadow(remap = false)
    protected static int MAX_PATTERN_COUNT = OI$MaxPatternCount;
}
