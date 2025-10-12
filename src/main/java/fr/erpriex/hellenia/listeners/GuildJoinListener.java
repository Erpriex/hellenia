package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.Hellenia;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class GuildJoinListener implements EventListener {

    private Hellenia main;

    public GuildJoinListener(Hellenia main) {
        this.main = main;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof GuildJoinEvent){
            onGuildJoin((GuildJoinEvent) event);
        }
    }

    public void onGuildJoin(GuildJoinEvent event){
        main.getRepositoriesRegistry().getGuildRepository().getOrCreate(event.getGuild().getIdLong());
    }
}
