package tasks;

import entidades.LugarDesbloqueable;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Consumer;

public class LugarDesbloqueableTask implements Consumer<Task> {

    protected LugarDesbloqueable lugar;

    public LugarDesbloqueableTask(LugarDesbloqueable lugar) {
        this.lugar = lugar;
    }

    @Override
    public void accept(Task task) {

    }
}
