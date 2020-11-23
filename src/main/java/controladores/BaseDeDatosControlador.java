package controladores;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;

public class BaseDeDatosControlador {

    private static EntityManagerFactory emf;

    public static void inicializar() {
        emf = Persistence.createEntityManagerFactory("objectdb:$objectdb/adicionalesbyuc/ad_datos.odb");
    }

    public static EntityManager obtenerEntityManager() {
        return emf.createEntityManager();
    }

    public static PersistenceUnitUtil getPUU() {
        return emf.getPersistenceUnitUtil();
    }

}
