package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.LangModel;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Consumer;

public class OIRecipeType {

    public ResourceLocation location;

    public String group;

    public GTRecipeType recipeType;

    public LangModel lang = new LangModel();

    public OIRecipeType(ResourceLocation location,String group) {
        this.location = location;
        this.group = group;
    }

    public OIRecipeType register(Consumer<GTRecipeType> consumer, RecipeType<?>... proxyRecipes) {
        var recipeType = new GTRecipeType(location, group, proxyRecipes);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        consumer.accept(recipeType);
        return setRecipeType(recipeType);
    }

    public OIRecipeType setRecipeType(GTRecipeType recipeType){
        this.recipeType = recipeType;
        return this;
    }

    public GTRecipeBuilder recipeBuilder(ResourceLocation id) {
        return recipeType.recipeBuilder(id);
    }

    public GTRecipeBuilder recipeBuilder(String id){
        return recipeBuilder(OIMod.id(id));
    }

    public OIRecipeType setLang(String value){
        lang.setLang(value);
        return this;
    }

    public OIRecipeType setLang(String locale,String value){
        lang.setLang(locale,value);
        return this;
    }

    public OIRecipeType langApply() {
        lang.apply((k,v)-> LangDataGenerator.normal.getCollector(k).addTranslation(location.toLanguageKey(),v));
        return this;
    }
}
