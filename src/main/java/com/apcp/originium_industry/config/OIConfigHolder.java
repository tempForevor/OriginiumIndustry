package com.apcp.originium_industry.config;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.annotation.LangAnnotation;
import com.apcp.originium_industry.api.datagen.langgen.annotation.LangAnnotations;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.UpdateRestrictions;
import dev.toma.configuration.config.format.ConfigFormats;
import org.apache.commons.codec.language.bm.Lang;


@Config(id=OIMod.CONFIG_ID,group= OIMod.MOD_ID)
public final class OIConfigHolder {

    public static OIConfigHolder INSTANCE;
    private static final Object LOCK = new Object();

    public static void init(){
        synchronized (LOCK){
            if(INSTANCE == null) {
                dev.toma.configuration.config.ConfigHolder<OIConfigHolder> configHolder = Configuration.registerConfig(OIConfigHolder.class, ConfigFormats.YAML);
                INSTANCE = configHolder.getConfigInstance();
            }
            INSTANCE.realDifficulty = Difficulty.values()[INSTANCE.difficulty];

            GTConfigInit.init();
        }
    }

    public static void initTranslation(){
        var configClass = INSTANCE.getClass();
        OIConfigLangScanner.scanDeep(configClass);
    }

    public Difficulty realDifficulty;

    public boolean isEasyMode(){
        return realDifficulty.equal(Difficulty.Easy);
    }
    public boolean isHardMode(){
        return realDifficulty.equal(Difficulty.Hard);
    }

    @Configurable
    @Configurable.Comment("This controls the modifier of the GTM recipes' speed. ( 0.1 -> ( 20s -> 2s ) )")
    @Configurable.UpdateRestriction(UpdateRestrictions.GAME_RESTART)
    @Configurable.DecimalRange(min=0.0001, max=10.0)
    @LangAnnotation("Recipe Speed Modifier")
    @LangAnnotation(locale = "zh_cn",value = "配方时间系数")
    public double recipeSpeedModifier = 0.1;

    @Configurable
    @Configurable.Comment("This controls the max slot size of the Super ME Pattern Buffer.")
    @Configurable.UpdateRestriction(UpdateRestrictions.GAME_RESTART)
    @Configurable.Range(min=1, max=18)
    @LangAnnotation("Max ME Pattern Buffer Row Size")
    @LangAnnotation(locale = "zh_cn",value = "ME样板总成最大列数")
    public int maxMEPatternRow = 9;
    @Configurable
    @Configurable.Comment("This controls the max slot size of the Super ME Pattern Buffer.")
    @Configurable.UpdateRestriction(UpdateRestrictions.GAME_RESTART)
    @Configurable.Range(min=1, max=18)
    @LangAnnotation("Max ME Pattern Buffer Col Size")
    @LangAnnotation(locale = "zh_cn",value = "ME样板总成最大行数")
    public int maxMEPatternCol = 6;
    @Configurable
    @Configurable.Comment("This controls the max slot size of the Super ME Pattern Buffer.")
    @Configurable.UpdateRestriction(UpdateRestrictions.GAME_RESTART)
    @Configurable.Range(min=1, max=18)
    @LangAnnotation("Max ME Pattern Buffer Page Size")
    @LangAnnotation(locale = "zh_cn",value = "ME样板总成最大页数")
    public int maxMEPatternPage = 3;

    // Exactly, it is hard-coded in the code,since I find no easy ways to dynamic change the translations in normal framework till now.
    // Now parallel = parallelScale ^ Voltage(ulv=0).
    public int parallelScale = 4;

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
    @Configurable.Comment("This controls the difficulty of the game.(WIP,only influence the gtceu content.)\n0->Easy,1->Hard")
    @Configurable.UpdateRestriction(UpdateRestrictions.GAME_RESTART)
    @Configurable.Range(min=0,max=1)
    @LangAnnotation("difficulty")
    @LangAnnotation(locale = "zh_cn",value = "难度")
    public int difficulty = 0;

}
