package com.apcp.originium_industry.api.gtaddon;

import com.apcp.originium_industry.OIMod;
import com.apcp.originium_industry.api.datagen.langgen.LangDataGenerator;
import com.apcp.originium_industry.api.datagen.langgen.LangModel;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class OIItem {

    public ItemEntry<Item> item;
    public String id;

    public LangModel lang = new LangModel();

    public OIItem(String id){
        this.id = id;
    }

    public OIItem setItem(ItemEntry<Item> item){
        this.item = item;
        return this;
    }

    /**
     * @param consumer It will call Registrate.item(id,Item::new) to create a registrateBuilder.It will automatically call the register() and setItem() too.
    * */
    public OIItem register(Consumer<ItemBuilder<Item, GTRegistrate>> consumer){
        var registrateItemBuilder = OIMod.OIREGISTRATE.item(id, Item::new);
        consumer.accept(registrateItemBuilder);
        return setItem(registrateItemBuilder.register());
    }

    public OIItem setLang(String locale,String trans){
        lang.setLang(locale, trans);
        return this;
    }
    public OIItem setLang(String trans){
        lang.setLang(trans);
        return this;
    }
    public OIItem langApply(){
        lang.apply((k,v)-> LangDataGenerator.normal.getCollector(k).addTranslation("item."+OIMod.MOD_ID+"."+id,v));
        return this;
    }
}
