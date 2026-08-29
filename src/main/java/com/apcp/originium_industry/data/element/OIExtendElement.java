package com.apcp.originium_industry.data.element;


import com.apcp.originium_industry.api.gtaddon.OIElement;

public class OIExtendElement {

    public static OIElement Originium = new OIElement("originium");
    public static OIElement ActiveOriginium = new OIElement("active_originium");
    public static OIElement Spacetime = new OIElement("spacetime");
    public static OIElement HeatDeadSpace = new OIElement("heat_dead_space");

    public static void init(){
        Originium.createAndRegister(12,23,-1,"","◈",false);
        ActiveOriginium.createAndRegister(13,23,-1,"","▣",true);
        Spacetime.createAndRegister(1,1,-1,"","▢",false);
        HeatDeadSpace.createAndRegister(-1,-1,-1,"","▲",true);
    }
}
