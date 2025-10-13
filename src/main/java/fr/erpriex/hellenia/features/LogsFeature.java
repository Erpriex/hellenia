package fr.erpriex.hellenia.features;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.db.entities.GuildSettingsLogsEntity;
import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;

import java.awt.*;
import java.time.Instant;

public class LogsFeature {

    private Hellenia main;

    public LogsFeature(Hellenia main) {
        this.main = main;
    }

    public ActionRow buildButtonsSettings(GuildSettingsLogsEntity settingsLogs) {
        String toggleId  = ComponentIds.of("settings-logs", "toggle");
        Button toggleButton = settingsLogs.isEnabled() ? Button.danger(toggleId, "❌ Désactiver") : Button.success(toggleId, "✅ Activer");

        String setChannelId  = ComponentIds.of("settings-logs", "setChannel");
        Button setChannelButton = Button.primary(setChannelId, "\uD83D\uDCDD Configurer le salon");

        return ActionRow.of(toggleButton, setChannelButton);
    }

    public EmbedBuilder buildEmbedSettings(GuildSettingsLogsEntity settingsLogs) {
        EmbedBuilder logsMenuEmbed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle("\uD83D\uDCF0 Logs")
                .setDescription(settingsLogs.isEnabled() ? "✅ Activé" : "❌ Désactivé")
                .addBlankField(false)
                .addField("\uD83D\uDCDD Salon des logs", settingsLogs.getChannelId() == null ? "`❌ Aucun salon configuré`" : "<#" + settingsLogs.getChannelId() + ">", true)
                .addBlankField(false)
                .setFooter("Hellenia", main.getJda().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now());

        return logsMenuEmbed;
    }

}
