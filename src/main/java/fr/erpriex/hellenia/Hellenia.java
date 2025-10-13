package fr.erpriex.hellenia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.erpriex.hellenia.commands.CommandSettings;
import fr.erpriex.hellenia.commands.CommandStop;
import fr.erpriex.hellenia.commands.construct.CommandMap;
import fr.erpriex.hellenia.db.HibernateUtil;
import fr.erpriex.hellenia.db.repositories.RepositoriesRegistry;
import fr.erpriex.hellenia.features.LogsFeature;
import fr.erpriex.hellenia.interactions.buttons.ButtonRegistry;
import fr.erpriex.hellenia.interactions.buttons.SettingsLogsButton;
import fr.erpriex.hellenia.interactions.buttons.SettingsLogsSetChannelButton;
import fr.erpriex.hellenia.interactions.buttons.SettingsLogsToggleButton;
import fr.erpriex.hellenia.interactions.selects.SelectRegistry;
import fr.erpriex.hellenia.interactions.selects.SettingsLogsSetChannelSelect;
import fr.erpriex.hellenia.listeners.*;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.util.Scanner;

public class Hellenia implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(Hellenia.class);

    @Getter
    private JDA jda;

    @Getter
    private CommandMap commandMap;

    private final Scanner scanner = new Scanner(System.in);
    private boolean running;

    private SessionFactory sessionFactory;

    @Getter
    private RepositoriesRegistry repositoriesRegistry;

    @Getter
    private LogsFeature logsFeature;

    public Hellenia() throws InterruptedException {
        Gson gson = new GsonBuilder().create();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("config.json")), JsonObject.class);
        String token = jsonObject.get("token").getAsString();
        String commandPrefix = jsonObject.get("commandPrefix").getAsString();

        sessionFactory = HibernateUtil.getSessionFactory();
        repositoriesRegistry = new RepositoriesRegistry(this, sessionFactory);

        commandMap = new CommandMap(this, commandPrefix);

        commandMap.registerCommand(new CommandSettings(this));
        commandMap.registerCommand(new CommandStop(this));

        ButtonRegistry buttons = new ButtonRegistry()
                .register(new SettingsLogsButton(this))
                .register(new SettingsLogsSetChannelButton())
                .register(new SettingsLogsToggleButton(this));

        SelectRegistry selects = new SelectRegistry()
                .register(new SettingsLogsSetChannelSelect(this));

        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new ButtonRouterListener(buttons))
                .addEventListeners(new CommandListener(this))
                .addEventListeners(new GuildJoinListener(this))
                .addEventListeners(new ReadyListener(this))
                .addEventListeners(new SelectRouterListener(selects))
                .build();

        logsFeature = new LogsFeature(this);

        jda.awaitReady();

        commandMap.registerSlashCommands();
    }

    public static void main(String[] args) {
        try {
            Hellenia hellenia = new Hellenia();
            new Thread(hellenia, "bot").start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        running = true;

        while(running){
            if(scanner.hasNextLine()){
                commandMap.commandConsole(scanner.nextLine());
            }
        }

        scanner.close();
        log.info("Arret du bot !");
        HibernateUtil.shutdown();
        jda.shutdown();
        System.exit(0);
    }

    public void stopBot(){
        running = false;
    }

}
