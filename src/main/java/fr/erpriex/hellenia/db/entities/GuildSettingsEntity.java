package fr.erpriex.hellenia.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guilds_settings")
public class GuildSettingsEntity {

    @Id
    @Getter
    @Setter
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "logs_id")
    @Getter
    @Setter
    private GuildSettingsLogsEntity logs;

}
