package controladores;

import entidades.LugarDesbloqueable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class LDControlador {

    private static ConcurrentHashMap<String, LugarDesbloqueable> lugaresDesbloqueables;

    public static void inicializar() {
        lugaresDesbloqueables = new ConcurrentHashMap<>();
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        TypedQuery<LugarDesbloqueable> gymsQuery = em.createQuery("SELECT g FROM LugarDesbloqueable g",LugarDesbloqueable.class);
        List<LugarDesbloqueable> lds = gymsQuery.getResultList();
        lds.stream().forEach(elem -> lugaresDesbloqueables.put(elem.getNombre(),elem));
        em.close();
    }

    public static void nuevoLD(LugarDesbloqueable lugar) {
        lugaresDesbloqueables.put(lugar.getNombre(),lugar);
    }

    public static LugarDesbloqueable getLugaresDesbloqueables(String nombre) {
        return lugaresDesbloqueables.get(nombre);
    }

    public static ConcurrentHashMap<String, LugarDesbloqueable> getLugaresDesbloqueables() {
        return lugaresDesbloqueables;
    }

    public static Boolean existeNCS(String nombre) {
        return lugaresDesbloqueables.values().stream().anyMatch(elem->elem.getNombre().equalsIgnoreCase(nombre));
    }

    public static void removerLD(String nombre) {
        lugaresDesbloqueables.remove(nombre);
    }
}
