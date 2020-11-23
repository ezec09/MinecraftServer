package tasks.lugardesbloqueable;

import controladores.BCC;
import controladores.BaseDeDatosControlador;
import controladores.LDControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.LugarDesbloqueable;
import entidades.PlayerAnalisisLocationElement;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.text.title.Title;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RevisarPosTask implements Consumer<Task> {

    public static void lanzarCheck() {
        RevisarPosTask revisarPosicionesTask = new RevisarPosTask();
        Task.builder().execute(revisarPosicionesTask).async().delay(5L, TimeUnit.SECONDS).submit(BCC.getPluginContainer());
    }

    @Override
    public void accept(Task task) {
        try {

            Collection<LugarDesbloqueable> lugaresDesbloqueables = LDControlador.getLugaresDesbloqueables().values();
            List<PlayerAnalisisLocationElement> onlinePlayers = SesionControlador.getJugadores().stream().map(elem -> new PlayerAnalisisLocationElement(elem)).collect(Collectors.toList());
            onlinePlayers.stream().forEach((player) ->
                    lugaresDesbloqueables.stream().filter((lugar) -> lugar.tePuedoDesbloquear(player.getJugador())).forEach((lugar) -> player.desbloqueoElLugar(lugar)
                )
            );
            List<PlayerAnalisisLocationElement> playersQueDesbloquearonUnLugar = onlinePlayers.stream().filter((players) -> players.desbloqueeoAlgo()).collect(Collectors.toList());
            if (!playersQueDesbloquearonUnLugar.isEmpty()) {
                this.guardarDesbloqueos(playersQueDesbloquearonUnLugar);
                playersQueDesbloquearonUnLugar.stream().forEach((locationAnalisis) -> {
                    locationAnalisis.getJugador().getPlayer().sendTitle(this.getMensaje(locationAnalisis.getLugar()));
                    locationAnalisis.getJugador().getPlayer().sendMessage(Text.of(TextColors.GOLD, TextStyles.BOLD, "Felicitaciones!!, has desbloqueado " + locationAnalisis.getLugar().getNombre().toUpperCase() + "."));
                    locationAnalisis.getJugador().desbloqueasteUnLugar(locationAnalisis.getLugar());
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            lanzarCheck();
        }
    }

    private void guardarDesbloqueos(List<PlayerAnalisisLocationElement> desbloqueados) {
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        em.getTransaction().begin();
        desbloqueados.stream().forEach(elem -> {
            Jugador jugador = em.find(Jugador.class,elem.getNombre().toLowerCase());
            jugador.desbloqueasteUnLugar(elem.getLugar());
        });
        em.getTransaction().commit();
        em.close();
    }

    private Title getMensaje(LugarDesbloqueable lugar) {
        Title.Builder titulador = Title.builder();
        titulador.fadeIn(20).stay(50).fadeOut(20);
        titulador.title(Text.of(TextColors.GOLD, "Desbloqueaste: "));
        titulador.subtitle(Text.of(TextColors.GOLD, lugar.getNombre() + "!!"));
        return titulador.build();
    }
}
