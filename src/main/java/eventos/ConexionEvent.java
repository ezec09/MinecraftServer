package eventos;

import controladores.BCC;
import controladores.BossBarControlador;
import entidades.Jugador;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.scheduler.Task;
import tasks.jugador.PreLoginTask;

import java.util.concurrent.TimeUnit;

public class ConexionEvent {

    private Integer deltaMov = 2;

    @Listener
    public void onLoginEvent(ClientConnectionEvent.Login event) {
        if (event.getTargetUser().getPlayer().isPresent()) {
            PreLoginTask task = new PreLoginTask(new Jugador(event.getTargetUser().getPlayer().get()),deltaMov);
            task.setContadorBar(BossBarControlador.getSVBForLogin());
            Task.builder().execute(task).async().interval(deltaMov, TimeUnit.SECONDS).submit(BCC.getPluginContainer());
        }
    }
}
