package tasks;

import entidades.Matrimonio;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Consumer;

public class MatrimonioTask implements Consumer<Task> {

    protected Matrimonio matrimonio;

    public MatrimonioTask(Matrimonio matrimonio) {
        this.matrimonio = matrimonio;
    }

    @Override
    public void accept(Task task) {

    }
}
