package com.apcp.originium_industry.api.datagen.modelgen;

import com.apcp.originium_industry.OIMod;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class OIItemModelGenerator extends ItemModelProvider {

    public static List<Item> items = new ArrayList<>();

    public OIItemModelGenerator(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen.getPackOutput(), OIMod.MOD_ID, existingFileHelper);
    }
    @Override
    protected void registerModels() {
        items.forEach(this::handheld);
    }

    public String name(NonNullSupplier<? extends ItemLike> item) {
        return ForgeRegistries.ITEMS.getKey(item.get().asItem()).getPath();
    }

    public ResourceLocation itemTexture(NonNullSupplier<? extends ItemLike> item) {
        return modLoc("item/" + name(item));
    }

    public ItemModelBuilder handheld(Item item){
        return handheld(()->item);
    }

    public ItemModelBuilder handheld(NonNullSupplier<? extends ItemLike> item) {
        return handheld(item, itemTexture(item));
    }

    public ItemModelBuilder handheld(NonNullSupplier<? extends ItemLike> item, ResourceLocation texture) {
        return withExistingParent(name(item), "item/handheld").texture("layer0", texture);
    }
}
