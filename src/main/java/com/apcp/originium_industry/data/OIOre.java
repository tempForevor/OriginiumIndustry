package com.apcp.originium_industry.data;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.data.material.OIElementMaterial;
import com.gregtechceu.gtceu.api.data.worldgen.GTLayerPattern;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.WorldGenLayers;
import com.gregtechceu.gtceu.api.data.worldgen.generator.indicators.SurfaceIndicatorGenerator;
import com.gregtechceu.gtceu.common.data.GTOres;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class OIOre{

    public static GTOreDefinition ORIGINIUM_VEIN = create(OIMod.id("originium_vein"), vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.4f).weight(40)
            .layer(WorldGenLayers.STONE)
            .heightRangeUniform(40, 80)
            .biomes(BiomeTags.IS_OVERWORLD)
            .layeredVeinGenerator(generator -> generator
                    .withLayerPattern(() -> GTLayerPattern.builder()
                            .layer(l -> l.weight(2).state(Blocks.STONE::defaultBlockState).size(2, 4))
                            .layer(l -> l.weight(1).mat(Coal).size(1, 1))
                            .layer(l -> l.weight(3).mat(OIElementMaterial.ActiveOriginium.material).size(3, 5))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(OIElementMaterial.ActiveOriginium.material)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    public static void init(){

    }

    public static GTOreDefinition create(ResourceLocation location, @NotNull Consumer<GTOreDefinition> config){
        return GTOres.create(location,config);
    }
}
