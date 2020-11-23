package Database;

import entidades.Jugador;

public class JugadoresDb extends OperacionesDb{

    public boolean existeJugador(String nombre) {
        return em.find(Jugador.class, nombre.toLowerCase()) != null;
    }


}
