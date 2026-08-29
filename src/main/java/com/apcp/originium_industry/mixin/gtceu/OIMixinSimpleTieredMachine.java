package com.apcp.originium_industry.mixin.gtceu;

import com.apcp.originium_industry.commmon.item.VirtualItemBehavior;
import com.apcp.originium_industry.commmon.machine.trait.VirtualCircuitItemFilterHandler;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.SimpleTieredMachine;
import com.gregtechceu.gtceu.api.machine.WorkableTieredMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleTieredMachine.class)
public class OIMixinSimpleTieredMachine extends WorkableTieredMachine {

    private OIMixinSimpleTieredMachine(IMachineBlockEntity holder, int tier, Int2IntFunction tankScalingFunction,
                                       Object... args) {
        super(holder, tier, tankScalingFunction, args);
    }

    @Inject(method = "createCircuitItemHandler",at=@At("HEAD"),remap=false,cancellable=true)
    protected void createCircuitItemHandler(Object[] args, CallbackInfoReturnable<NotifiableItemStackHandler> cir) {
        cir.setReturnValue(new VirtualCircuitItemFilterHandler(this).setFilter(
                VirtualItemBehavior::isCircuitVirtualItem
        ));
    }

    @Override
    protected @NotNull NotifiableItemStackHandler createImportItemHandler(Object @NotNull... args) {
        return new NotifiableItemStackHandler(this, getRecipeType().getMaxInputs(ItemRecipeCapability.CAP), IO.IN).setFilter(i -> !VirtualItemBehavior.isCircuitVirtualItem(i));
    }
}
