package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.Hellenia;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;

public class GuildVoiceUpdateListener implements EventListener {

    private Hellenia main;

    public GuildVoiceUpdateListener(Hellenia main) {
        this.main = main;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof GuildVoiceUpdateEvent){
            onGuildVoiceUpdate((GuildVoiceUpdateEvent) event);
        }
    }

    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        Long guildId = event.getGuild().getIdLong();
        if(main.getLogsManager().canUse(guildId)) {
            Member member = event.getMember();
            String descriptionEmbed = getDescriptionEmbed(event, member);

            EmbedBuilder logEmbed = new EmbedBuilder()
                    .setColor(Color.YELLOW)
                    .setTitle("Activité vocale")
                    .setDescription(descriptionEmbed)
                    .setImage("https://i.imgur.com/1fIa6Ob.png")
                    .setTimestamp(Instant.now());

            event.getGuild().getTextChannelById(main.getLogsManager().getChannelId(guildId)).sendMessageEmbeds(logEmbed.build()).queue();
        }
    }

    @NotNull
    private static String getDescriptionEmbed(GuildVoiceUpdateEvent event, Member member) {
        AudioChannelUnion channelJoined = event.getChannelJoined();
        AudioChannelUnion channelLeft = event.getChannelLeft();

        String descriptionEmbed = "<@" + member.getId() + "> ";

        if (channelJoined != null && channelLeft == null) { // Joined channel
            descriptionEmbed += "a rejoint le salon <#" + channelJoined.getId() + "> \uD83D\uDCE5";
        }

        else if (channelJoined == null && channelLeft != null) { // Left channel
            descriptionEmbed += "a quitté le salon <#" + channelLeft.getId() + "> \uD83D\uDCE4";
        }

        else if (channelJoined != null && channelLeft != null) { // Moved channel
            descriptionEmbed += "s'est déplacé du salon <#" + channelLeft.getId() + "> vers <#" + channelJoined.getId() + "> \uD83D\uDEB4";
        }
        return descriptionEmbed;
    }

}
