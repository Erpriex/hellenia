package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.interactions.ComponentIds;
import fr.erpriex.hellenia.interactions.selects.SelectHandler;
import fr.erpriex.hellenia.interactions.selects.SelectRegistry;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelectRouterListener extends ListenerAdapter {
    private final SelectRegistry registry;
    public SelectRouterListener(SelectRegistry registry) { this.registry = registry; }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        String id = event.getComponentId();
        String ns = ComponentIds.namespace(id);
        String act = ComponentIds.action(id);
        String payload = ComponentIds.payload(id);

        SelectHandler handler = registry.find(ns, act);
        if (handler == null) {
            event.reply("❌ Menu inconnu.").setEphemeral(true).queue();
            return;
        }
        try {
            handler.handle(event, payload, event.getValues());
        } catch (Exception e) {
            event.reply("⚠️ Erreur: " + e.getMessage()).setEphemeral(true).queue();
        }
    }
}
