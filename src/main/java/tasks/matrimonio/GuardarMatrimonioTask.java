package tasks.matrimonio;

import controladores.BaseDeDatosControlador;
import entidades.Jugador;
import entidades.Matrimonio;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import states.matrimonios.Consumado;
import states.matrimonios.EstadoMatrimonio;
import tasks.MatrimonioTask;

import javax.persistence.EntityManager;

public class GuardarMatrimonioTask extends MatrimonioTask {

    private Consumado consumado;

    public GuardarMatrimonioTask(Matrimonio matrimonio) {
        super(matrimonio);
    }

    public GuardarMatrimonioTask(Matrimonio matrim, Consumado consumado) {
        super(matrim);
        this.consumado = consumado;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            Jugador parejeUnoDB = em.find(Jugador.class,matrimonio.getParejaUno().getNombre().toLowerCase());
            Jugador parejeDosDB = em.find(Jugador.class,matrimonio.getParejaDos().getNombre().toLowerCase());
            em.getTransaction().begin();
            em.persist(this.matrimonio);
            parejeUnoDB.setMatrim(this.matrimonio);
            parejeDosDB.setMatrim(this.matrimonio);
            em.getTransaction().commit();

            matrimonio.getParejaUno().setMatrim(this.matrimonio);
            matrimonio.getParejaDos().setMatrim(this.matrimonio);
            matrimonio.getParejaUno().actualizarPrefijo();
            matrimonio.getParejaDos().actualizarPrefijo();
            //TODO LDControlador.nuevoLD(lugar);
            this.matrimonio.getParejaUno().getPlayer().sendMessage(Text.of(TextColors.AQUA ,"Felicitaciones te has casado con " + this.matrimonio.getParejaDos().getNombre()));
            this.matrimonio.getParejaDos().getPlayer().sendMessage(Text.of(TextColors.AQUA ,"Felicitaciones te has casado con " + this.matrimonio.getParejaUno().getNombre()));
            this.consumado.setFinalizado();
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            em.close();
        }
    }
}
