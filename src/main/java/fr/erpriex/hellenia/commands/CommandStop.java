package fr.erpriex.hellenia.commands;

import fr.erpriex.hellenia.Hellenia;
import fr.erpriex.hellenia.commands.construct.Command;

public class CommandStop {

    private Hellenia main;

    public CommandStop(Hellenia main){
        this.main = main;
    }

    @Command( name = "stop", description = "Arrêter le bot", type = Command.ExecutorType.CONSOLE, isSlashCommand = true)
    public void command(){
        main.stopBot();
    }

}
