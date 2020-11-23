package entidades;

import java.time.LocalDateTime;

public class CooldownPlayer {

    private String nombre;
    private LocalDateTime fecha;

    public CooldownPlayer(String nombre) {
        this.nombre = nombre;
        this.fecha = LocalDateTime.now().plusSeconds(-5);
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarCd(Integer cd) {
        this.fecha = LocalDateTime.now().plusSeconds(cd);
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
