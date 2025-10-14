package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.Hellenia;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.EventListener;

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
            AudioChannelUnion channelJoined = event.getChannelJoined();
            AudioChannelUnion channelLeft = event.getChannelLeft();

            String descriptionEmbed = "";

            if (channelJoined != null && channelLeft == null) { // Joined channel
                descriptionEmbed = "a rejoint le salon <#" + channelJoined.getId() + ">";
            }

            else if (channelJoined == null && channelLeft != null) { // Left channel
                descriptionEmbed = "a quitté le salon <#" + channelLeft.getId() + ">";
            }

            else if (channelJoined != null && channelLeft != null) { // Moved channel
                descriptionEmbed = "s'est déplacé du salon <#" + channelLeft.getId() + "> vers <#" + channelJoined.getId() + ">";
            }

            EmbedBuilder logEmbed = new EmbedBuilder()
                    .setColor(Color.YELLOW)
                    .setAuthor(member.getUser().getName(), null, member.getUser().getAvatarUrl())
                    .setDescription(descriptionEmbed)
                    .setTimestamp(Instant.now());

            event.getGuild().getTextChannelById(main.getLogsManager().getChannelId(guildId)).sendMessageEmbeds(logEmbed.build()).queue();
        }
    }

}
