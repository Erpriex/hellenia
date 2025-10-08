package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.Hellenia;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadyListener implements EventListener {

    private static final Logger log = LoggerFactory.getLogger(ReadyListener.class);

    private Hellenia main;

    public ReadyListener(Hellenia main){
        this.main = main;
    }

    @Override
    public void onEvent(GenericEvent event) {
        if(event instanceof ReadyEvent){
            onReady((ReadyEvent) event);
        }
    }

    public void onReady(ReadyEvent event){
        log.info("Bot ready !");
        main.getJda().getGuilds().forEach(guild -> {
            main.getRepositoriesRegistry().getGuildRepository().getOrCreate(guild.getIdLong());
        });
    }
}
