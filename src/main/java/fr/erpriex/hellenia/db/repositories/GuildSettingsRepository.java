package fr.erpriex.hellenia.db.repositories;

import fr.erpriex.hellenia.db.entities.GuildSettingsEntity;
import org.hibernate.SessionFactory;

public class GuildSettingsRepository extends GenericRepository<GuildSettingsEntity, Long> {

    public GuildSettingsRepository(SessionFactory sf) {
        super(GuildSettingsEntity.class, sf);
    }

}
