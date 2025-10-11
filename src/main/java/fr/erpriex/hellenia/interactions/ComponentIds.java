package fr.erpriex.hellenia.interactions;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class ComponentIds {
    private ComponentIds() {}

    public static String of(String namespace, String action) {
        return namespace + ":" + action;
    }
    public static String of(String namespace, String action, String payload) {
        return namespace + ":" + action + "|" + encode(payload);
    }

    public static String namespace(String id) { return id.split("[:|]", 2)[0]; }
    public static String action(String id) { return id.split("[:|]", 3)[1]; }
    public static String payload(String id) {
        String[] parts = id.split("\\|", 2);
        return parts.length == 2 ? decode(parts[1]) : null;
    }

    private static String encode(String s){ return Base64.getUrlEncoder().withoutPadding()
            .encodeToString(s.getBytes(StandardCharsets.UTF_8)); }
    private static String decode(String s){ return new String(Base64.getUrlDecoder().decode(s), StandardCharsets.UTF_8); }
}
