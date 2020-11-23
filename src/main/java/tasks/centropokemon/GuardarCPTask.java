package tasks.centropokemon;

import controladores.BaseDeDatosControlador;
import controladores.CPControlador;
import controladores.SesionControlador;
import entidades.CentroPokemon;
import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.CentroPokemonTask;

import javax.persistence.EntityManager;

public class GuardarCPTask extends CentroPokemonTask {

    private Jugador origen;

    public GuardarCPTask(CentroPokemon centroPokemon, Jugador origen) {
        super(centroPokemon);
        this.origen = origen;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(centroPokemon);
            em.getTransaction().commit();
            CPControlador.nuevoCP(centroPokemon);
            origen.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Se guardo correctamente."));
        }catch (Exception ex) {
            ex.printStackTrace();
            origen.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error al guardar."));
        }finally {
            em.close();
        }

    }
}
