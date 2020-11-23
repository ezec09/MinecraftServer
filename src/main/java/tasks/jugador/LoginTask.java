package tasks.jugador;

import controladores.BCC;
import controladores.BaseDeDatosControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.Sesion;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.JugadorTask;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class LoginTask extends JugadorTask {

    private String password;

    public LoginTask(Jugador jugador, String password) {
        super(jugador);
        this.password = password;
    }

    @Override
    public void accept(Task task) {
        Player player = jugador.getPlayer();
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        Jugador jugadorCargado = em.find(Jugador.class,jugador.getNombre().toLowerCase());
        em.close();

        if(jugadorCargado != null && jugadorCargado.getPassword().equals(password))
            this.loginCorrecto(jugadorCargado);
        else if(jugadorCargado == null)
            player.sendMessage(Text.of(TextColors.RED,"No estas registrado."));
        else if(!jugadorCargado.getPassword().equals(password))
            player.sendMessage(Text.of(TextColors.RED,"Contrasenia incorrecta."));
        else
            player.sendMessage(Text.of(TextColors.DARK_RED,"Ups, no sabemos que paso."));
    }

    public void loginCorrecto(Jugador jdb) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        if(jdb.getLugaresDesbloqueados() == null)
            jdb.setLugaresDesbloqueados(new ArrayList<>());
        Sesion sesion = SesionControlador.nuevaSesion(jdb);
        em.getTransaction().begin();
        em.persist(sesion);
        em.getTransaction().commit();
        em.close();
        jugador.getPlayer().sendMessage(Text.of(TextColors.GREEN,"Logueado correctamente"));
    }
}
