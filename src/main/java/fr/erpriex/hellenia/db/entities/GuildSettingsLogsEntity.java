package fr.erpriex.hellenia.db.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guilds_settings_logs")
public class GuildSettingsLogsEntity {

    @Id
    @Getter
    @Setter
    private Long id;

    @Column(nullable = false)
    @Getter
    @Setter
    private boolean enabled;

}
