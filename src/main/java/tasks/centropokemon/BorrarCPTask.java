package tasks.centropokemon;

import controladores.BaseDeDatosControlador;
import controladores.CPControlador;
import entidades.CentroPokemon;
import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.CentroPokemonTask;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Consumer;

public class BorrarCPTask extends CentroPokemonTask {

    private Jugador origen;

    public BorrarCPTask(CentroPokemon centroPokemon, Jugador origen) {
        super(centroPokemon);
        this.origen = origen;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            CentroPokemon cpABorrar = em.find(CentroPokemon.class, centroPokemon.getNombre());
            em.getTransaction().begin();
            em.remove(cpABorrar);
            em.getTransaction().commit();
            CPControlador.removerCP(centroPokemon.getNombre());
            origen.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Se borro correctamente."));
        }catch (Exception ex){
            ex.printStackTrace();
            origen.getPlayer().sendMessage(Text.of(TextColors.RED, "Hubo un error al borrar."));
        }finally {
            em.close();
        }
    }
}
