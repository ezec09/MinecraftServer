package tasks.gimnasios;

import controladores.BaseDeDatosControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.GimnasioTask;

import javax.persistence.EntityManager;

public class SacarLiderGymTask extends GimnasioTask {

    private Jugador lider, source;

    public SacarLiderGymTask(Gimnasio gym, Jugador lider, Jugador source) {
        super(gym);
        this.lider = lider;
        this.source = source;
    }


    @Override
    public void accept(Task task) {
        if(!gym.tieneLider(lider)) {
            source.getPlayer().sendMessage(Text.of(TextColors.RED, "No es lider de este gym."));
            return;
        }
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            Gimnasio gymDB = em.find(Gimnasio.class,gym.getNombreGym());
            em.getTransaction().begin();
            gymDB.sacarLider(lider);
            em.getTransaction().commit();
            gym.sacarLider(lider);
            source.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Sacadp correctamente."));
            lider.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Ya no sos lider de " + gym.getNombreGym()));
        }catch (Exception ex) {
            ex.printStackTrace();
            source.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error."));
        }finally {
            em.close();
        }
    }
}
