package fr.erpriex.hellenia.interactions.buttons;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.awt.*;
import java.time.Instant;

public class SettingsLogsButton implements ButtonHandler {
    @Override public String namespace() { return "settings"; }
    @Override public String action() { return "logs"; }

    @Override
    public void handle(ButtonInteractionEvent event, String payload) {
        var embed = event.getMessage().getEmbeds().isEmpty() ? null : event.getMessage().getEmbeds().get(0);
        String value = "`✅ Activé`";
        if (embed != null && embed.getFields().stream().anyMatch(f -> f.getName().contains("Logs") && f.getValue().contains("✅"))) {
            value = "`❌ Désactivé`";
        }

        EmbedBuilder updated = new EmbedBuilder(embed != null ? embed : new EmbedBuilder().build())
                .setColor(Color.ORANGE)
                .clearFields()
                .addField("📰 Logs", value, true)
                .setTimestamp(Instant.now());

        event.editMessageEmbeds(updated.build()).queue();
    }
}