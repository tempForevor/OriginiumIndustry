package com.apcp.originium_industry.data.machine;

public class OIMachines {

    public static void init(){
        OIGCYMMachines.init();
        OICustomMachines.init();
        OIUIMachines.init();
    }

    public static void initTranslation(){
        OIGCYMMachines.initTranslation();
        OICustomMachines.initTranslation();
        OIUIMachines.initTranslation();
    }
    @Deprecated
    public static void initModel(){
        OICustomMachines.initModel();
        OIUIMachines.initModel();
    }
}
