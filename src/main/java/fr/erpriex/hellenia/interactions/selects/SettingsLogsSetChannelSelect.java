package fr.erpriex.hellenia.interactions.selects;

import fr.erpriex.hellenia.Hellenia;
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
        Long guildId = event.getGuild().getIdLong();
        String channelId = values.get(0);

        main.getLogsManager().setChannelId(guildId, Long.parseLong(channelId));

        event.editMessageEmbeds(main.getLogsManager().buildEmbedSettings(guildId).build())
                .setComponents(main.getLogsManager().buildButtonsSettings(guildId))
                .queue();
    }
}
