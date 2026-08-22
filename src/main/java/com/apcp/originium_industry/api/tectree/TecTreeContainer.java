package com.apcp.originium_industry.api.tectree;

import java.util.HashMap;

public class TecTreeContainer {

    public HashMap<Integer,Boolean> lock;

    public TecTreeContainer(){
        lock = new HashMap<>();
    }
    public boolean check(int id){
        var res = lock.get(id);
        if(res == null){
            lock.put(id,false);
            return false;
        }
        return res;
    }
    public boolean check(OITecInfo info){
        return check(info.id);
    }

    public void unlock(OITecInfo info){
        lock.remove(info.id);
        lock.put(info.id,true);
    }

    public void clear(){
        lock.clear();
    }
}
