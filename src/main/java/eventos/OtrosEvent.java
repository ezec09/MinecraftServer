package eventos;

import controladores.SesionControlador;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.filter.type.Exclude;
import org.spongepowered.api.event.item.inventory.AffectItemStackEvent;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

public class OtrosEvent {

    @Listener(
            order = Order.FIRST
    )
    @Exclude({SendCommandEvent.class, ClientConnectionEvent.Auth.class, ClientConnectionEvent.Join.class,
            ChangeInventoryEvent.Held.class, ChangeEntityEquipmentEvent.class})
    public void onAnyPlayerEvent(Event event, @First Player player) {
        if (event instanceof Cancellable) {
            ((Cancellable)event).setCancelled(!SesionControlador.existeSesion(player.getName()));
        }
    }

    @Listener
    public void onInventoryMedallaEvent(AffectItemStackEvent evento, @First Inventory inventory) {
        evento.setCancelled(((inventory.getInventoryProperty(InventoryTitle.class).get()).getValue()).toPlain().contains("VERMEDALLAS"));
    }
}
