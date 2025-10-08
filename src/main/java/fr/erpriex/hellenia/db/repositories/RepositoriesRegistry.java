package fr.erpriex.hellenia.db.repositories;

import lombok.Getter;
import org.hibernate.SessionFactory;

public class RepositoriesRegistry {

    @Getter
    private final GuildRepository guildRepository;

    public RepositoriesRegistry(SessionFactory sf) {
        this.guildRepository = new GuildRepository(sf);
    }

}
