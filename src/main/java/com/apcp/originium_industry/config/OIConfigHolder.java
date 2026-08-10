package com.apcp.originium_industry.config;

import com.apcp.originium_industry.OIMod;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.UpdateRestrictions;


@Config(id="OriginiumIndustry",group= OIMod.MOD_ID)
public final class ConfigHolder {

    private static final Object LOCK = new Object();

    public ConfigHolder(){
        synchronized (LOCK){
            GTConfigInit.init();
        }
    }

    @Configurable
    @Configurable.Comment("This controls the modifier of the gtrecipes' speed.(0.1->(20s->2s))")
    @Configurable.UpdateRestriction(UpdateRestrictions.GAME_RESTART)
    @Configurable.DecimalRange(min=0.0001, max=10.0)
    public double recipeSpeedModifier = 0.1;

    public enum Difficulty{
        Easy(0),
        Hard(1);

        public final int value;

        Difficulty(int value){
            this.value = value;
        }

        boolean equal(Difficulty another){
            return value == another.value;
        }
    }

    @Configurable
    @Configurable.Comment("This controls the difficulty of the game.(WIP,only influence the gtceu content.)")
    @Configurable.UpdateRestriction(UpdateRestrictions.GAME_RESTART)
    @Configurable.StringPattern("Easy|Hard")
    public String difficulty = "Easy";

}
