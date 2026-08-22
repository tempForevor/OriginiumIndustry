package com.apcp.originium_industry.api.datagen.modelgen;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.modelgen.collector.OIBlockModelCollector;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
@Deprecated
public class OIBlockModelGenerator extends BlockStateProvider {

    public static OIBlockModelCollector collector = new OIBlockModelCollector();

    public OIBlockModelGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), OIMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        collector.generate(this);
        collector.models.forEach(this::simpleBlockWithItem);
    }
}
