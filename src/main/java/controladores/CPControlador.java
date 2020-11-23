package controladores;

import com.flowpowered.math.vector.Vector3i;
import entidades.CentroPokemon;
import entidades.Jugador;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CPControlador  {

    private static ConcurrentHashMap<String, CentroPokemon> centroPokemons ;

    public static void inicializar() {
        centroPokemons = new ConcurrentHashMap<>();
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        TypedQuery<CentroPokemon> cpQuery = em.createQuery("SELECT c FROM CentroPokemon c",CentroPokemon.class);
        List<CentroPokemon> gyms = cpQuery.getResultList();
        gyms.stream().forEach(elem -> centroPokemons.put(elem.getNombre(),elem));
        em.close();
    }

    public static void nuevoCP(CentroPokemon centroPokemon) {
        centroPokemons.put(centroPokemon.getNombre(),centroPokemon);
    }

    public static List<CentroPokemon> getCps() {
        return new ArrayList<>(centroPokemons.values());
    }

    public static CentroPokemon getCp(String nombreCP) {
        return centroPokemons.get(nombreCP);
    }

    public static void removerCP(String nombre) {
        centroPokemons.remove(nombre);
    }

    public static CentroPokemon closestTo(Jugador player) {
        Vector3i pos = player.getPlayer().getLocation().getBlockPosition();
        CentroPokemon cpMasCerca = CPControlador.getCps().get(0);
        Integer distanciaMasCerca = cpMasCerca.distanciaASinRaiz(pos);
        Iterator var5 = centroPokemons.values().iterator();

        while(var5.hasNext()) {
            CentroPokemon cp = (CentroPokemon)var5.next();
            Integer distTemp = cp.distanciaASinRaiz(pos);
            if (distTemp < distanciaMasCerca) {
                cpMasCerca = cp;
                distanciaMasCerca = distTemp;
            }
        }

        return cpMasCerca;
    }
}
