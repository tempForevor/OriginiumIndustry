package com.apcp.originium_industry.data.recipe.gtceu;


import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.data.material.OIElementMaterial;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;


public class OIGTExtendRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.LASER_ENGRAVER_RECIPES.recipeBuilder(OIMod.id("activating_originium"))
                .EUt(GTValues.V[GTValues.UEV],1)
                .duration(20*20)
                .inputItems(TagPrefix.dust,OIElementMaterial.Originium.material,1)
                .outputItems(TagPrefix.gemExquisite,OIElementMaterial.ActiveOriginium.material,1)
//                .notConsumable(TagPrefix.lens, MarkerMaterials.Color.Blue,1)
                .notConsumable(GTItems.FIELD_GENERATOR_UV.asStack(1))
                .save(provider);
    }
}
