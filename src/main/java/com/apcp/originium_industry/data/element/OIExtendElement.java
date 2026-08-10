package com.apcp.originium_industry.data.element;


import com.gregtechceu.gtceu.api.data.chemical.Element;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class OIElement {

    public static Element createAndRegister(long protons, long neutrons, long halfLifeSeconds, String decayTo,
                                            String name, String symbol, boolean isIsotope) {
        Element element = new Element(protons, neutrons, halfLifeSeconds, decayTo, name, symbol, isIsotope);
        GTRegistries.ELEMENTS.register(name, element);
        return element;
    }

    public static Element Originium;
    public static Element ActiveOriginium;
    public static Element Spacetime;
    public static Element HeatDeadSpace;

    public static void init(){
        Originium = createAndRegister(12,23,-1,"","originium","◈",false);
        ActiveOriginium = createAndRegister(13,23,-1,"","active_originium","▣",true);
        Spacetime = createAndRegister(1,1,-1,"","spacetime","▢",false);
        HeatDeadSpace = createAndRegister(-1,-1,-1,"","heat_dead_space","▲",true);
    }
}
