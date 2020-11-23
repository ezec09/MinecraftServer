package entidades;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Sesion {

    @Id @GeneratedValue Long id;
    private Jugador jugador;
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Temporal(TemporalType.TIMESTAMP)
    private Date cierre;

    public Sesion(Jugador jugador) {
        this.jugador = jugador;
        this.inicio = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Date getCierre() {
        return cierre;
    }

    public void setCierre(Date cierre) {
        this.cierre = cierre;
    }

    public void cerrarSesion() {
        this.cierre = new Date();
    }
}
