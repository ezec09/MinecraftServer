package tasks.jugador;

import controladores.BaseDeDatosControlador;
import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.JugadorTask;

import javax.persistence.EntityManager;

public class CambiarPasswordTask extends JugadorTask {


    private final String passwordVieja;
    private final String passwordNueva;

    public CambiarPasswordTask(Jugador jugador, String passwordNueva, String passwordVieja) {
        super(jugador);
        this.passwordNueva = passwordNueva;
        this.passwordVieja = passwordVieja;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        Jugador jugadorDB = em.find(Jugador.class,jugador.getNombre().toLowerCase());
        if(jugadorDB != null && jugadorDB.getPassword().equals(passwordVieja)) {
            em.getTransaction().begin();
            jugadorDB.setPassword(passwordNueva);
            em.getTransaction().commit();
            jugador.getPlayer().sendMessage(Text.of(TextColors.GREEN,"Password cambiada correctamente."));
        }else if(jugadorDB != null && !jugadorDB.getPassword().equals(passwordVieja)) {
            jugador.getPlayer().sendMessage(Text.of(TextColors.RED,"Datos incorrectos."));
        }else if(jugadorDB == null) {
            jugador.getPlayer().sendMessage(Text.of(TextColors.RED,"No te encontramos en la db. Avisar a un admin."));
        }else {
            jugador.getPlayer().sendMessage(Text.of(TextColors.DARK_RED,"Ups no sabemos que paso."));
        }
        em.close();
    }
}
