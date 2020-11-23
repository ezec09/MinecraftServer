package comandos.centropokemon;

import com.flowpowered.math.vector.Vector3i;
import controladores.BCC;
import controladores.SesionControlador;
import entidades.CentroPokemon;
import entidades.Jugador;
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
import tasks.centropokemon.GuardarCPTask;

public class GuardarCPComando implements CommandExecutor {


    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.admin.cp.crear");
        builderCS.executor(new GuardarCPComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.string(Text.of("nombre")))
        );
        return builderCS.build();
    }


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String nombreCP = args.<String>getOne("nombre").get();
            Vector3i posicion = ((Player) src).getLocation().getBlockPosition();
            String nombreWorld = ((Player) src).getLocation().getExtent().getName();
            Jugador jugador = SesionControlador.getJugador(player.getName());
            CentroPokemon cp = new CentroPokemon(nombreCP,posicion,nombreWorld);
            Task.builder().execute(new GuardarCPTask(cp, jugador))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
