package tasks.evento;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import controladores.BCC;
import controladores.BossBarControlador;
import entidades.BossSpawnConfig;
import entidades.EstadoEventos;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import com.flowpowered.math.vector.Vector3i;

public class EventoTask implements Consumer<Task> {
    private LocalDateTime proximoEvento;
    private Boolean corrioNotificacionCincoMinutos;
    private Boolean corrioNotificacionCooldown;
    private EstadoEventos estadoEvento;
    private BossSpawnEventTask eventoCorriendo;
    private ArrayList<BossSpawnConfig> configsPosibles;

    public EventoTask() {
        this.corrioNotificacionCooldown = false;
        this.corrioNotificacionCincoMinutos = false;
        this.estadoEvento = EstadoEventos.NOCORRIO;
        this.configsPosibles = new ArrayList();
        this.proximoEvento = null;
    }

    private LocalDateTime calcularTimeProximoEvento() {
        int playersOnline = Sponge.getServer().getOnlinePlayers().size();
        if (playersOnline < 10) {
            return LocalDateTime.now().plusHours(2L);
        } else {
            return playersOnline < 20 ? LocalDateTime.now().plusHours(1L) : LocalDateTime.now().plusMinutes(30L);
        }
    }

    public void accept(Task task) {
        //Se calcula la primera vez que corre, corre despues de 3 minutos que inicio el server.
        //Hice esto para que si lo reiniciamos, los users tengan tiempo para reloguear.
        if(proximoEvento == null)
            this.proximoEvento = this.calcularTimeProximoEvento();
        int faltante = Long.valueOf(Duration.between(LocalDateTime.now(), this.proximoEvento).getSeconds()).intValue();
        this.notificarEvento(faltante);
        this.runEvento(faltante);
    }

    private void notificarEvento(int faltante) {
        if (faltante <= 300 && !this.corrioNotificacionCincoMinutos) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.AQUA, TextStyles.ITALIC, "Evento en 5 minutos. Utiliza /participar cuando inicie."));
            this.corrioNotificacionCincoMinutos = true;
        } else if (faltante < 10 && !this.corrioNotificacionCooldown) {
            NotificarEventosTask notifEventoTask = new NotificarEventosTask(this.proximoEvento);
            notifEventoTask.setCountDown(BossBarControlador.getSVBForEvento());
            Task.builder().execute(notifEventoTask).interval(1L, TimeUnit.SECONDS).submit(BCC.getPluginContainer());
            this.corrioNotificacionCooldown = true;
        }

    }

    private void runEvento(int faltante) {
        int onlinePlayers = Sponge.getServer().getOnlinePlayers().size();
        if (this.estadoEvento == EstadoEventos.NOCORRIO && faltante <= 0) {
            int indexConfigRandmon = Double.valueOf(Math.random() * 100.0D % (double)this.configsPosibles.size()).intValue();
            int spawnSimultaneos = onlinePlayers > 7 ? 2 : 1;
            int spawnDelay = onlinePlayers > 5 ? 3 : 5;
            int duracion = Math.max(onlinePlayers+1,10); ///cambiar en produccion
            ArrayList<String> pokemons = this.configsPosibles.get(indexConfigRandmon).getPokemon();
            Vector3i posCentral = this.configsPosibles.get(indexConfigRandmon).getPosCentral();
            BCC.logger.info(this.configsPosibles.get(indexConfigRandmon).getNombreLugar());
            BossSpawnEventTask evento = new BossSpawnEventTask(spawnSimultaneos, spawnDelay, duracion, pokemons, posCentral, 20);
            Task.builder().interval(1L, TimeUnit.SECONDS).execute(evento).submit(BCC.getPluginContainer());
            this.eventoCorriendo = evento;
            this.estadoEvento = EstadoEventos.CORRIENDO;
            Text mensajito = Text.builder().append(Text.of(TextColors.GREEN, TextStyles.ITALIC, "Utiliza /participar.(Click aqui!)")).onClick(TextActions.runCommand("/participar")).build();
            Sponge.getServer().getBroadcastChannel().send(mensajito);
        } else if (this.estadoEvento == EstadoEventos.FINALIZADO) {
            this.proximoEvento = this.calcularTimeProximoEvento();
            this.corrioNotificacionCooldown = false;
            this.corrioNotificacionCincoMinutos = false;
            this.estadoEvento = EstadoEventos.NOCORRIO;
        }
    }

    public void finalizoEvento() {
        this.estadoEvento = EstadoEventos.FINALIZADO;
        this.eventoCorriendo = null;
        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.GREEN, "Evento finalizado, utiliza /eventoestado para ver el proximo evento."));
    }

    public Text getEventoInfo() {
        if(this.proximoEvento == null) {
            return Text.of(TextColors.RED, TextStyles.ITALIC, "No hay eventos programados aun!!");
        }
        Duration diferencia = Duration.between(LocalDateTime.now(), this.proximoEvento);
        Long segundosFaltantes = diferencia.getSeconds();
        Text infoEvento;
        if (segundosFaltantes.intValue() <= 300) {
            infoEvento = Text.of(TextColors.AQUA, TextStyles.ITALIC, "Proximo evento de spawn de bosses en " + segundosFaltantes.intValue() + " segundos.");
        } else if (segundosFaltantes.intValue() <= 3600) {
            infoEvento = Text.of(TextColors.AQUA, TextStyles.ITALIC, "Proximo evento de spawn de bosses en " + Long.valueOf(diferencia.toMinutes()).intValue() + " minutos.");
        } else {
            infoEvento = Text.of(TextColors.AQUA, TextStyles.ITALIC, "Proximo evento de spawn de bosses en " + Long.valueOf(diferencia.toHours()).intValue() + " horas aproximadamente.");
        }

        return infoEvento;
    }

    public boolean hayEventoCorriendo() {
        return this.eventoCorriendo != null;
    }

    public Location<World> getUbicacionEvento() {
        return this.eventoCorriendo.getUbicacion();
    }

    public void setConfigsPosibles(ArrayList<BossSpawnConfig> configsPosibles) {
        this.configsPosibles = configsPosibles;
    }

    public ArrayList<BossSpawnConfig> getConfigsPosibles() {
        return this.configsPosibles;
    }

    public LocalDateTime getProximoEvento() {
        return this.proximoEvento;
    }

    public void cambiarTiempoDeEvento(LocalDateTime nuevoTime) {
        this.proximoEvento = nuevoTime;
    }
}
