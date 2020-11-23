package states.matrimonios;

import controladores.BCC;
import entidades.Matrimonio;
import org.spongepowered.api.scheduler.Task;
import tasks.matrimonio.GuardarMatrimonioTask;

import javax.persistence.Embeddable;

@Embeddable
public class Consumado extends EstadoMatrimonio {


    private boolean corriendo = true;

    @Override
    public void siguienteEstado(Matrimonio matrimonio) {
        matrimonio.setEstado(new Divorsio());
    }

    public void aplicar(Matrimonio matrim) {
        Task.builder().execute(new GuardarMatrimonioTask(matrim, this)).async().submit(BCC.getPluginContainer());
    }

    public void setFinalizado() {
        this.corriendo = false;
    }

    @Override
    public Boolean enProceso() {
        return corriendo;
    }
}
