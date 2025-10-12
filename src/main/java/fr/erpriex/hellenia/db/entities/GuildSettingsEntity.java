package fr.erpriex.hellenia.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guilds_settings")
public class GuildSettingsEntity {

    @Id
    @Getter
    @Setter
    private Long id;

}
