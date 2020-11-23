package tasks.lugardesbloqueable;

import controladores.BaseDeDatosControlador;
import controladores.LDControlador;
import entidades.Jugador;
import entidades.LugarDesbloqueable;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.LugarDesbloqueableTask;

import javax.persistence.EntityManager;

public class GuardarLDTask extends LugarDesbloqueableTask {

    private Jugador creador;

    public GuardarLDTask(LugarDesbloqueable lugar, Jugador creador) {
        super(lugar);
        this.creador = creador;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(lugar);
            em.getTransaction().commit();
            LDControlador.nuevoLD(lugar);
            creador.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Se guardo correctamente."));
        }catch (Exception ex) {
            ex.printStackTrace();
            creador.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error al guardar."));
        }finally {
            em.close();
        }
    }
}
