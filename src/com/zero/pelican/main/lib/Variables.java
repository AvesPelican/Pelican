package com.zero.pelican.main.lib;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class Variables {
    private static final Map<String, Double> variables;
    static {
        variables = new HashMap<>();
        variables.put("PI", Math.PI);
        variables.put("E", Math.E);
        variables.put("PHI", 1.618033988749895);
    }

    public static boolean isExists(String key){
        return variables.containsKey(key);
    }
    public static double get(String key){
        if (!isExists(key)) return 0;
        return variables.get(key);
    }

    public static void set(String key, double value){
        variables.put(key,value);
    }
}
