package tasks.matrimonio;

import controladores.BaseDeDatosControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.Matrimonio;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import states.matrimonios.Consumado;
import states.matrimonios.Divorsio;
import tasks.MatrimonioTask;

import javax.persistence.EntityManager;

public class DivorsiarMatrimonioTask extends MatrimonioTask {

    private Jugador source;

    public DivorsiarMatrimonioTask(Matrimonio matrimonio, Jugador source) {
        super(matrimonio);
        this.source = source;
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        try {
            Jugador parejeUnoDB = em.find(Jugador.class,matrimonio.getParejaUno().getNombre().toLowerCase());
            Jugador parejeDosDB = em.find(Jugador.class,matrimonio.getParejaDos().getNombre().toLowerCase());
            Matrimonio matrimonioDB = em.find(Matrimonio.class, this.matrimonio.getId());
            em.getTransaction().begin();
            matrimonioDB.setEstado(new Divorsio());
            parejeUnoDB.setMatrim(null);
            parejeDosDB.setMatrim(null);
            em.getTransaction().commit();

            Jugador parejaUno = SesionControlador.getJugador(matrimonio.getParejaUno().getNombre());
            if(parejaUno!=null) {
                parejaUno.setMatrim(null);
                parejaUno.actualizarPrefijo();
            }
            Jugador parejaDos = SesionControlador.getJugador(matrimonio.getParejaDos().getNombre());
            if(parejaDos!=null) {
                parejaDos.setMatrim(null);
                parejaDos.actualizarPrefijo();
            }
            this.matrimonio.setEstado(new Divorsio());
            //TODO LDControlador.nuevoLD(lugar);
            this.source.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Divorsiado correctamente"));
        }catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            em.close();
        }
    }
}
