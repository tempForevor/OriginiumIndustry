package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.LangModel;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;

import java.util.Locale;

public class OICombTieredMachineDef {

    public MachineDefinition[] machines;
    public String id;
    public LangModel lang = new LangModel();

    public OICombTieredMachineDef(String id){
        this.id = id;
    }

    public OICombTieredMachineDef setMachines(MachineDefinition[] machines) {
        this.machines = machines;
        return this;
    }
    public OICombTieredMachineDef setLang(String locale, String trans){
        lang.setLang(locale,trans);
        return this;
    }
    public OICombTieredMachineDef setLang(String trans){
        lang.setLang(trans);
        return this;
    }
    public String generateId(int tier){
        return "block." + OIMod.MOD_ID + "." + GTValues.VN[tier].toLowerCase(Locale.ROOT) + "_" + id;
    }
    public String generateLang(int tier,String rawLang){
        return GTValues.VNF[tier] + " " + rawLang;
    }
    public OICombTieredMachineDef langApply(int[] tiers){
        for(int i : tiers){
            lang.apply((k,v)-> LangDataGenerator.normal.getCollector(k).addTranslation(generateId(i),generateLang(i,v)));
        }
        return this;
    }

}
