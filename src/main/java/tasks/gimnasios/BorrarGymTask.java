package tasks.gimnasios;

import controladores.BaseDeDatosControlador;
import controladores.GymControlador;
import controladores.SesionControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import tasks.GimnasioTask;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BorrarGymTask extends GimnasioTask {

    private Jugador jugador;
    private String queryJugadoresToUpdate = "SELECT DISTINCT j " +
            "FROM Jugador j " +
            "INNER JOIN j.gimnasiosGanados g " +
            "WHERE g.nombreGym = :gym ";

    public BorrarGymTask(Gimnasio gym, Jugador jugador) {
        super(gym);
        this.jugador = jugador;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            Gimnasio gimnasioBorrar = em.find(Gimnasio.class, gym.getNombreGym());
            TypedQuery<Jugador> jugadoresAActualizar = em.createQuery(queryJugadoresToUpdate,Jugador.class);
            jugadoresAActualizar.setParameter("gym",gym.getNombreGym());
            List<Jugador> jugadores = jugadoresAActualizar.getResultList();
            em.getTransaction().begin();
            em.remove(gimnasioBorrar);
            jugadores.stream().forEach(jugador -> jugador.seCerroUnGym(gym));
            em.getTransaction().commit();
            GymControlador.removerGym(gym.getNombreGym());
            SesionControlador.seCerrorGym(gym);
            jugador.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Se borro correctamente."));
        }catch (Exception ex){
            ex.printStackTrace();
            jugador.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error al borrar."));
        }finally {
            em.close();
        }
    }
}
