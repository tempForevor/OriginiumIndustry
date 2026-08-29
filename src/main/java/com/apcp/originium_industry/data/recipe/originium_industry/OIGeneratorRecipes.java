package com.apcp.originium_industry.data.recipe.originium_industry;

import com.apcp.originium_industry.data.material.OIElementMaterial;
import com.apcp.originium_industry.data.material.OIOriProcMaterial;
import com.apcp.originium_industry.commmon.recipe_type.OICustomRecipeType;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTItems;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class OIGeneratorRecipes {

    public static void init(Consumer<FinishedRecipe> consumer){
        OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeBuilder("ulv_active_originium")
                .inputItems(TagPrefix.dust, OIElementMaterial.ActiveOriginium.material)
                .outputItems(TagPrefix.dust, OIOriProcMaterial.IMPURE_ORIGINIUM.material)
                .duration(20*20)
                .EUt(-GTValues.V[GTValues.ULV],3)
                .save(consumer);
        OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeBuilder("lv_active_originium")
                .inputItems(TagPrefix.gem, OIElementMaterial.ActiveOriginium.material)
                .outputItems(TagPrefix.dust,OIOriProcMaterial.IMPURE_ORIGINIUM.material,2)
                .duration(20*20)
                .EUt(-GTValues.V[GTValues.LV],3)
                .save(consumer);
        OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeBuilder("mv_active_originium")
                .inputItems(TagPrefix.gemFlawless, OIElementMaterial.ActiveOriginium.material)
                .notConsumable(GTItems.FIELD_GENERATOR_LV.asStack(1))
                .outputItems(TagPrefix.dust,OIOriProcMaterial.IMPURE_ORIGINIUM.material,3)
                .duration(20*20)
                .EUt(-GTValues.V[GTValues.MV],3)
                .save(consumer);
        OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeBuilder("hv_active_originium")
                .inputItems(TagPrefix.gemExquisite, OIElementMaterial.ActiveOriginium.material)
                .notConsumable(GTItems.FIELD_GENERATOR_MV.asStack(1))
                .outputItems(TagPrefix.dust,OIOriProcMaterial.IMPURE_ORIGINIUM.material,4)
                .duration(20*20)
                .EUt(-GTValues.V[GTValues.HV],3)
                .save(consumer);
        OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeBuilder("ev_active_originium")
                .inputItems(TagPrefix.gemExquisite, OIElementMaterial.ActiveOriginium.material)
                .notConsumable(GTItems.FIELD_GENERATOR_HV.asStack(1))
                .outputItems(TagPrefix.dust,OIOriProcMaterial.IMPURE_ORIGINIUM.material,5)
                .duration(20*20)
                .EUt(-GTValues.V[GTValues.EV],3)
                .save(consumer);
    }
}
