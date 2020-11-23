package tasks.jugador;

import controladores.BaseDeDatosControlador;
import entidades.Jugador;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.JugadorTask;

import javax.persistence.EntityManager;

public class GetPasswordTask extends JugadorTask {

    private String jugadorABuscar;

    public GetPasswordTask(Jugador jugador, String jugadorABuscar) {
        super(jugador);
        this.jugadorABuscar = jugadorABuscar;
    }

    @Override
    public void accept(Task task) {
        Player player = jugador.getPlayer();
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        Jugador jugador = em.find(Jugador.class,jugadorABuscar.toLowerCase());
        em.close();

        if(jugador != null)
            player.sendMessage(Text.of(TextColors.GREEN,"Password: " + jugador.getPassword()));
        else
            player.sendMessage(Text.of(TextColors.RED,"No se encontro el jugador."));

    }
}
