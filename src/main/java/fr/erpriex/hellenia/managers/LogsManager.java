package fr.erpriex.hellenia.managers;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.db.entities.GuildSettingsLogsEntity;
import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;

import java.awt.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class LogsManager {

    private Hellenia main;

    private Map<Long, Boolean> enableStatus = new HashMap<>();
    private Map<Long, Long> logsChannels = new HashMap<>();

    public LogsManager(Hellenia main) {
        this.main = main;
    }

    public boolean canUse(Long guildId) {
        if(enableStatus.containsKey(guildId)) {
            return logsChannels.get(guildId) != null;
        }
        registerValue(guildId);
        return enableStatus.get(guildId);
    }

    public boolean isEnabled(Long guildId) {
        if(enableStatus.containsKey(guildId)) {
            return enableStatus.get(guildId);
        }
        registerValue(guildId);
        return enableStatus.get(guildId);
    }

    public Long getChannelId(Long guildId) {
        if(logsChannels.containsKey(guildId)) {
            return logsChannels.get(guildId);
        }
        registerValue(guildId);
        return logsChannels.get(guildId);
    }

    public void setEnabled(Long guildId, boolean enabled) {
        GuildSettingsLogsEntity settingsLogs = main.getRepositoriesRegistry().getGuildSettingsLogsRepository().findById(guildId).get();
        settingsLogs.setEnabled(enabled);
        main.getRepositoriesRegistry().getGuildSettingsLogsRepository().update(settingsLogs);
        enableStatus.put(guildId, enabled);
    }

    public void setChannelId(Long guildId, Long channelId) {
        GuildSettingsLogsEntity settingsLogs = main.getRepositoriesRegistry().getGuildSettingsLogsRepository().findById(guildId).get();
        settingsLogs.setChannelId(channelId);
        main.getRepositoriesRegistry().getGuildSettingsLogsRepository().update(settingsLogs);
        logsChannels.put(guildId, channelId);
    }

    public void registerValue(Long guildId) {
        GuildSettingsLogsEntity settingsLogs = main.getRepositoriesRegistry().getGuildSettingsLogsRepository().findById(guildId).get();
        enableStatus.put(guildId, settingsLogs.isEnabled());
        logsChannels.put(guildId, settingsLogs.getChannelId());
    }

    public ActionRow buildButtonsSettings(Long guildId) {
        String toggleId  = ComponentIds.of("settings-logs", "toggle");
        Button toggleButton = isEnabled(guildId) ? Button.danger(toggleId, "❌ Désactiver") : Button.success(toggleId, "✅ Activer");

        String setChannelId  = ComponentIds.of("settings-logs", "setChannel");
        Button setChannelButton = Button.primary(setChannelId, "\uD83D\uDCDD Configurer le salon");

        return ActionRow.of(toggleButton, setChannelButton);
    }

    public EmbedBuilder buildEmbedSettings(Long guildId) {
        Long channelId = getChannelId(guildId);

        EmbedBuilder logsMenuEmbed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle("\uD83D\uDCF0 Logs")
                .setDescription(isEnabled(guildId) ? "✅ Activé" : "❌ Désactivé")
                .addBlankField(false)
                .addField("\uD83D\uDCDD Salon des logs", channelId == null ? "`❌ Aucun salon configuré`" : "<#" + channelId + ">", true)
                .addBlankField(false)
                .setFooter("Hellenia", main.getJda().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now());

        return logsMenuEmbed;
    }

}
