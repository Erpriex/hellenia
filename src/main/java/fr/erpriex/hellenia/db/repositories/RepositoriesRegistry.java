package fr.erpriex.hellenia.db.repositories;

import fr.erpriex.hellenia.Hellenia;
import lombok.Getter;
import org.hibernate.SessionFactory;

public class RepositoriesRegistry {

    private Hellenia main;

    @Getter
    private final GuildRepository guildRepository;

    @Getter
    private final GuildSettingsLogsRepository guildSettingsLogsRepository;

    @Getter
    private final GuildSettingsRepository guildSettingsRepository;

    public RepositoriesRegistry(Hellenia main, SessionFactory sf) {
        this.guildRepository = new GuildRepository(main, sf);
        this.guildSettingsLogsRepository = new GuildSettingsLogsRepository(sf);
        this.guildSettingsRepository = new GuildSettingsRepository(sf);
    }

}
