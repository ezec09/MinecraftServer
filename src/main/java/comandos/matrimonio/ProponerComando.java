package comandos.matrimonio;

import com.flowpowered.math.vector.Vector3i;
import controladores.BCC;
import controladores.MatrimonioControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.Matrimonio;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import tasks.jugador.GetPasswordTask;

public class ProponerComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para ver proponer casamiento a otra persona."));
        builderCS.permission("adicionalesbyuc.comandos.user.proponer");
        builderCS.executor(new ProponerComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.player(Text.of("pareja")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Vector3i sacerdote = new Vector3i(595,71,-1192);
        String mundo = "Planu";
        Float distanciaAlCuadrado = 25.0f;
        if (src instanceof Player) {
            Player player = (Player) src;
            Jugador parejaUno = SesionControlador.getJugador(src.getName());
            Player parejaDosPlayer = args.<Player>getOne("pareja").get();
            Jugador parejaDos = SesionControlador.getJugador(parejaDosPlayer.getName());
            Boolean estaEnPlanuPU = player.getLocation().getExtent().getName().equalsIgnoreCase(mundo);
            Boolean estaEnPlanuPD = parejaDosPlayer.getLocation().getExtent().getName().equalsIgnoreCase(mundo);
            Boolean estaCercaCuraPU = player.getPosition().distanceSquared(sacerdote.toDouble()) < distanciaAlCuadrado;
            Boolean estaCercaCuraPD = parejaDosPlayer.getPosition().distanceSquared(sacerdote.toDouble()) < distanciaAlCuadrado;
            if(parejaUno.estaCasado()) {
                parejaUno.getPlayer().sendMessage(Text.of(TextColors.RED, "Ya estas casado."));
            } else if(parejaDos.estaCasado()) {
                parejaUno.getPlayer().sendMessage(Text.of(TextColors.RED, parejaDos.getNombre() + " ya esta casado."));
            } else if(parejaDos.equals(parejaUno)) {
                parejaUno.getPlayer().sendMessage(Text.of(TextColors.RED, "No te podes casar con vos mismo."));
            } else if(MatrimonioControlador.hayMatrimonioCorriendo()) {
                player.sendMessage(Text.of(TextColors.RED, "Hay otro casamiento en proceso"));
            } else if (!estaEnPlanuPD || !estaEnPlanuPU || !estaCercaCuraPU || !estaCercaCuraPD) {
                player.sendMessage(Text.of(TextColors.RED, "No estan cerca del cura!"));
            } else {
                player.sendMessage(Text.of(TextColors.GREEN, "Le has propuesto casamiento a " + parejaDos.getNombre()));
                MatrimonioControlador.nuevoMatrimionio(new Matrimonio(parejaUno,parejaDos));
            }
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }

        return CommandResult.success();
    }
}
