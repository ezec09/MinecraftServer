package entidades;

import controladores.BCC;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import scala.Int;
import states.matrimonios.EsperaConsentimiento;
import states.matrimonios.EstadoMatrimonio;

import javax.persistence.*;

@Entity
public class Matrimonio {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    private Jugador parejaUno;

    @OneToOne(fetch = FetchType.EAGER)
    private Jugador parejaDos;

    @Embedded
    private EstadoMatrimonio estado;

    public Matrimonio(Jugador parejaUno, Jugador parejaDos) {
        this.parejaUno = parejaUno;
        this.parejaDos = parejaDos;
        this.estado = new EsperaConsentimiento(this);
    }

    public Integer getId() {
        return id;
    }

    public Jugador getParejaUno() {
        return parejaUno;
    }

    public Jugador getParejaDos() {
        return parejaDos;
    }

    public void acepto() {
        BCC.logger.info("Acepto: " +  this.toString());
        estado.aceptoMatrimonio(this);
    }

    public void noAcepto() {
        estado.noAceptoMatrimonio(this);
    }

    public void siguienteEstado() {
        estado.siguienteEstado(this);
    }

    public void setEstado(EstadoMatrimonio estado) {
        this.estado = estado;
    }

    public Boolean enProceso() {
        return this.estado.enProceso();
    }

    public Boolean esperandoConcentimiento() {
        return this.estado.esperandoConsentimiento();
    }

    public Text getPrefijo() {
        String letraUno = parejaUno.getNombre().substring(0,1);
        String letraDos = parejaDos.getNombre().substring(0,1);
        String corazon = "\u2764";
        Text parteUno =  Text.of(TextColors.GOLD, TextStyles.BOLD, "[" + letraUno);
        Text corazoncito =  Text.of(TextColors.RED, TextStyles.BOLD, corazon);
        Text parteDos =  Text.of(TextColors.GOLD, TextStyles.BOLD, letraDos + "]");
        return  parteUno.concat(corazoncito).concat(parteDos);
    }
}
