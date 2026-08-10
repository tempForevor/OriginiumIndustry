package com.apcp.originium_industry.data.material;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.gtaddon.OIMaterial;
import com.apcp.originium_industry.data.element.OIExtendElement;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.ToolProperty;
import com.gregtechceu.gtceu.api.fluids.FluidBuilder;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_FINE_WIRE;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_FRAME;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_SPRING;
import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.GENERATE_SPRING_SMALL;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class OIElementMaterial {


    public static OIMaterial Originium = new OIMaterial(OIMod.id("originium"));
    public static OIMaterial ActiveOriginium = new OIMaterial(OIMod.id("active_originium"));
    public static OIMaterial Spacetime = new OIMaterial(OIMod.id("spacetime"));
    public static OIMaterial HeatDeadSpace = new OIMaterial(OIMod.id("heat_dead_space"));

    public static void init(){
        Originium.setMaterial(Originium.provide()
                .element(OIExtendElement.Originium.element)
                .ingot()
                .liquid(new FluidBuilder().temperature(1223))
                .ore()
                .color(0x122312).secondaryColor(0x231223c)
                .appendFlags(EXT2_METAL, GENERATE_GEAR, GENERATE_SMALL_GEAR, GENERATE_RING, GENERATE_FRAME,
                        GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_FINE_WIRE)
                .toolStats(ToolProperty.Builder.of(6.0F, 7.5F, 214748364, 5)
                        .enchantability(14).build())
                .rotorStats(10000, 14000, 2.0f, 12800)
                .cableProperties(V[MAX], 32, 0)
                .buildAndRegister());


        ActiveOriginium.setMaterial(ActiveOriginium.provide()
                .element(OIExtendElement.ActiveOriginium.element)
                .gem()
                .ore()
                .liquid(new FluidBuilder().temperature(1223))
                .color(0x122312+0x462400).secondaryColor(0xffff23c)
                .appendFlags(STD_METAL,GENERATE_ROD)
                .iconSet(MaterialIconSet.EMERALD)
                .cableProperties(V[ULV],1,1)
                .buildAndRegister());



        Spacetime.setMaterial(Spacetime.provide()
                .element(OIExtendElement.Spacetime.element)
                .liquid(new FluidBuilder().temperature(0))
                .color(0x111155).secondaryColor(0x333377c)
                .buildAndRegister());



        HeatDeadSpace.setMaterial(HeatDeadSpace.provide()
                .element(OIExtendElement.HeatDeadSpace.element)
                .liquid(new FluidBuilder().temperature(0))
                .color(0x1111ee).secondaryColor(0x0000ffc)
                .buildAndRegister());

    }

    public static void initTranslation(){
        Originium.setLang("Originium")
                .setLang("zh_cn","源石")
                .langApply();
        ActiveOriginium.setLang("Active Originium")
                .setLang("zh_cn","活性化的源石")
                .langApply();
        Spacetime.setLang("Spacetime")
                .setLang("zh_cn","时空")
                .langApply();
        HeatDeadSpace.setLang("Heatdeath Space")
                .setLang("zh_cn","热寂化的时空")
                .langApply();
    }
}
