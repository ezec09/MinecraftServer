package states.matrimonios;

import entidades.Matrimonio;

import javax.persistence.Embeddable;

@Embeddable
public class Divorsio extends EstadoMatrimonio {

    @Override
    public void siguienteEstado(Matrimonio matrimonio) {

        // no hay nada mas
    }

    @Override
    public Boolean enProceso() {
        return false;
    }
}
