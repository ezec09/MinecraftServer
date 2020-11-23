package eventos;

import com.google.common.collect.ImmutableMap;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.events.battles.BattleEndEvent;
import com.pixelmonmod.pixelmon.api.events.pokemon.SetNicknameEvent;
import com.pixelmonmod.pixelmon.battles.controller.participants.BattleParticipant;
import com.pixelmonmod.pixelmon.battles.controller.participants.PlayerParticipant;
import com.pixelmonmod.pixelmon.enums.battle.BattleResults;
import controladores.BCC;
import controladores.CPControlador;
import controladores.SesionControlador;
import entidades.CentroPokemon;
import entidades.Jugador;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.pixelmonmod.pixelmon.api.events.HeldItemChangedEvent;
import org.spongepowered.api.scheduler.Task;
import tasks.centropokemon.SendToCpTask;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PixelmonEvent {

    @SubscribeEvent(
            priority = EventPriority.HIGH
    )
    public void onChangeHeldItemEvent(HeldItemChangedEvent event) {
        if (event.player != null && !SesionControlador.existeSesion(event.player.getName())) {
            ItemStack item = event.pokemon.getHeldItem();
            event.newHeldItem = item.copy();
        }
    }

    @SubscribeEvent
    public void onChangeNameEvent(SetNicknameEvent event) {
        event.setCanceled(!SesionControlador.existeSesion(event.player.getName()));
    }

    @SubscribeEvent
    public void onEndBattleEvent(BattleEndEvent evento) {
        ImmutableMap<BattleParticipant, BattleResults> resultados = evento.results;
        List<PlayerParticipant> playerToSpawn = resultados.keySet().stream().filter((elem) -> {
            return elem instanceof PlayerParticipant;
        }).map((elem) -> {
            return (PlayerParticipant)elem;
        }).filter((elem) -> {
            return elem.countAblePokemon() == 0;
        }).filter((elem) -> {
            return Pixelmon.storageManager.getParty(elem.player).countAblePokemon() == 0;
        }).collect(Collectors.toList());
        playerToSpawn.stream().forEach((elem) -> {
           Jugador jugador =  SesionControlador.getJugador(elem.player.getName());
           CentroPokemon cp = CPControlador.closestTo(jugador);
           Task.builder().execute(new SendToCpTask(cp,jugador))
                    .async()
                    .interval(1L, TimeUnit.SECONDS)
                    .submit(BCC.getPluginContainer());
        });
    }

}
