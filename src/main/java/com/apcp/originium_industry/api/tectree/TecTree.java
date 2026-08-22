package com.apcp.originium_industry.api.tectree;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TecTree {
    public Map<ResourceLocation,Integer> locationConverter;
    public HashMap<Integer,OITecInfo> tecInfoItems;

    public TecTree(){
        locationConverter = new HashMap<>();
        tecInfoItems = new HashMap<>();
    }

    public OITecInfo get(int id){
        return tecInfoItems.get(id);
    }

    public OITecInfo get(ResourceLocation location){
        return tecInfoItems.get(locationConverter.get(location));
    }

    public void register(OITecInfo tecInfo){
        locationConverter.put(tecInfo.location,tecInfo.id);
        tecInfoItems.put(tecInfo.id,tecInfo);
    }
}
