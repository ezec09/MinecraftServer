package tasks.gimnasios;

import controladores.BaseDeDatosControlador;
import controladores.GymControlador;
import controladores.SesionControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import entidades.Sesion;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.GimnasioTask;

import javax.persistence.EntityManager;

public class GuardarGymTask extends GimnasioTask {

    private Jugador creador;

    public GuardarGymTask(Gimnasio gym, Jugador creador) {
        super(gym);
        this.creador = creador;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(gym);
            em.getTransaction().commit();
            GymControlador.nuevoGym(gym);
            SesionControlador.nuevoGym();
            creador.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Se guardo correctamente."));
        }catch (Exception ex) {
            ex.printStackTrace();
            creador.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error al guardar."));
        }finally {
            em.close();
        }

    }
}
