package entidades;

public class PlayerAnalisisLocationElement {

    Jugador jugador;
    LugarDesbloqueable lugar = null;

    public PlayerAnalisisLocationElement(Jugador jugador) {
        this.jugador = jugador;
    }

    public void desbloqueoElLugar(LugarDesbloqueable lugar) {
        this.lugar = lugar;
    }

    public boolean desbloqueeoAlgo() {
        return this.lugar != null;
    }

    public String getNombre() {
        return this.jugador.getNombre();
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public LugarDesbloqueable getLugar() {
        return this.lugar;
    }
}
