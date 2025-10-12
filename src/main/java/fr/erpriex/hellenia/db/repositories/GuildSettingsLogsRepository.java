package fr.erpriex.hellenia.db.repositories;

import fr.erpriex.hellenia.db.entities.GuildSettingsLogsEntity;
import org.hibernate.SessionFactory;

public class GuildSettingsLogsRepository extends GenericRepository<GuildSettingsLogsEntity, Long> {

    public GuildSettingsLogsRepository(SessionFactory sf) {
        super(GuildSettingsLogsEntity.class, sf);
    }

}
