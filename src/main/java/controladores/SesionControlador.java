package controladores;

import entidades.Gimnasio;
import entidades.Jugador;
import entidades.LugarDesbloqueable;
import entidades.Sesion;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SesionControlador {

    private static ConcurrentHashMap<String, Sesion> cacheSesiones;

    public static void initSesionControlador() {
        cacheSesiones = new ConcurrentHashMap<>();
    }

    public static boolean jugadorEstaLogueado(String nombreJugador) {
        return existeSesion(nombreJugador);
    }

    public static Sesion nuevaSesion(Jugador jugador) {
        Sesion sesionCreada = new Sesion(jugador);
        cacheSesiones.put(jugador.getNombre().toLowerCase(), sesionCreada);
        return sesionCreada;
    }

    public static boolean existeSesion(String jugador) {
        return cacheSesiones.containsKey(jugador.toLowerCase());
    }

    public static Sesion getSesion(String jugador) {
        return cacheSesiones.get(jugador);
    }

    public static Jugador getJugador(String jugador) {
        String nombreAdaptado = jugador.toLowerCase();
        if(!cacheSesiones.containsKey(nombreAdaptado))
            return null;
        return cacheSesiones.get(nombreAdaptado).getJugador();
    }

    public static void seCerrorGym(Gimnasio gym) {
        cacheSesiones.values().stream().forEach(sesion -> sesion.getJugador().seCerroUnGym(gym));
    }

    public static Sesion sacarSesion(String jugador) {
        return cacheSesiones.remove(jugador.toLowerCase());
    }

    public static void nuevoGym() {
        cacheSesiones.values().stream().forEach(sesion -> sesion.getJugador().actualizarPrefijo());
    }

    public static Collection<Jugador> getJugadores() {
        return cacheSesiones.values().stream().map(elem-> elem.getJugador()).collect(Collectors.toList());
    }

    public static void seBorroLd(LugarDesbloqueable lugar) {
        cacheSesiones.values().stream().forEach(sesion -> sesion.getJugador().seBorrorUnLD(lugar));
    }
}
