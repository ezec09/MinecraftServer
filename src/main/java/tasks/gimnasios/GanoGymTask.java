package tasks.gimnasios;

import controladores.BCC;
import controladores.BaseDeDatosControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.GimnasioTask;

import javax.persistence.EntityManager;

public class GanoGymTask extends GimnasioTask {

    private Jugador lider,ganador;

    public GanoGymTask(Gimnasio gym, Jugador lider, Jugador ganador) {
        super(gym);
        this.lider = lider;
        this.ganador = ganador;
    }

    @Override
    public void accept(Task task) {
        if(ganador.ganoGym(gym)) {
            lider.getPlayer().sendMessage(Text.of(TextColors.RED,"Este jugador ya gano este gym."));
            return;
        } else if(!gym.tieneLider(lider)){
            lider.getPlayer().sendMessage(Text.of(TextColors.RED,"No sos lider de este gym."));
            return;
        }
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        //String deleteGymsFromPlayers = "DELETE FROM Jugador";
        try {
            Jugador jugadorDB = em.find(Jugador.class,ganador.getNombre().toLowerCase());
            em.getTransaction().begin();
            jugadorDB.ganasteGym(gym);
            em.getTransaction().commit();
            ganador.ganasteGym(gym);
            ganador.getPlayer().sendMessage(Text.of(TextColors.GREEN,"Ganaste el gimnasio "+ gym.getNombreGym() +"!"));
        }catch (Exception ex){
            ex.printStackTrace();
            lider.getPlayer().sendMessage(Text.of(TextColors.RED,"Hubo un error."));
        }finally {
            em.close();
        }
    }
}
