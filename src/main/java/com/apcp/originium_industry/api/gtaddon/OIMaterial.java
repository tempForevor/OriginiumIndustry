package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.LangModel;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

@SuppressWarnings("UnusedReturnValue")
public class OIMaterial{

    public Material material;

    public ResourceLocation location;

    public OIMaterial(ResourceLocation location) {
        this.location = location;
    }

    public LangModel lang = new LangModel();

    public OIMaterial setLang(String value){
        lang.setLang(value);
        return this;
    }

    public OIMaterial setLang(String locale,String value){
        lang.setLang(locale,value);
        return this;
    }

    public OIMaterial langApply() {
        lang.apply((k,v)-> LangDataGenerator.materials.getCollector(k).addTranslation(location,v));
        return this;
    }

    public OIMaterialBuilder provide(){
        return new OIMaterialBuilder(location);
    }

    /**
    * @param consumer It will pass a builder provided by `provider()`,and it will automatically call the `buildAndRegister()` after accepting the consumer.
    * */
    public OIMaterial build(Consumer<OIMaterialBuilder> consumer){
        var builder = provide();
        consumer.accept(builder);
        return setMaterial(builder.buildAndRegister());
    }

    public OIMaterial buildRaw(Consumer<Material.Builder> consumer){
        var builder = new Material.Builder(location);
        consumer.accept(builder);
        return setMaterial(builder.buildAndRegister());
    }

    public OIMaterial buildProcDust(Consumer<OIMaterialBuilder> consumer){
        return build(builder -> {
            builder.dust()
                    .ignoredTagPrefixes(TagPrefix.dustSmall,TagPrefix.dustTiny)
                    .flags(MaterialFlags.NO_SMELTING);
            consumer.accept(builder);
        });
    }

    public OIMaterial buildProcLiquid(Consumer<OIMaterialBuilder> consumer){
        return build(builder->{
            builder.liquid();
            consumer.accept(builder);
        });
    }

    public OIMaterial setMaterial(Material material){
        this.material = material;
        return this;
    }

}
