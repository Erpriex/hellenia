package fr.erpriex.hellenia.interactions.buttons;

import fr.erpriex.hellenia.Hellenia;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class SettingsLogsButton implements ButtonHandler {

    private Hellenia main;

    public SettingsLogsButton(Hellenia main) {
        this.main = main;
    }

    @Override public String namespace() { return "settings"; }
    @Override public String action() { return "logs"; }

    @Override
    public void handle(ButtonInteractionEvent event, String payload) {
        Long guildId = event.getGuild().getIdLong();

        EmbedBuilder logsMenuEmbed = main.getLogsManager().buildEmbedSettings(guildId);

        event.editMessageEmbeds(logsMenuEmbed.build())
                .setComponents(main.getLogsManager().buildButtonsSettings(guildId))
                .queue();
    }
}