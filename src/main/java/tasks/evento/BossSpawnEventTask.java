package tasks.evento;

import com.flowpowered.math.vector.Vector3i;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

import controladores.EventoControlador;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


public class BossSpawnEventTask implements Consumer<Task> {
    private Integer spawnSimultaneos = 1;
    private Integer spawnDelay = 1;
    private Integer duracion = 20;
    private ArrayList<String> pokemon;
    private Vector3i posCentral;
    private Integer maxFromCentral = 6;
    private World mundo;
    private Integer corridas = 0;
    private ArrayList<Integer> bossesTipo;

    public BossSpawnEventTask(Integer spawnSimultaneos, Integer spawnDelay, Integer duracion, ArrayList<String> pokemon, Vector3i posCentral, Integer maxFromCentral) {
        this.spawnSimultaneos = spawnSimultaneos;
        this.spawnDelay = spawnDelay;
        this.duracion = duracion;
        this.pokemon = pokemon;
        this.posCentral = posCentral;
        this.maxFromCentral = maxFromCentral;
        this.mundo = (World)Sponge.getServer().getWorld("Planu").get();
        this.bossesTipo = new ArrayList();
        this.bossesTipo.add(1);
        this.bossesTipo.add(2);
        //this.bossesTipo.add("Legendary");
        //this.bossesTipo.add("Ultimate");
    }

    public void accept(Task task) {
        int cantidadDeConectados = Sponge.getServer().getOnlinePlayers().size();
        if (this.corridas % this.spawnDelay == 0 && cantidadDeConectados > 0) {
            for(int cantidadSpawn = 0; cantidadSpawn < this.spawnSimultaneos; ++cantidadSpawn) {
                String comando = "pokespawncoords ";
                int pokemonIndexSpawn = Double.valueOf(Math.random() * 100.0D % (double)this.pokemon.size()).intValue();
                Vector3i spawnPoint = this.calcularSpawnPosition().getPosition().toInt();
                Integer randomBoss = this.bossesTipo.get(Double.valueOf(Math.random() * 100.0D % (double)this.bossesTipo.size()).intValue());
                String pokemon = (String)this.pokemon.get(pokemonIndexSpawn);
                comando = comando.concat(pokemon + " " + spawnPoint.getX() + " " + spawnPoint.getY() + " " + spawnPoint.getZ() + " boss:" + randomBoss);
                Sponge.getCommandManager().process(Sponge.getServer().getConsole(), comando);
            }
        }

        if (this.corridas > this.duracion || cantidadDeConectados == 0) {
            task.cancel();
            EventoControlador.getEventoTask().finalizoEvento();
        }

        this.corridas = this.corridas + 1;
    }

    public Location calcularSpawnPosition() {
        Integer x = this.posCentral.getX();
        Integer y = this.posCentral.getY();
        Integer z = this.posCentral.getZ();

        Optional posicionASpawn;
        do {
            Double offset = Math.random() * 1000.0D;
            Integer operacionMath = Math.random() <= 0.5D ? 1 : -1;
            Integer xzOffset = offset.intValue() % this.maxFromCentral * operacionMath;
            Integer yOffset = offset.intValue() % 5;
            Location posicion = new Location<>(this.mundo, new Vector3i(xzOffset + x, yOffset + y, xzOffset + z));
            posicionASpawn = Sponge.getTeleportHelper().getSafeLocation(posicion, 3, 3);
        } while(!posicionASpawn.isPresent());

        return (Location)posicionASpawn.get();
    }

    public Location getUbicacion() {
        return new Location<>(this.mundo, this.posCentral);
    }
}

