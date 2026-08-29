package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.LangModel;
import com.apcp.originium_industry.api.datagen.modelgen.OIBlockModelGenerator;
import com.apcp.originium_industry.api.datagen.modelgen.collector.OIBlockModelInfo;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

public class OIMachineDef {

    public MachineDefinition machine;
    public String id;
    public LangModel lang = new LangModel();
    public LangModel tooltipLang = new LangModel();

    public OIMachineDef(String id){
        this.id = id;
    }
    public OIMachineDef setMachine(MachineDefinition machine){
        this.machine = machine;
        return this;
    }

    public MachineBuilder<MachineDefinition,?> provide(Function<IMachineBlockEntity, MetaMachine> metaMachine){
        return OIMod.OIREGISTRATE.machine(id,metaMachine);
    }
    public OIMachineDef register(Function<IMachineBlockEntity, MetaMachine> metaMachine, Consumer<MachineBuilder<MachineDefinition,?>> consumer){
        var builder = provide(metaMachine);
        consumer.accept(builder);
        return setMachine(builder.register());
    }


    public OIMachineDef setLang(String locale,String value){
        lang.setLang(locale,value);
        return this;
    }
    public OIMachineDef setLang(String value){
        lang.setLang(value);
        return this;
    }
    public String generateId(){
        return "block."+ OIMod.MOD_ID+"."+id;
    }
    public String generateTooltipId(){
        return generateId() + ".tooltip";
    }

    @SuppressWarnings("UnusedReturnValue")
    public OIMachineDef applyLang(){
        lang.apply((k,v)-> LangDataGenerator.normal.getCollector(k).addTranslation(generateId(),v));
        tooltipLang.apply((k,v)->LangDataGenerator.normal.getCollector(k).addTranslation(generateTooltipId(),v));
        return this;
    }

    @Deprecated
    public OIMachineDef initModels(){
        OIBlockModelGenerator.collector.modelGenerators
                .put(machine.getBlock(),
                        OIBlockModelInfo.buildTieredPlaceholderMachine(machine));
        return this;
    }
}
