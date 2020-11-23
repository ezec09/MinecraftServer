package states.matrimonios;

import entidades.Matrimonio;

import javax.persistence.Embeddable;

@Embeddable
public abstract class EstadoMatrimonio {


    public abstract void siguienteEstado(Matrimonio matrimonio);

    public void aceptoMatrimonio(Matrimonio matrimonio) {
        //la mayoria no hace nada, solo espera consentimiento
    }

    public void noAceptoMatrimonio(Matrimonio matrimonio) {
        //la mayotia no hace nada, solo espera consentimiento
    }

    public Boolean esperandoConsentimiento() {
        return false;
    }

    public Boolean enProceso() {
        return true;
    }

}
