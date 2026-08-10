package com.apcp.originium_industry.config;

import com.gregtechceu.gtceu.config.ConfigHolder;

public class GTConfigInit {

    public static boolean isEasyMode(){
        return OIConfigHolder.INSTANCE.isEasyMode();
    }

    public static boolean isHardMode(){
        return OIConfigHolder.INSTANCE.isHardMode();
    }

    public static void init(){
        ConfigHolder.init();
        ConfigHolder.INSTANCE.machines.highTierContent = true;
        ConfigHolder.INSTANCE.machines.doesExplosionDamagesTerrain = false;
        ConfigHolder.INSTANCE.machines.shouldWeatherOrTerrainExplosion = false;
        ConfigHolder.INSTANCE.machines.harmlessActiveTransformers = true;
        ConfigHolder.INSTANCE.machines.enableWorldAccelerators = true;
        ConfigHolder.INSTANCE.machines.steelSteamMultiblocks = true;
        ConfigHolder.INSTANCE.machines.enableCleanroom = true;

        // Final game goal : FE Energy Generator
        ConfigHolder.INSTANCE.compat.energy.enableFEConverters = false;
        ConfigHolder.INSTANCE.compat.energy.nativeEUToFE = false;

        // Difficulty
        ConfigHolder.INSTANCE.compat.ae2.meHatchEnergyUsage *= isEasyMode() ? 0 : 1;
        ConfigHolder.INSTANCE.recipes.casingsPerCraft = isEasyMode() ? 3 : 2;
        ConfigHolder.INSTANCE.recipes.removeVanillaTNTRecipe = true;
        ConfigHolder.INSTANCE.recipes.removeVanillaBlockRecipes = false;
        ConfigHolder.INSTANCE.recipes.generateLowQualityGems = true;
        ConfigHolder.INSTANCE.recipes.hardDyeRecipes = isHardMode();
        ConfigHolder.INSTANCE.recipes.harderBrickRecipes = isHardMode();
        ConfigHolder.INSTANCE.recipes.hardAdvancedIronRecipes = isHardMode();
        ConfigHolder.INSTANCE.recipes.hardGlassRecipes = isHardMode();
        ConfigHolder.INSTANCE.recipes.hardWoodRecipes = isHardMode();
        ConfigHolder.INSTANCE.recipes.hardToolArmorRecipes = isHardMode();
        ConfigHolder.INSTANCE.recipes.harderCharcoalRecipe = isHardMode();
        ConfigHolder.INSTANCE.recipes.hardIronRecipes = isHardMode();
        ConfigHolder.INSTANCE.machines.cleanMultiblocks = isEasyMode();
        ConfigHolder.INSTANCE.machines.orderedAssemblyLineItems = isHardMode();
        ConfigHolder.INSTANCE.machines.orderedAssemblyLineFluids = false;
    }
}
