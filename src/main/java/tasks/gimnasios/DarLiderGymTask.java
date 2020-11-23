package tasks.gimnasios;

import controladores.BaseDeDatosControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.GimnasioTask;

import javax.persistence.EntityManager;

public class DarLiderGymTask extends GimnasioTask {

    private Jugador lider, source;

    public DarLiderGymTask(Gimnasio gym, Jugador lider, Jugador source) {
        super(gym);
        this.lider = lider;
        this.source = source;
    }

    @Override
    public void accept(Task task) {
        if(gym.tieneLider(lider)) {
            source.getPlayer().sendMessage(Text.of(TextColors.RED, "Ya es lider de este gym."));
            return;
        }
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            Gimnasio gymDB = em.find(Gimnasio.class,gym.getNombreGym());
            em.getTransaction().begin();
            gymDB.addLider(lider);
            em.getTransaction().commit();
            gym.addLider(lider);
            source.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Agregado correctamente."));
            lider.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Ahora sos lider de " + gym.getNombreGym()));
        }catch (Exception ex) {
            ex.printStackTrace();
            source.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error."));
        }finally {
            em.close();
        }
    }
}
