package fr.erpriex.hellenia.db.repositories;

import fr.erpriex.hellenia.db.entities.GuildEntity;
import org.hibernate.SessionFactory;

public class GuildRepository extends GenericRepository<GuildEntity, Long>{

    public GuildRepository(SessionFactory sf) {
        super(GuildEntity.class, sf);
    }

    public GuildEntity getOrCreate(Long guildId) {
        return findById(guildId).orElseGet(() -> {
            GuildEntity guild = new GuildEntity();
            guild.setId(guildId);
            return save(guild);
        });
    }

}
