package fr.erpriex.hellenia.interactions.buttons;

import fr.erpriex.hellenia.Hellenia;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.time.Instant;

public class SettingsLogsToggleButton implements ButtonHandler {

    private Hellenia main;

    public SettingsLogsToggleButton(Hellenia main) {
        this.main = main;
    }

    @Override public String namespace() { return "settings-logs"; }
    @Override public String action() { return "toggle"; }

    @Override
    public void handle(ButtonInteractionEvent event, String payload) {
        Long guildId = event.getGuild().getIdLong();
        main.getLogsManager().setEnabled(guildId, !main.getLogsManager().isEnabled(guildId));

        MessageEmbed previousEmbed = event.getMessage().getEmbeds().get(0);

        EmbedBuilder logsMenuEmbed = new EmbedBuilder(previousEmbed)
                .setDescription(main.getLogsManager().isEnabled(guildId) ? "✅ Activé" : "❌ Désactivé")
                .setTimestamp(Instant.now());

        event.editMessageEmbeds(logsMenuEmbed.build())
                .setComponents(main.getLogsManager().buildButtonsSettings(guildId))
                .queue();
    }

}
