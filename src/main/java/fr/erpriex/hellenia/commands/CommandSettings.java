package fr.erpriex.hellenia.commands;

import fr.erpriex.hellenia.commands.construct.Command;
import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.time.Instant;

public class CommandSettings {

    @Command(name = "settings", description = "Paramètres du bot", type = Command.ExecutorType.USER)
    public void command(SlashCommandInteractionEvent event){
        if (event.getMember() == null
                || (!event.getMember().isOwner() && !event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
            event.reply("❌ Vous n'avez pas la permission d'utiliser cette commande.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        EmbedBuilder settingsMenuEmbed = new EmbedBuilder()
                .setColor(Color.ORANGE)
                .setTitle("\uD83D\uDEE0\uFE0F Paramères")
                .addBlankField(false)
                .addField("\uD83D\uDCF0 Logs", "`❌ Désactivé`", true)
                .addBlankField(false)
                .setFooter("Hellenia", event.getJDA().getSelfUser().getAvatarUrl())
                .setTimestamp(Instant.now());

        String logsId  = ComponentIds.of("settings", "logs", String.valueOf(event.getUser().getIdLong()));

        Button logsBtn = Button.primary(logsId, "📰 Logs");

        event.replyEmbeds(settingsMenuEmbed.build())
                .addComponents(ActionRow.of(logsBtn))
                .setEphemeral(true)
                .queue();
    }

}
