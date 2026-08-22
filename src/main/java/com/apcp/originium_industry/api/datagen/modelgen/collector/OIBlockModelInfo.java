package com.apcp.originium_industry.api.datagen.modelgen.collector;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
@Deprecated
@Accessors(fluent = true)
@Setter
@Getter
public class OIBlockModelInfo {
    public String name;
    public ResourceLocation parent;
    public Map<String, ResourceLocation> textures = new HashMap<>();

    public boolean isMachine = false;
    public MachineDefinition definition;

    public OIBlockModelInfo addTexture(String key,ResourceLocation texture){
        textures.put(key,texture);
        return this;
    }

    public static OIBlockModelInfo fromMachine(MachineDefinition definition) {
        return new OIBlockModelInfo()
                .name(definition.getDescriptionId())
                .isMachine(true)
                .definition(definition);
    }
    public static OIBlockModelInfo buildTieredMachine(MachineDefinition definition,int tier,ResourceLocation overlay,ResourceLocation emissive) {
        return fromMachine(definition)
                .parent(GTCEu.id("block/casings/voltage/"+ GTValues.VN[tier].toLowerCase(Locale.ROOT)))
                .addTexture("overlay_front",overlay)
                .addTexture("overlay_front_emissive",emissive);
    }
    public static OIBlockModelInfo buildTieredMachine(MachineDefinition definition,ResourceLocation overlay,ResourceLocation emissive) {
        return buildTieredMachine(definition,definition.getTier(),overlay,emissive);
    }
    public static OIBlockModelInfo buildTieredPlaceholderMachine(MachineDefinition definition,int tier) {
        return OIBlockModelInfo.buildTieredMachine(definition,tier,
                GTCEu.id("block/overlay/machine/overlay_screen"),
                GTCEu.id("block/overlay/machine/overlay_screen_emissive"));
    }
    public static OIBlockModelInfo buildTieredPlaceholderMachine(MachineDefinition definition){
        return buildTieredPlaceholderMachine(definition,definition.getTier());
    }
    public static OIBlockModelInfo buildCube(Block block,ResourceLocation cubeTexture) {
        return new OIBlockModelInfo()
                .name(block.getDescriptionId())
                .isMachine(false)
                .parent(ResourceLocation.tryBuild("minecraft","block/cube_all"))
                .addTexture("all",cubeTexture);
    }
}
