package eventos;

import controladores.BCC;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.Sesion;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;

import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent.Chat;
import org.spongepowered.api.text.Text;

public class ChatEvent {

    @Listener(
            order = Order.LAST
    )
    public void onChatEvent(Chat evento, @First Player player) {
        Jugador emisor = SesionControlador.getJugador(player.getName());
        Text header = evento.getFormatter().getHeader().toText();
        if(emisor.getPrefijo() == null)
            emisor.actualizarPrefijo();
        header = emisor.getPrefijo().concat(Text.of(" ")).concat(header);
        evento.getFormatter().setHeader(header);
    }

}
