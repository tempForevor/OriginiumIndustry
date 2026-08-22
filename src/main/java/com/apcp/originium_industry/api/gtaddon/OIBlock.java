package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.LangModel;
import com.gregtechceu.gtceu.common.data.models.GTModels;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import lombok.Getter;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class OIBlock {
    public String id;
    public BlockEntry<Block> block;
    @Getter
    public LangModel lang = new LangModel();
    @Getter
    public LangModel tooltipLang = new LangModel();

    public OIBlock(String id) {
        this.id = id;
    }

    public OIBlock setBlock(BlockEntry<Block> block) {
        this.block = block;
        return this;
    }

    public OIBlock applyLang(){
        lang.apply((k,v)-> LangDataGenerator.normal.getCollector(k).addTranslation("block."+ OIMod.MOD_ID+"."+id,v));
        tooltipLang.apply((k,v)-> LangDataGenerator.normal.getCollector(k).addTranslation("block."+ OIMod.MOD_ID+"."+id+".tooltip",v));
        return this;
    }

    public Builder build(){
        return new Builder(id);
    }

    public static class Builder {
        public String id;

        public Builder(String id){
            this.id = id;
        }

        public BlockEntry<Block> createCasingBlock(ResourceLocation texture) {
            return createCasingBlock(Block::new, texture, () -> Blocks.IRON_BLOCK,
                    () -> RenderType::solid);
        }

        public BlockEntry<Block> createCasingBlock(NonNullFunction<BlockBehaviour.Properties, Block> blockSupplier,
                                                          ResourceLocation texture,
                                                          NonNullSupplier<? extends Block> properties,
                                                          Supplier<Supplier<RenderType>> type) {
            return OIMod.OIREGISTRATE.block(id, blockSupplier)
                    .initialProperties(properties)
                    .properties(p -> p.isValidSpawn((state, level, pos, ent) -> false))
                    .addLayer(type)
                    .exBlockstate(GTModels.cubeAllModel(texture))
                    .tag(CustomTags.MINEABLE_WITH_CONFIG_VALID_PICKAXE_WRENCH)
                    .item(BlockItem::new)
                    .build()
                    .register();
        }
    }
}
