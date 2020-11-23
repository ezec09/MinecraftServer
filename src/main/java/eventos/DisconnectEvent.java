package eventos;

import controladores.BCC;
import controladores.SesionControlador;
import entidades.Sesion;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.scheduler.Task;
import tasks.sesion.CerrarSesionTask;

import java.util.concurrent.TimeUnit;

public class DisconnectEvent {

    @Listener
    public void onDisconnectEvent(ClientConnectionEvent.Disconnect event) {
        String nombreJugador = event.getTargetEntity().getName().toLowerCase();
        if(SesionControlador.existeSesion(nombreJugador)) {
            //Task CerrarSesion
            Sesion ss = SesionControlador.getSesion(nombreJugador);
            CerrarSesionTask cst = new CerrarSesionTask(ss);
            Task.builder().execute(cst).async().submit(BCC.getPluginContainer());
        }
    }
}
