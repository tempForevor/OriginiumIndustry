package com.apcp.originium_industry.mixin.gtceu;

import com.apcp.originium_industry.api.gtaddon.OIParallelUtil;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ParallelHatchPartMachine;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import lombok.Getter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParallelHatchPartMachine.class)
public class OIMixinParallelHatchPartMachine {

    @Final
    @Mutable
    @Shadow(remap = false)
    private int maxParallel;

    @Shadow(remap = false)
    @Persisted
    @Getter
    private int currentParallel = 1;

    @Inject(method = "<init>",at=@At("TAIL"),remap = false)
    public void ParallelHatchPartMachine(IMachineBlockEntity holder, int tier, CallbackInfo ci){
        this.maxParallel = OIParallelUtil.normalMaxParallel(tier);
        this.currentParallel = maxParallel;
    }
}

