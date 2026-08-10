package com.apcp.originium_industry.data.recipe;

import com.apcp.originium_industry.data.recipe.gtceu.OIGTExtendRecipe;
import com.apcp.originium_industry.data.recipe.originium_industry.OIExtendRecipe;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class OIRecipes {

    public static void init(Consumer<FinishedRecipe> provider){
        OIGTExtendRecipe.init(provider);
        OIExtendRecipe.init(provider);
    }
}
