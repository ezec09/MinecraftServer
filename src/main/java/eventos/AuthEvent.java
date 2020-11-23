package eventos;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class AuthEvent {

    @Listener
    public void onPreLoginEvent(ClientConnectionEvent.Auth event) {
        String name = (event.getProfile().getName().get()).toLowerCase();
        Boolean cancelar = Sponge.getServer().getOnlinePlayers().stream().anyMatch(elem -> elem.getName().toLowerCase().equals(name));
        event.setCancelled(cancelar);
    }
}
