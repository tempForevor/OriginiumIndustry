package com.apcp.originium_industry.data.machine.single.generator;

import com.google.common.collect.Tables;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.gui.editor.EditableMachineUI;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.WorkableTieredMachine;
import com.gregtechceu.gtceu.api.machine.feature.IEnvironmentalHazardEmitter;
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableEnergyContainer;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.modifier.ParallelLogic;
import com.gregtechceu.gtceu.api.recipe.ui.GTRecipeTypeUI;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;

public class OriginiumReleaser extends WorkableTieredMachine implements IFancyUIMachine, IEnvironmentalHazardEmitter {

    public OriginiumReleaser(IMachineBlockEntity holder, int tier,Object... args){
        super(holder, tier, GTMachineUtils.genericGeneratorTankSizeFunction, args);

    }

    @Override
    public float getHazardStrengthPerOperation() {
        return 0.1f;
    }

    @Override
    protected @NotNull NotifiableEnergyContainer createEnergyContainer(Object @NotNull ... args) {
        var energyContainer = super.createEnergyContainer(args);
        energyContainer.setSideOutputCondition(side -> !hasFrontFacing() || side == getFrontFacing());
        return energyContainer;
    }

    @Override
    protected boolean isEnergyEmitter() {
        return true;
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }

    @Override
    public @Nullable GTRecipe doModifyRecipe(GTRecipe recipe) {
        var EUt = recipe.getOutputEUt().getTotalEU();
        if(EUt <= 0) {
            return GTRecipeModifiers.OC_PERFECT_SUBTICK.applyModifier(this, recipe);
        }
        int max_parallel = (int)((getMaxVoltage() / EUt) % Integer.MAX_VALUE);
        var parallel = ParallelLogic.getParallelAmountFast(this,recipe,max_parallel);

        return recipe.copy(ContentModifier.multiplier(parallel),false);
    }

    @Override
    public boolean canVoidRecipeOutputs(RecipeCapability<?> capability) {
        return capability != EURecipeCapability.CAP;
    }

    @Override
    public void afterWorking() {
        super.afterWorking();
        spreadEnvironmentalHazard();
    }

    @Override
    public long getDisplayRecipeVoltage() {
        return GTValues.V[this.tier];
    }

    @SuppressWarnings("UnstableApiUsage")
    public static BiFunction<ResourceLocation, GTRecipeType, EditableMachineUI> EDITABLE_UI_CREATOR = Util
            .memoize((path,recipeType) -> new EditableMachineUI("generator", path, () -> {
                WidgetGroup template = recipeType.getRecipeUI().createEditableUITemplate(false, false).createDefault();
                WidgetGroup group = new WidgetGroup(0, 0, template.getSize().width + 4 + 8,
                        template.getSize().height + 8);
                Size size = group.getSize();
                template.setSelfPosition(new Position(
                        (size.width - 4 - template.getSize().width) / 2 + 4,
                        (size.height - template.getSize().height) / 2));
                group.addWidget(template);
                return group;
            }, (template, machine) -> {
                if (machine instanceof OriginiumReleaser generatorMachine) {
                    var storages = Tables.newCustomTable(new EnumMap<>(IO.class),
                            LinkedHashMap<RecipeCapability<?>, Object>::new);
                    storages.put(IO.IN, ItemRecipeCapability.CAP, generatorMachine.importItems.storage);
                    storages.put(IO.OUT, ItemRecipeCapability.CAP, generatorMachine.exportItems.storage);
                    storages.put(IO.IN, FluidRecipeCapability.CAP, generatorMachine.importFluids);
                    storages.put(IO.OUT, FluidRecipeCapability.CAP, generatorMachine.exportFluids);

                    generatorMachine.getRecipeType().getRecipeUI().createEditableUITemplate(false, false).setupUI(
                            template,
                            new GTRecipeTypeUI.RecipeHolder(generatorMachine.recipeLogic::getProgressPercent,
                                    storages,
                                    new CompoundTag(),
                                    Collections.emptyList(),
                                    false, false));
                    createEnergyBar().setupUI(template, generatorMachine);
                }
            }));

}
