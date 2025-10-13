package fr.erpriex.hellenia.interactions.selects;

import java.util.HashMap;
import java.util.Map;

public class SelectRegistry {
    private final Map<String, SelectHandler> map = new HashMap<>();
    private static String key(String ns, String act){ return ns + ":" + act; }

    public SelectRegistry register(SelectHandler h) {
        map.put(key(h.namespace(), h.action()), h);
        return this;
    }
    public SelectHandler find(String ns, String act) {
        return map.get(key(ns, act));
    }
}
