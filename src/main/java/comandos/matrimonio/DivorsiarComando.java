package comandos.matrimonio;

import com.flowpowered.math.vector.Vector3i;
import controladores.BCC;
import controladores.MatrimonioControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.Matrimonio;
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
import tasks.matrimonio.DivorsiarMatrimonioTask;

public class DivorsiarComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para matar parejas."));
        builderCS.permission("adicionalesbyuc.comandos.admin.divorsiar");
        builderCS.executor(new DivorsiarComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.player(Text.of("jugador")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            Jugador source = SesionControlador.getJugador(player.getName());
            Player playerADivorsiar = args.<Player>getOne("jugador").get();
            Jugador jugadorADivorsiar = SesionControlador.getJugador(playerADivorsiar.getName());
            if(!jugadorADivorsiar.estaCasado()) {
                src.sendMessage(Text.of(TextColors.RED,"No esta casado!"));
            }else {
                Task.builder().async().execute(new DivorsiarMatrimonioTask(jugadorADivorsiar.getMatrimonio(),source)).submit(BCC.getPluginContainer());
            }
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }

        return CommandResult.success();
    }
}
