package tasks.centropokemon;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.storage.PlayerPartyStorage;
import controladores.BCC;
import entidades.CentroPokemon;
import entidades.Jugador;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.CentroPokemonTask;

public class SendToCpTask extends CentroPokemonTask {

    private Jugador enviado;
    private Integer segundosAEnviar = 5;

    public SendToCpTask(CentroPokemon centroPokemon, Jugador enviado) {
        super(centroPokemon);
        this.enviado = enviado;
    }

    @Override
    public void accept(Task task) {
        try {
            if(centroPokemon != null && segundosAEnviar > 0 ) {
                if(segundosAEnviar%2 != 0)
                    enviado.getPlayer().sendMessage(Text.of(TextColors.RED, "Vas a ser enviado al centro pokemon mas cercano."));
            }else if(centroPokemon != null && segundosAEnviar == 0) {
                enviado.getPlayer().sendMessage(Text.of(TextColors.GREEN, "Tus pokemons han sido curados."));
                PlayerPartyStorage playerPartyStorage = Pixelmon.storageManager.getParty(enviado.getPlayer().getUniqueId());
                Task.builder().execute(() -> {playerPartyStorage.heal();
                                              enviado.getPlayer().setLocation(centroPokemon.getLocation());
                }).submit(BCC.getPluginContainer());
                task.cancel();
            }else {
                enviado.getPlayer().sendMessage(Text.of(TextColors.RED, "Ups no hay centros pokemons cercanos"));
                task.cancel();
            }
            segundosAEnviar--;
        }catch (Exception ex) {
            task.cancel();
        }
    }
}
