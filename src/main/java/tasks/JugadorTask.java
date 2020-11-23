package tasks;

import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;

import java.util.function.Consumer;

public class JugadorTask implements Consumer<Task> {

    protected Jugador jugador;

    public JugadorTask(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public void accept(Task task) {

    }


}
