package com.apcp.originium_industry.data.machine.part;

import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.gui.widget.IntInputWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ParallelHatchPartMachine;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SuperParallelHatch extends MultiblockPartMachine implements IParallelHatch {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            SuperParallelHatch.class, MultiblockPartMachine.MANAGED_FIELD_HOLDER);
    private static final int MIN_PARALLEL = 1;

    private final int maxParallel;

    @Persisted
    @Getter
    private int currentParallel;

    public SuperParallelHatch(IMachineBlockEntity machineBlockEntity) {
        super(machineBlockEntity);
        maxParallel = Integer.MAX_VALUE;
        currentParallel = maxParallel;
    }

    public void setCurrentParallel(int parallelAmount) {
        this.currentParallel = Mth.clamp(parallelAmount, MIN_PARALLEL, this.maxParallel);
        for (IMultiController controller : this.getControllers()) {
            if (controller instanceof IRecipeLogicMachine rlm) {
                rlm.getRecipeLogic().markLastRecipeDirty();
            }
        }
    }

    @Override
    public Widget createUIWidget() {
        WidgetGroup parallelAmountGroup = new WidgetGroup(0, 0, 100, 20);
        parallelAmountGroup.addWidget(new IntInputWidget(this::getCurrentParallel, this::setCurrentParallel)
                .setMin(MIN_PARALLEL)
                .setMax(maxParallel));

        return parallelAmountGroup;
    }

    @Override
    @NotNull
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public boolean canShared() {
        return false;
    }
}
