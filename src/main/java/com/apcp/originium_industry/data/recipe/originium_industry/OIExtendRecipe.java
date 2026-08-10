package com.apcp.originium_industry.data.recipe.originium_industry;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class OIExtendRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        OIGeneratorRecipes.init(provider);
        OIOriProcRecipes.init(provider);
    }
}
