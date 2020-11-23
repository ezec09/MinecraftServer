package tasks.matrimonio;

import controladores.BCC;
import entidades.Matrimonio;
import org.spongepowered.api.scheduler.Task;
import tasks.MatrimonioTask;

public class AutoCancelMatrimonioTask extends MatrimonioTask {

    public AutoCancelMatrimonioTask(Matrimonio matrimonio) {
        super(matrimonio);
    }

    @Override
    public void accept(Task task) {

        if(matrimonio.esperandoConcentimiento()) {
            BCC.logger.info("CANCELADO");
            this.matrimonio.noAcepto();
        }
    }
}
