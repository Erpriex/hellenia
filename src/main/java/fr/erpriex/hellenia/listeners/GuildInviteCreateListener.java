package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.utils.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.hooks.EventListener;

import java.awt.*;
import java.time.Instant;

public class GuildInviteCreateListener implements EventListener {

    private Hellenia main;

    public GuildInviteCreateListener(Hellenia main) {
        this.main = main;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof GuildInviteCreateEvent) {
            onGuildInviteCreate((GuildInviteCreateEvent) event);
        }
    }

    public void onGuildInviteCreate(GuildInviteCreateEvent event) {
        Long guildId = event.getGuild().getIdLong();
        if(main.getLogsManager().canUse(guildId)) {
            Invite invite = event.getInvite();
            User inviter = invite.getInviter();
            EmbedBuilder logEmbed = new EmbedBuilder()
                    .setColor(Color.YELLOW)
                    .setAuthor(inviter.getName(), null, inviter.getAvatarUrl())
                    .setDescription("a créé une invitation")
                    .addField("Code", "`" + invite.getCode() + "`", true)
                    .addField("URL", invite.getUrl(), true)
                    .addField("Salon", event.getChannel().getAsMention(), true)
                    .addField("Durée", TimeUtils.formatTime(invite.getMaxAge()), true)
                    .addField("Max utilisations", invite.getMaxUses() == 0 ? "Illimité" : invite.getMaxUses() + " utilisateurs", true)
                    .setTimestamp(Instant.now());

            event.getGuild().getTextChannelById(main.getLogsManager().getChannelId(guildId)).sendMessageEmbeds(logEmbed.build()).queue();
        }
    }
}
