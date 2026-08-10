package com.apcp.originium_industry.mixin.gtceu;

import com.apcp.originium_industry.config.OIConfigHolder;
import com.google.gson.JsonObject;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GTRecipeBuilder.class)
public class OIMixinGTRecipeBuilder {

    @Shadow(remap = false)
    public GTRecipeType recipeType;

    @Shadow(remap = false)
    public int duration;

    @Shadow(remap = false)
    public GTRecipeBuilder duration(int duration) {
        return null;
    }

//    @Shadow public abstract GTRecipeBuilder recipeType(GTRecipeType recipeType);

    @Unique
    private long OI$eut = 0;

    @Inject(method = "EUt(J)Lcom/gregtechceu/gtceu/data/recipe/builder/GTRecipeBuilder;", at = @At("HEAD"), remap = false)
    private void eu(long eu, CallbackInfoReturnable<GTRecipeBuilder> cir) {
        OI$eut = eu;
    }

    @Unique
    private double OI$getRecipeTimeModifier(){
        return OIConfigHolder.INSTANCE.recipeSpeedModifier;
    }

    @Unique
    private int OI$getDuration() {
//        GTCEu.LOGGER.info("!!!recipe modified: {} with duration {} and eut {}",recipeType.toString(),duration,gTRM$eut);
//        boolean t = gTRM$eut < 0 ||
//                recipeType == GTRecipeTypes.get("primitive_void_ore") ||
//                recipeType == GTRecipeTypes.get("large_boiler") ||
//                recipeType == GTRecipeTypes.get("steam_boiler") ||
//                recipeType == GTRecipeTypes.get("slaughterhouse") ||
//                recipeType == GTRecipeTypes.get("dyson_sphere") ||
//                recipeType == GTRecipeTypes.get("space_elevator") ||
//                recipeType == GTRecipeTypes.get("annihilate_generator");
//        GTCEu.LOGGER.info("!!!recipe judging... {}",t);
        if (OI$eut < 0 ||
                recipeType == GTRecipeTypes.get("primitive_void_ore") ||
                recipeType == GTRecipeTypes.get("large_boiler") ||
                recipeType == GTRecipeTypes.get("steam_boiler") ||
                recipeType == GTRecipeTypes.get("slaughterhouse") ||
                recipeType == GTRecipeTypes.get("dyson_sphere") ||
                recipeType == GTRecipeTypes.get("space_elevator") ||
                recipeType == GTRecipeTypes.get("annihilate_generator")) {
            return Math.abs(duration);
        }
//        for(String i:Config.noModifiedRecipes){
//            if(recipeType == GTRecipeTypes.get(i)){
//                return Math.abs(duration);
//            }
//        }
        return (int) Math.min(Integer.MAX_VALUE, Math.max(1, Math.abs(duration * OI$getRecipeTimeModifier())));
    }

    @Inject(method = "toJson", at = @At("TAIL"), remap = false)
    public void toJson(JsonObject json, CallbackInfo ci) {
        json.addProperty("duration", OI$getDuration());
    }
}
