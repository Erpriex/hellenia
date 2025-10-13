package fr.erpriex.hellenia.interactions.buttons;

import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.selections.SelectOption;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.List;
import java.util.stream.Collectors;

public class SettingsLogsSetChannelButton implements ButtonHandler {

    @Override public String namespace() { return "settings-logs"; }
    @Override public String action() { return "setChannel"; }

    @Override
    public void handle(ButtonInteractionEvent event, String payload) {
        List<SelectOption> options = event.getGuild().getTextChannels().stream()
                .map(channel -> SelectOption.of("#" + channel.getName(), channel.getId()))
                .limit(25)
                .collect(Collectors.toList());

        String selectId = ComponentIds.of(namespace(), "setChannelSelect", String.valueOf(event.getUser().getIdLong()));
        StringSelectMenu selectMenu = StringSelectMenu.create(selectId)
                .setPlaceholder("Sélectionnez un salon")
                .addOptions(options)
                .setRequiredRange(1, 1)
                .build();

        MessageEmbed logsSettingsEmbed = event.getMessage().getEmbeds().get(0);

        event.editMessageEmbeds(logsSettingsEmbed)
                .setComponents(ActionRow.of(selectMenu))
                .queue();
    }


}
