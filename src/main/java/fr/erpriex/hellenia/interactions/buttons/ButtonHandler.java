package fr.erpriex.hellenia.interactions.buttons;

import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ButtonHandler {
    String namespace();
    String action();

    void handle(ButtonInteractionEvent event, String payload) throws Exception;

    default String id(String payload) {
        return ComponentIds.of(namespace(), action(), payload);
    }
    default String id() {
        return ComponentIds.of(namespace(), action());
    }
}
