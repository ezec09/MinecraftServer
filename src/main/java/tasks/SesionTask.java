package tasks;

import entidades.Sesion;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Consumer;

public class SesionTask implements Consumer<Task> {

    protected Sesion ss;

    public SesionTask(Sesion ss) {
        this.ss = ss;
    }

    @Override
    public void accept(Task task) {

    }

}
