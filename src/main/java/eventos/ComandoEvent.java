package eventos;

import controladores.BCC;
import controladores.CooldownControlador;
import controladores.LDControlador;
import controladores.SesionControlador;
import entidades.CooldownCommand;
import entidades.CooldownPlayer;
import entidades.Jugador;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ComandoEvent {

    @Listener(
            order = Order.FIRST
    )
    public void onSendCommandEvent(SendCommandEvent event, @First Player player) {
        String comando = event.getCommand();
        boolean esLoginORegister = comando.equalsIgnoreCase("login") || comando.equalsIgnoreCase("registrar");
        boolean existeSesion = SesionControlador.existeSesion(player.getName().toLowerCase());
        event.setCancelled(existeSesion == esLoginORegister);
        if(existeSesion && comando.contains("warp") && event.getArguments().split(" ")[0].length() > 0) {
            String warp = event.getArguments().replace("\"", "");
            Boolean loTieneDesbloqueado = SesionControlador.getJugador(player.getName()).meTenesDesbloqueadoNCS(warp);
            Boolean estaEnLugares = LDControlador.existeNCS(warp);
            Boolean cancelar = estaEnLugares && !loTieneDesbloqueado;
            if(cancelar)
                player.sendMessage(Text.of(TextColors.RED,"No has debloqueado esta zona aun!!"));
            event.setCancelled(cancelar);
        }

        if(existeSesion && CooldownControlador.comandoTieneCd(comando)) {
            CooldownCommand cdCommand = CooldownControlador.getCommandCd(comando);
            CooldownPlayer cdPlayer = cdCommand.getPlayerCooldown(player.getName());

            if(cdPlayer.getFecha().isBefore(LocalDateTime.now())) {
                cdPlayer.agregarCd(cdCommand.getCooldown());
            }else {
                Duration duration = Duration.between(LocalDateTime.now(), cdPlayer.getFecha());
                player.sendMessage(Text.of(TextColors.RED, "Puedes usar este comando dentro de " + duration.getSeconds() + " segundos"));
                event.setCancelled(true);
            }
        }


    }
}
