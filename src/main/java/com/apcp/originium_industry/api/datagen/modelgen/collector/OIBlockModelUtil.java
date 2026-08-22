package com.apcp.originium_industry.api.datagen.modelgen.collector;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.registry.registrate.provider.GTBlockstateProvider;
import com.gregtechceu.gtceu.data.model.builder.MachineModelBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class OIBlockModelUtil {
    public static void createPlaceHolderModel(@NotNull DataGenContext<Block, ? extends Block> ctx,
                                              @NotNull GTBlockstateProvider prov,
                                              @NotNull MachineModelBuilder<@NotNull BlockModelBuilder> modelBuilder,int tier){
        var model = prov.models()
                .withExistingParent(ctx.getName(), OIBlockModelUtil.getTieredCasingParent(tier))
                .texture("overlay_front", OIBlockModelUtil.getOverlayPlaceHolder()[0])
                .texture("overlay_front_emissive", OIBlockModelUtil.getOverlayPlaceHolder()[1]);
        modelBuilder.addReplaceableTextures("bottom", "top", "side")
                .forAllStatesModels((state)-> model);
    }

    public static ResourceLocation getTieredCasingParent(int tier){
        return GTCEu.id("block/casings/voltage/" + GTValues.VN[tier].toLowerCase(Locale.ROOT));
    }
    public static ResourceLocation[] getOverlayPlaceHolder(){
        return new ResourceLocation[]{GTCEu.id("block/multiblock/data_bank/overlay_front_active"),
                GTCEu.id("block/multiblock/data_bank/overlay_front_active_emissive")};
    }
}
