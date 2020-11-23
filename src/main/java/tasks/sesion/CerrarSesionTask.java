package tasks.sesion;

import controladores.BCC;
import controladores.BaseDeDatosControlador;
import controladores.SesionControlador;
import entidades.Sesion;
import org.spongepowered.api.scheduler.Task;
import tasks.SesionTask;

import javax.persistence.EntityManager;

public class CerrarSesionTask extends SesionTask {

    public CerrarSesionTask(Sesion ss) {
        super(ss);
    }

    @Override
    public void accept(Task task) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        Sesion sesionDB = em.find(Sesion.class, ss.getId());
        em.getTransaction().begin();
        sesionDB.cerrarSesion();
        ss.cerrarSesion();
        em.getTransaction().commit();
        em.close();
        SesionControlador.sacarSesion(ss.getJugador().getNombre());
    }
}
