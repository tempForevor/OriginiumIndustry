package com.apcp.originium_industry.data.recipe.originium_industry;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.data.item.OIAEItems;
import com.apcp.originium_industry.data.machine.OICustomPartMachines;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class OIPartsRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(OIMod.id("super_me_pattern_buffer"))
                .inputItems(Blocks.ENDER_CHEST.asItem(),1)
                .outputItems(OICustomPartMachines.SUPER_ME_PATTERN_BUFFER.machine.asStack(1))
                .EUt(GTValues.V[GTValues.MV],1)
                .duration(20*20)
                .circuitMeta(24)
                .save(provider);
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(OIMod.id("super_me_pattern_buffer_proxy"))
                .inputItems(OICustomPartMachines.SUPER_ME_PATTERN_BUFFER.machine.asStack(1))
                .outputItems(OICustomPartMachines.SUPER_ME_PATTERN_BUFFER_PROXY.machine.asStack(1))
                .EUt(GTValues.V[GTValues.MV],1)
                .duration(20*20)
                .circuitMeta(24)
                .save(provider);
        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(OIMod.id("virtual_item_cell_from_proxy"))
                .inputItems(OICustomPartMachines.SUPER_ME_PATTERN_BUFFER_PROXY.machine.asStack(1))
                .outputItems(OIAEItems.VIRTUAL_ITEM_CELL.item.asStack(1))
                .EUt(GTValues.V[GTValues.MV],1)
                .duration(20*20)
                .circuitMeta(24)
                .save(provider);
    }
}
