package tasks;

import entidades.CentroPokemon;
import org.spongepowered.api.scheduler.Task;


import java.util.function.Consumer;

public class CentroPokemonTask implements Consumer<Task> {

    protected CentroPokemon centroPokemon;

    public CentroPokemonTask(CentroPokemon centroPokemon) {
        this.centroPokemon = centroPokemon;
    }

    @Override
    public void accept(Task task) {

    }
}
