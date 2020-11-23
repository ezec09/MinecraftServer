package comandos.lugardesbloqueable;

import controladores.LDControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class ListarLDComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.user.ld.list");
        builderCS.arguments(GenericArguments.optionalWeak(GenericArguments.string(Text.of("all"))));
        builderCS.executor(new ListarLDComando());
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<String> mostrarTodos = args.getOne("all");
        Text.Builder bufferMensaje = Text.builder();
        Text separador = Text.of("-----------------------------------------------");
        if (mostrarTodos.isPresent() && ((String)mostrarTodos.get()).equalsIgnoreCase("all")) {
            bufferMensaje.append(separador).append(Text.NEW_LINE);
            LDControlador.getLugaresDesbloqueables().values().stream().forEach((elem) -> {
                bufferMensaje.append(Text.of(elem.toStringDetail())).append(Text.NEW_LINE);
            });
            bufferMensaje.append(separador);
            src.sendMessage(bufferMensaje.build());
        }else {
            Jugador jugador = SesionControlador.getJugador(src.getName());
            bufferMensaje.append(separador).append(Text.NEW_LINE);
            jugador.getLugarDesbloqueados().stream().forEach((elem) -> {
                bufferMensaje.append(Text.of(elem.getNombre())).append(Text.NEW_LINE);
            });
            bufferMensaje.append(separador);
            src.sendMessage(bufferMensaje.build());
        }
        return CommandResult.success();
    }
}
