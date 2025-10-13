package fr.erpriex.hellenia.interactions.buttons;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.db.entities.GuildSettingsLogsEntity;
import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
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
        GuildSettingsLogsEntity settingsLogs = main.getRepositoriesRegistry().getGuildSettingsLogsRepository().findById(event.getGuild().getIdLong()).get();
        settingsLogs.setEnabled(!settingsLogs.isEnabled());
        main.getRepositoriesRegistry().getGuildSettingsLogsRepository().update(settingsLogs);

        MessageEmbed previousEmbed = event.getMessage().getEmbeds().get(0);

        EmbedBuilder logsMenuEmbed = new EmbedBuilder(previousEmbed)
                .setDescription(settingsLogs.isEnabled() ? "✅ Activé" : "❌ Désactivé")
                .setTimestamp(Instant.now());

        String toggleId  = ComponentIds.of("settings-logs", "toggle", String.valueOf(event.getUser().getIdLong()));
        Button toggleButton = settingsLogs.isEnabled() ? Button.danger(toggleId, "❌ Désactiver") : Button.success(toggleId, "✅ Activer");

        event.editMessageEmbeds(logsMenuEmbed.build())
                .setComponents(ActionRow.of(toggleButton))
                .queue();
    }

}
