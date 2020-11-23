package controladores;

import com.flowpowered.math.vector.Vector3i;
import entidades.BossSpawnConfig;
import org.spongepowered.api.scheduler.Task;
import tasks.evento.EventoTask;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class EventoControlador {

    private static EventoTask eventoTask;

    public static void crearEvenetoTask() {
        ArrayList<BossSpawnConfig> configs = EventoControlador.getConfigs();
        eventoTask = new EventoTask();
        eventoTask.setConfigsPosibles(configs);
    }

    private static ArrayList<BossSpawnConfig> getConfigs() {
        ArrayList<BossSpawnConfig> bossSpawnConfigs = new ArrayList<>();
        ArrayList<String> pokemonNormal = new ArrayList<>();
        pokemonNormal.add("forretress");
        pokemonNormal.add("leafeon");
        pokemonNormal.add("nuzleaf");
        pokemonNormal.add("exeggutor");
        bossSpawnConfigs.add(new BossSpawnConfig("gymnormal", pokemonNormal,new Vector3i(1094,75,-5682)));

        ArrayList<String> pokemonDragon = new ArrayList<>();
        pokemonDragon.add("raticate");
        pokemonDragon.add("tauros");
        pokemonDragon.add("arbok");
        pokemonDragon.add("persian");
        bossSpawnConfigs.add(new BossSpawnConfig("gymdragon", pokemonDragon,new Vector3i(-829,73,676)));

        ArrayList<String> pokemonRoca = new ArrayList<>();
        pokemonRoca.add("magmar");
        pokemonRoca.add("hippowdon");
        pokemonRoca.add("delphox");
        pokemonRoca.add("drapion");
        bossSpawnConfigs.add(new BossSpawnConfig("gymroca", pokemonRoca,new Vector3i(-58,103,509)));

        ArrayList<String> pokemonVolador = new ArrayList<>();
        pokemonVolador.add("jolteon");
        pokemonVolador.add("rhydon");
        pokemonVolador.add("scyther");
        pokemonVolador.add("typhlosion");
        bossSpawnConfigs.add(new BossSpawnConfig("gymvolador", pokemonVolador,new Vector3i(235,69,1340)));

        ArrayList<String> pokemonFuego = new ArrayList<>();
        pokemonFuego.add("heatmor");
        pokemonFuego.add("magcargo");
        pokemonFuego.add("sandslash");
        pokemonFuego.add("scrafty");
        bossSpawnConfigs.add(new BossSpawnConfig("gymfuego", pokemonFuego,new Vector3i(-5443,93,-745)));

        ArrayList<String> pokemonFantasma = new ArrayList<>();
        pokemonFantasma.add("greninja");
        pokemonFantasma.add("ludicolo");
        pokemonFantasma.add("toxicroak");
        pokemonFantasma.add("weezing");
        bossSpawnConfigs.add(new BossSpawnConfig("gymfantasma", pokemonFantasma,new Vector3i(-352,67,-396)));

        ArrayList<String> pokemonPlanta = new ArrayList<>();
        pokemonPlanta.add("galvantula");
        pokemonPlanta.add("haxorus");
        pokemonPlanta.add("scolipede");
        pokemonPlanta.add("liepard");
        bossSpawnConfigs.add(new BossSpawnConfig("gymplanta", pokemonPlanta,new Vector3i(399,74,-1951)));

        ArrayList<String> pokemonHielo = new ArrayList<>();
        pokemonHielo.add("glaceon");
        pokemonHielo.add("mamoswine");
        pokemonHielo.add("froslass");
        pokemonHielo.add("beartic");
        bossSpawnConfigs.add(new BossSpawnConfig("gymhielo", pokemonHielo,new Vector3i(969,93,-1184)));
        return bossSpawnConfigs;
    }

    public static EventoTask getEventoTask() {
        return eventoTask;
    }

    public static void iniciarTask() {
        Task.builder().delay(3, TimeUnit.MINUTES).interval(1,TimeUnit.SECONDS).execute(eventoTask).async().submit(BCC.getPluginContainer());
        //cambiar en produccion
        //Task.builder().delay(3, TimeUnit.SECONDS).interval(1,TimeUnit.SECONDS).execute(eventoTask).async().submit(BCC.getPluginContainer());
    }
}
