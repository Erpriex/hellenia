package fr.erpriex.hellenia.interactions.selects;

import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface SelectHandler {
    String namespace();
    String action();

    void handle(StringSelectInteractionEvent event, String payload, java.util.List<String> values) throws Exception;

    default String id() { return ComponentIds.of(namespace(), action()); }
    default String id(String payload) { return ComponentIds.of(namespace(), action(), payload); }
}
