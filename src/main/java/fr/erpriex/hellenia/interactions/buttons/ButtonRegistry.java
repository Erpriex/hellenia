package fr.erpriex.hellenia.interactions.buttons;

import java.util.HashMap;
import java.util.Map;

public class ButtonRegistry {
    private final Map<String, ButtonHandler> map = new HashMap<>();
    private static String key(String ns, String act){ return ns + ":" + act; }

    public ButtonRegistry register(ButtonHandler h) {
        map.put(key(h.namespace(), h.action()), h);
        return this;
    }

    public ButtonHandler find(String ns, String act) {
        return map.get(key(ns, act));
    }
}
