package com.apcp.originium_industry.commmon.recipe_type;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.gtaddon.OIRecipeType;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import com.lowdragmc.lowdraglib.gui.texture.ProgressTexture;

public class OICustomRecipeType {

    public static OIRecipeType ORIGINIUM_ENERGY_RELEASE = new OIRecipeType(OIMod.id("originium_energy_release"),GTRecipeTypes.GENERATOR);
    public static OIRecipeType ORIGINIUM_DEACTIVATE = new OIRecipeType(OIMod.id("originium_deactivate"),GTRecipeTypes.GENERATOR);

    public static void init(){
        ORIGINIUM_ENERGY_RELEASE.register(
                recipeType -> recipeType
                        .setMaxIOSize(2,2,0,0)
                        .setSlotOverlay(true,false,true, GuiTextures.CENTRIFUGE_OVERLAY)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
                        .setEUIO(IO.OUT)
                        .setSound(GTSoundEntries.TURBINE)
        );
        ORIGINIUM_DEACTIVATE.register(
                recipeType -> recipeType
                        .setMaxIOSize(2,2,1,1)
                        .setSlotOverlay(true,true,true, GuiTextures.CENTRIFUGE_OVERLAY)
                        .setProgressBar(GuiTextures.PROGRESS_BAR_EXTRACT, ProgressTexture.FillDirection.LEFT_TO_RIGHT)
                        .setEUIO(IO.BOTH)
                        .setSound(GTSoundEntries.TURBINE)
        );
    }

    public static void initTranslation(){
        ORIGINIUM_ENERGY_RELEASE.setLang("Originium Energy Release")
                .setLang("zh_cn","源石能量释放")
                .langApply();
        ORIGINIUM_DEACTIVATE.setLang("Industry Originium Catalyse")
                .setLang("zh_cn","工业源石催化")
                .langApply();
    }
}
