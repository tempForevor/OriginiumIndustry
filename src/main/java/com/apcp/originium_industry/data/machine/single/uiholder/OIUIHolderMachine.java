package com.apcp.originium_industry.data.machine.single.uiholder;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IUIMachine;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import net.minecraft.world.entity.player.Player;
import java.util.function.Function;

public class OIUIHolderMachine extends MetaMachine implements IUIMachine {
    public Function<ModularUI, ModularUI> uiProvider;

    public OIUIHolderMachine(IMachineBlockEntity holder,Function<ModularUI, ModularUI> uiProvider) {
        super(holder);
        this.uiProvider = uiProvider;
    }

    @Override
    public ModularUI createUI(Player player) {
        return uiProvider.apply(new ModularUI(this,player));
    }
}
