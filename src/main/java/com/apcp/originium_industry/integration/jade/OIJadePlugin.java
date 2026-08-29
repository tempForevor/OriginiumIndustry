package com.apcp.originium_industry.integration.jade;

import com.apcp.originium_industry.integration.jade.provider.SuperMEPatternBufferProvider;
import com.apcp.originium_industry.integration.jade.provider.SuperMEPatternBufferProxyProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class OIJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new SuperMEPatternBufferProvider(), BlockEntity.class);
        registration.registerBlockDataProvider(new SuperMEPatternBufferProxyProvider(), BlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(new SuperMEPatternBufferProvider(), Block.class);
        registration.registerBlockComponent(new SuperMEPatternBufferProxyProvider(), Block.class);
    }
}
