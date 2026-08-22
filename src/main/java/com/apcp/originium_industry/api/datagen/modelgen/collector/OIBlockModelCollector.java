package com.apcp.originium_industry.api.datagen.modelgen.collector;

import com.gregtechceu.gtceu.data.model.builder.MachineModelBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.HashMap;
import java.util.Map;
@Deprecated
public class OIBlockModelCollector {
    public Map<Block, ModelFile> models = new HashMap<>();

    public Map<Block, OIBlockModelInfo> modelGenerators = new HashMap<>();

    public void generate(BlockStateProvider blockStateProvider) {
        models.clear();
        modelGenerators.forEach((block, info) -> {
                var builder = blockStateProvider.models().withExistingParent(info.name,info.parent);
                info.textures.forEach(builder::texture);
                if(info.isMachine){
                    builder.customLoader(
                            MachineModelBuilder.begin(info.definition)
                    );
                }
                models.put(block,builder);
            }
        );
    }
}
