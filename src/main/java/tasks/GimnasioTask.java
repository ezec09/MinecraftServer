package tasks;

import entidades.Gimnasio;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Consumer;

public class GimnasioTask implements Consumer<Task> {

    protected Gimnasio gym;

    public GimnasioTask(Gimnasio gym) {
        this.gym = gym;
    }

    @Override
    public void accept(Task task) {
        
    }

}
