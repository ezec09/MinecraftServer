package states.matrimonios;

import controladores.BCC;
import controladores.BossBarControlador;
import entidades.Matrimonio;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import tasks.evento.NotificarEventosTask;
import tasks.matrimonio.NotificarMatrimonioTask;

import java.util.concurrent.TimeUnit;

public class Anunciando extends EstadoMatrimonio {

    @Override
    public void siguienteEstado(Matrimonio matrimonio) {
        EntregandoRegalo regalos = new EntregandoRegalo();
        matrimonio.setEstado(regalos);
        regalos.aplicar(matrimonio);
    }


    public void aplicar(Matrimonio matrimonio) {
        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.AQUA, "Casamiento en curso, usa /iracasamiento"));
        NotificarMatrimonioTask notificacion = new NotificarMatrimonioTask(matrimonio,15);
        notificacion.setCasamientoBar(BossBarControlador.getSVBForCasamiento(
                matrimonio.getParejaUno().getNombre(),
                matrimonio.getParejaDos().getNombre()
        ));

        Task.builder().execute(notificacion).async().interval(1, TimeUnit.SECONDS).submit(BCC.getPluginContainer());
    }
}
