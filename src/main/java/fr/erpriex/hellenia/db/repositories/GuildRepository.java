package fr.erpriex.hellenia.db.repositories;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.db.entities.GuildEntity;
import fr.erpriex.hellenia.db.entities.GuildSettingsEntity;
import fr.erpriex.hellenia.db.entities.GuildSettingsLogsEntity;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildRepository extends GenericRepository<GuildEntity, Long>{

    private static final Logger log = LoggerFactory.getLogger(GuildRepository.class);

    private Hellenia main;

    public GuildRepository(Hellenia main, SessionFactory sf) {
        super(GuildEntity.class, sf);
        this.main = main;
    }

    public GuildEntity getOrCreate(Long guildId) {
        return findById(guildId).orElseGet(() -> {
            log.info("Creating guild {}", guildId);

            GuildSettingsLogsEntity logs = new GuildSettingsLogsEntity();
            logs.setId(guildId);
            logs.setEnabled(false);

            GuildSettingsEntity settings = new GuildSettingsEntity();
            settings.setId(guildId);
            settings.setLogs(logs);

            GuildEntity guild = new GuildEntity();
            guild.setId(guildId);
            guild.setSettings(settings);

            return save(guild);
        });
    }

}
