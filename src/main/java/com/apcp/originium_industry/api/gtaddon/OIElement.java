package com.apcp.originium_industry.api.gtaddon;

import com.gregtechceu.gtceu.api.data.chemical.Element;
import com.gregtechceu.gtceu.api.registry.GTRegistries;

@SuppressWarnings("UnusedReturnValue")
public class OIElement {

    public Element element;
    public String name;
    public String formula;

    public OIElement(String name) {
        this.name = name;
    }

    public OIElement setElement(Element element) {
        this.element = element;
        return this;
    }

    public OIElement createAndRegister(long protons, long neutrons, long halfLifeSeconds, String decayTo,
                                              String symbol, boolean isIsotope) {
        Element element = new Element(protons, neutrons, halfLifeSeconds, decayTo, name, symbol, isIsotope);
        GTRegistries.ELEMENTS.register(name, element);
        this.formula = symbol;
        return setElement(element);
    }
}
