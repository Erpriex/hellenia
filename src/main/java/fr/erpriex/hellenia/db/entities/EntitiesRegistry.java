package fr.erpriex.hellenia.db.entities;

import org.hibernate.cfg.Configuration;

public final class EntitiesRegistry {

    public static void register(Configuration cfg) {
        cfg.addAnnotatedClass(GuildEntity.class);
    }

}
