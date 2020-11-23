package tasks.matrimonio;

import controladores.BCC;
import entidades.Matrimonio;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.scheduler.Task;
import tasks.MatrimonioTask;

import java.util.function.Consumer;

public class NotificarMatrimonioTask extends MatrimonioTask {

    private Integer segundosTotales, segundosFaltantes;
    private ServerBossBar casamientoBar;

    public NotificarMatrimonioTask(Matrimonio matrimonio, Integer segundosTotales) {
        super(matrimonio);
        BCC.logger.info(matrimonio.toString());
        BCC.logger.info(this.matrimonio.toString());
        this.segundosTotales = segundosTotales;
        this.segundosFaltantes = segundosTotales;
    }

    public void setCasamientoBar(ServerBossBar casamientoBar) {
        this.casamientoBar = casamientoBar;
    }

    @Override
    public void accept(Task task) {
        this.inicializarBarraContador();

        if(segundosFaltantes > 1) {
            if(casamientoBar != null) {
                casamientoBar.setPercent(Math.max(casamientoBar.getPercent() - (1.0f / (segundosTotales-1) * 1f),0.0f));
            }
        } else {
            this.matrimonio.siguienteEstado();
            casamientoBar.setVisible(false);
            casamientoBar = null;
            task.cancel();
        }

        this.segundosFaltantes = this.segundosFaltantes - 1;
    }


    private void inicializarBarraContador() {
        Integer cantidadConectados = Sponge.getServer().getOnlinePlayers().size();
        if(casamientoBar != null && cantidadConectados>0 && casamientoBar.getPlayers().size() == 0) {
            casamientoBar.addPlayers(Sponge.getServer().getOnlinePlayers());
        }
    }

}
