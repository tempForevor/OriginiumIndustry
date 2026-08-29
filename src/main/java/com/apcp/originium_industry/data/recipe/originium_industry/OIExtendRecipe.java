package com.apcp.originium_industry.data.recipe.originium_industry;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.data.item.OIItems;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.Locale;
import java.util.function.Consumer;

public class OIExtendRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        for(var tier : GTValues.ALL_TIERS){
            GTRecipeTypes.PACKER_RECIPES.recipeBuilder(OIMod.id(GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_universal_circuit_pack"))
                    .inputItems(CustomTags.CIRCUITS_ARRAY[tier],1)
//                    .inputItems(GTCraftingComponents.CIRCUIT.get(tier),1)
                    .outputItems(OIItems.UNIVERSAL_CIRCUIT[tier].item.asStack(1))
                    .EUt(GTValues.V[GTValues.ULV],1)
                    .duration(1)
                    .circuitMeta(23)
                    .save(provider);
        }

        OIGeneratorRecipes.init(provider);
        OIOriProcRecipes.init(provider);
        OIPartsRecipes.init(provider);
    }
}
