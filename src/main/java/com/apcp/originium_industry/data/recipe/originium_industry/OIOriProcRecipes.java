package com.apcp.originium_industry.data.recipe.originium_industry;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.data.block.OICustomBlocks;
import com.apcp.originium_industry.data.item.OIOriProcItems;
import com.apcp.originium_industry.data.material.OIElementMaterial;
import com.apcp.originium_industry.data.material.OIOriProcMaterial;
import com.apcp.originium_industry.data.recipe_type.OICustomRecipeType;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class OIOriProcRecipes {
    public static void init(Consumer<FinishedRecipe> consumer){
        // Proc 1
        OICustomRecipeType.ORIGINIUM_ENERGY_RELEASE.recipeBuilder("ev_originium_proc")
                .inputItems(TagPrefix.gemExquisite, OIElementMaterial.ActiveOriginium.material)
                .notConsumable(GTItems.FIELD_GENERATOR_EV.asStack(1))
                .outputItems(TagPrefix.dust,OIOriProcMaterial.IMPURE_ORIGINIUM.material,4)
                .outputItems(TagPrefix.dust,OIOriProcMaterial.HALF_IMPURE_ORIGINIUM.material,1)
                .duration(20*20)
                .EUt(-GTValues.V[GTValues.EV],1)
                .save(consumer);

        OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeBuilder("fluorine_originium_catalyse")
                .inputItems(TagPrefix.gemExquisite, OIElementMaterial.ActiveOriginium.material)
                .notConsumable(GTItems.FIELD_GENERATOR_IV.asStack(1))
                .outputItems(TagPrefix.dust, OIOriProcMaterial.IMPURE_ORIGINIUM.material,10)
                .inputFluids(GTMaterials.Fluorine,1000)
                .outputFluids(OIOriProcMaterial.ORIGINIUM_CATALYZED_FLUORINATED_MIXTURE.material.getFluid(1000))
                .EUt(-GTValues.V[GTValues.IV],1)
                .duration(20*20)
                .save(consumer);

        OICustomRecipeType.ORIGINIUM_DEACTIVATE.recipeBuilder("fluorine_originium_catalyse_field")
                .inputItems(TagPrefix.gemExquisite, OIElementMaterial.ActiveOriginium.material)
                .inputItems(GTItems.FIELD_GENERATOR_UV,1)
                .outputItems(TagPrefix.dust,OIOriProcMaterial.IMPURE_ORIGINIUM.material,64)
                .outputItems(OIOriProcItems.ACTIVE_ORIGINIUM_MIXED_UV_FIELD.item.asStack(1))
                .inputFluids(GTMaterials.Fluorine,1000)
                .outputFluids(OIOriProcMaterial.ORIGINIUM_CATALYZED_FLUORINATED_MIXTURE.material.getFluid(1000))
                .EUt(-GTValues.V[GTValues.LuV],1)
                .duration(20*20)
                .save(consumer);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(OIMod.id("extractor_iv_proc"))
                .inputItems(TagPrefix.gem,OIElementMaterial.ActiveOriginium.material,16)
                .inputItems(TagPrefix.block,GTMaterials.Coal,32)
                .inputFluids(GTMaterials.Water,8000)
                .circuitMeta(23)
                .outputItems(OIOriProcItems.ORIGINIUM_WELL_EXTRACTOR.item.asStack(1))
                .duration(20*5)
                .EUt(GTValues.V[GTValues.IV],1)
                .save(consumer);

        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder(OIMod.id("originium_first_extract_proc"))
                .inputItems(OIOriProcItems.ORIGINIUM_WELL_EXTRACTOR.item.asStack(1))
                .inputFluids(OIOriProcMaterial.ORIGINIUM_CATALYZED_FLUORINATED_MIXTURE.material.getFluid(1000))
                .outputItems(TagPrefix.dust,OIOriProcMaterial.HALF_IMPURE_ORIGINIUM.material,32)
                .duration(20*25)
                .EUt(GTValues.V[GTValues.IV],1)
                .save(consumer);

        // TODO: Placeholder
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(OIMod.id("originium_primitive_pure_proc"))
                .inputItems(TagPrefix.dust,OIOriProcMaterial.HALF_IMPURE_ORIGINIUM.material,512)
                .inputFluids(GTMaterials.HydrogenPeroxide,8000)
                .outputItems(TagPrefix.dust,OIElementMaterial.Originium.material,1)
                .outputFluids(GTMaterials.Fluorine.getFluid(8000))
                .outputFluids(GTMaterials.CarbonDioxide.getFluid(8000))
                .duration(20*2)
                .circuitMeta(23)
                .EUt(GTValues.V[GTValues.LuV],1)
                .save(consumer);

        // Casing Proc
        GTRecipeTypes.CHEMICAL_BATH_RECIPES.recipeBuilder(OIMod.id("casing_originium_bath"))
                .inputItems(GTBlocks.CASING_STAINLESS_CLEAN.get().asItem(),1)
                .inputFluids(OIElementMaterial.Originium.material, 1000)
                .outputItems(OICustomBlocks.ORIGINIUM_CASING_t1.block.get().asItem(),1)
                .duration(20*2)
                .EUt(GTValues.V[GTValues.IV],1)
                .save(consumer);
    }
}
