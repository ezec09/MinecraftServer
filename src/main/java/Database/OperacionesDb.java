package Database;

import controladores.BaseDeDatosControlador;

import javax.persistence.EntityManager;

public class OperacionesDb {

    protected EntityManager em;

    public OperacionesDb() {
        em = BaseDeDatosControlador.obtenerEntityManager();
    }

    public EntityManager getEm() {
        return em;
    }

    public void cerrarEntityManager() {
        em.close();
    }
}
