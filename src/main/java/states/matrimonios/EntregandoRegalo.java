package states.matrimonios;

import com.flowpowered.math.vector.Vector3i;
import controladores.BCC;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.Matrimonio;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import tasks.evento.BossSpawnEventTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class EntregandoRegalo extends EstadoMatrimonio {

    @Override
    public void siguienteEstado(Matrimonio matrimonio) {
        Consumado cons = new Consumado();
        matrimonio.setEstado(cons);
        cons.aplicar(matrimonio);
    }

    public void aplicar(Matrimonio matrimonio) {
        /*
         * 20 pokeballs tipo poke/super/ultra
         * 20 rare candys pa todos
         * Spawn de bosses
         * 30k para todos
         */
        //lanzo evento
        double rng = (Math.random() *10000) %100;
        if(rng < 100){
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GOLD, "Celebramos el nacimiento de una nueva pareja con una lluvia de pokemons!!"));
            ArrayList<String> pokemons = new ArrayList<>();
            pokemons.add("flaébé"); pokemons.add("floette"); pokemons.add("florges");
            //flabébé , floette , florges
            BossSpawnEventTask evento = new BossSpawnEventTask(2,1,10,pokemons, new Vector3i(618,70,-1192),7);
            Task.builder().interval(1L, TimeUnit.SECONDS).execute(evento).submit(BCC.getPluginContainer());
            Sponge.getServer().getBroadcastChannel().send(Text.of("Evento"));
        }else if (rng < 60) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GOLD, "Celebramos el nacimiento de una nueva pareja con unas cuentas pokeballs!!"));
            ejecutarComandoParaTodos("give @pl pixelmon:poke_ball 10");
            ejecutarComandoParaTodos("give @pl pixelmon:great_ball 10");
            ejecutarComandoParaTodos("give @pl pixelmon:ultra_ball 10");
        }else if (rng < 80){
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GOLD, "Celebramos el nacimiento de una nueva pareja con unos caramelitos!!"));
            ejecutarComandoParaTodos("give @pl pixelmon:rare_candy 20");
        }else {
            ///givemoney @pl 15000
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GOLD, "Celebramos el nacimiento de una nueva pareja con unos pokedolares!!"));
            ejecutarComandoParaTodos("givemoney @pl 15000");
        }

        matrimonio.siguienteEstado();
    }

    private void ejecutarComandoParaTodos(String comando) {
        List<String> nombreJugadores = SesionControlador.getJugadores().stream().map(elem -> elem.getNombre()).collect(Collectors.toList());
        CommandSource consola = Sponge.getServer().getConsole();
        nombreJugadores.forEach(e-> {
            String comandoConPlayer = comando.replace("@pl", e);
            Sponge.getCommandManager().process(consola,comandoConPlayer);
        });
    }
}
