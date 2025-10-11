package fr.erpriex.hellenia.listeners;

import fr.erpriex.hellenia.interactions.buttons.ButtonRegistry;
import fr.erpriex.hellenia.interactions.ComponentIds;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonRouterListener extends ListenerAdapter {
    private final ButtonRegistry registry;

    public ButtonRouterListener(ButtonRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String id = event.getComponentId();
        String ns = ComponentIds.namespace(id);
        String act = ComponentIds.action(id);
        String payload = ComponentIds.payload(id);

        var handler = registry.find(ns, act);
        if (handler == null) {
            event.reply("❌ Bouton inconnu.").setEphemeral(true).queue();
            return;
        }
        try {
            handler.handle(event, payload);
        } catch (Exception e) {
            event.reply("⚠️ Erreur : " + e.getMessage()).setEphemeral(true).queue();
        }
    }
}
