package com.apcp.originium_industry.api.util.oi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OIEventRegistry<T> {
    private final List<T> REGISTRATION = new ArrayList<>();

    public void register(T obj){
        REGISTRATION.add(obj);
    }

    public List<T> getRegistration(){
        return Collections.unmodifiableList(REGISTRATION);
    }
}
