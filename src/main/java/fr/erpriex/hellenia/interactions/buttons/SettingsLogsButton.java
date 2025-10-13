package fr.erpriex.hellenia.interactions.buttons;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.db.entities.GuildSettingsLogsEntity;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;

public class SettingsLogsButton implements ButtonHandler {

    private Hellenia main;

    public SettingsLogsButton(Hellenia main) {
        this.main = main;
    }

    @Override public String namespace() { return "settings"; }
    @Override public String action() { return "logs"; }

    @Override
    public void handle(ButtonInteractionEvent event, String payload) {
        GuildSettingsLogsEntity settingsLogs = main.getRepositoriesRegistry().getGuildSettingsLogsRepository().findById(event.getGuild().getIdLong()).get();

        EmbedBuilder logsMenuEmbed = main.getLogsFeature().buildEmbedSettings(settingsLogs);

        event.editMessageEmbeds(logsMenuEmbed.build())
                .setComponents(main.getLogsFeature().buildButtonsSettings(settingsLogs))
                .queue();
    }
}