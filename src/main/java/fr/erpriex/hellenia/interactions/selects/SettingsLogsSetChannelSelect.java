package fr.erpriex.hellenia.interactions.selects;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.db.entities.GuildSettingsLogsEntity;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

import java.util.List;

public class SettingsLogsSetChannelSelect implements SelectHandler {

    private Hellenia main;

    public SettingsLogsSetChannelSelect(Hellenia main) {
        this.main = main;
    }

    @Override public String namespace() { return "settings-logs"; }
    @Override public String action() { return "setChannelSelect"; }

    @Override
    public void handle(StringSelectInteractionEvent event, String payload, List<String> values) {
        String channelId = values.get(0);

        GuildSettingsLogsEntity settingsLogs = main.getRepositoriesRegistry().getGuildSettingsLogsRepository().findById(event.getGuild().getIdLong()).get();
        settingsLogs.setChannelId(Long.parseLong(channelId));
        main.getRepositoriesRegistry().getGuildSettingsLogsRepository().update(settingsLogs);

        event.editMessageEmbeds(main.getLogsFeature().buildEmbedSettings(settingsLogs).build())
                .setComponents(main.getLogsFeature().buildButtonsSettings(settingsLogs))
                .queue();
    }
}
