package com.apcp.originium_industry.commmon.cover;

import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import net.minecraft.core.Direction;

@Deprecated
public class VirtualItemProviderCover extends CoverBehavior {

    public VirtualItemProviderCover(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
    }
}
