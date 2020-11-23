package controladores;

import com.flowpowered.math.vector.Vector3i;
import entidades.Jugador;
import entidades.Matrimonio;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import tasks.matrimonio.AutoCancelMatrimonioTask;

import java.util.concurrent.TimeUnit;

public class MatrimonioControlador {

    private static Matrimonio matrim;

    public static Boolean hayMatrimonioCorriendo() {

        return matrim != null && matrim.enProceso();
    }

    public static void nuevoMatrimionio(Matrimonio matrimonioNuevo) {
        matrim = matrimonioNuevo;
        Task.builder().async().execute(new AutoCancelMatrimonioTask(matrim)).delay(15, TimeUnit.SECONDS).submit(BCC.getPluginContainer());
    }

    public static boolean esLaPareja(Jugador jugadorDos) {
        return matrim.getParejaDos().equals(jugadorDos);
    }

    public static Matrimonio getMatrim() {
        BCC.logger.info("GetMatrim: " +  matrim.toString());
        return matrim;
    }

    public static Location getUbicacion() {
        World world =  Sponge.getServer().getWorld("Planu").get();
        return new Location<>(world, new Vector3i(618,70,-1192));
    }
}
