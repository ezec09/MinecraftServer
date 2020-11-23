package tasks.lugardesbloqueable;

import controladores.BaseDeDatosControlador;
import controladores.LDControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.LugarDesbloqueable;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.LugarDesbloqueableTask;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BorrarLDTask extends LugarDesbloqueableTask {

    private Jugador origen;
    private String queryJugadoresToUpdate = "SELECT DISTINCT j " +
            "FROM Jugador j " +
            "INNER JOIN j.lugaresDesbloqueados g " +
            "WHERE g.nombre = :lugar";

    public BorrarLDTask(LugarDesbloqueable lugar, Jugador origen) {
        super(lugar);
        this.origen = origen;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            LugarDesbloqueable gimnasioBorrar = em.find(LugarDesbloqueable.class, lugar.getNombre());
            TypedQuery<Jugador> jugadoresAActualizar = em.createQuery(queryJugadoresToUpdate,Jugador.class);
            jugadoresAActualizar.setParameter("lugar",lugar.getNombre());
            List<Jugador> jugadores = jugadoresAActualizar.getResultList();
            em.getTransaction().begin();
            em.remove(gimnasioBorrar);
            jugadores.stream().forEach(jugador -> jugador.seBorrorUnLD(lugar));
            em.getTransaction().commit();
            LDControlador.removerLD(lugar.getNombre());
            SesionControlador.seBorroLd(lugar);
            origen.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Se borro correctamente."));
        }catch (Exception ex){
            ex.printStackTrace();
            origen.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error al borrar."));
        }finally {
            em.close();
        }
    }
}
