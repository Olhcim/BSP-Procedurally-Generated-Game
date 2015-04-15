package com.olhcim.util;

import java.util.HashMap;
import javafx.util.Pair;

public class Map2D <Type> {
    
    HashMap< Pair<Integer,Integer>, Type > map;
    
    public Map2D()
    {
        map = new HashMap<>();
    }
    
    public void put(int x, int y, Type a)
    {
        Pair<Integer, Integer> pair = new Pair<>(x,y);
        map.put(pair, a);
    }
    
    public void remove(int x, int y)
    {
        Pair<Integer, Integer> pair = new Pair<>(x,y);
        map.remove(pair);
    }
    
    public void remove(Type a)
    {
        map.remove(a);
    }
    
    public Type get(int x, int y)
    {
        Pair<Integer, Integer> pair = new Pair<>(x,y);
        return map.get(pair);
    }
    
    public boolean containsKey(int x, int y)
    {
        Pair<Integer, Integer> pair = new Pair<>(x,y);
        return map.containsKey(pair);
    }
    
    public boolean containsValue(Type a)
    {
        return map.containsValue(a);
    }
    
    public Object[] getValueArray()
    {
        return map.values().toArray();
    }
    
    public void clear()
    {
        map.clear();
    }
    
    public int size()
    {
        return map.size();
    }
}