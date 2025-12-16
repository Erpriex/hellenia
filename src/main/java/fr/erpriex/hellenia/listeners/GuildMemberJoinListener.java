package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.utils.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import java.awt.*;
import java.time.Instant;

public class GuildMemberJoinListener implements EventListener {

    private Hellenia main;

    public GuildMemberJoinListener(Hellenia main) {
        this.main = main;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof GuildMemberJoinEvent){
            onGuildMemberJoin((GuildMemberJoinEvent) event);
        }
    }

    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Long guildId = event.getGuild().getIdLong();
        if(main.getLogsManager().canUse(guildId)) {
            User user = event.getUser();
            EmbedBuilder logEmbed = new EmbedBuilder()
                    .setColor(Color.YELLOW)
                    .setTitle("Nouvel utilisateur")
                    .setDescription("<@" + user.getId() + "> a rejoint le Discord \uD83D\uDC4B")
                    .setThumbnail(user.getAvatarUrl())
                    .setImage("https://i.imgur.com/1fIa6Ob.png")
                    .addField("ID", "`" + user.getId() + "`", true)
                    .addField("Compte créé le", "<t:" + user.getTimeCreated().toEpochSecond() + ":f>", true)
                    .setTimestamp(Instant.now());

            event.getGuild().getTextChannelById(main.getLogsManager().getChannelId(guildId)).sendMessageEmbeds(logEmbed.build()).queue();
        }
    }

}
