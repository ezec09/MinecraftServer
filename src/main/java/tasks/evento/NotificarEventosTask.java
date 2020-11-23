package tasks.evento;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class NotificarEventosTask implements Consumer<Task> {

    private LocalDateTime eventoProx;
    private int ultimaDiferencia;
    private ServerBossBar countDown;

    public NotificarEventosTask(LocalDateTime eventoProx) {
        this.eventoProx = eventoProx;
    }

    public void setCountDown(ServerBossBar countDown) {
        this.countDown = countDown;
    }

    public void accept(Task task) {
        int faltante = Long.valueOf(Duration.between(LocalDateTime.now(), this.eventoProx).getSeconds()).intValue();
        this.inicializarBarraContador();

        if (faltante != this.ultimaDiferencia) {
            Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.AQUA, TextStyles.ITALIC, "Evento en " + faltante + " segundos."));
            if(countDown != null) {
                countDown.setPercent(Math.max(countDown.getPercent() - (1.0f / 9 * 1f),0.0f));
            }
        }

        if (faltante <= 1) {
            countDown.setVisible(false);
            countDown = null;
            task.cancel();
        }

        this.ultimaDiferencia = faltante;
    }

    private void inicializarBarraContador() {
        int cantidadConectados = Sponge.getServer().getOnlinePlayers().size();
        if(countDown != null && cantidadConectados>0 && countDown.getPlayers().size() == 0) {
            countDown.addPlayers(Sponge.getServer().getOnlinePlayers());
        }
    }

}
