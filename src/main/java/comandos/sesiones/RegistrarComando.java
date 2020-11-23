package comandos.sesiones;

import controladores.BCC;
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
import tasks.jugador.RegistrarTask;

public class RegistrarComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para registrarse al server."));
        //builderCS.permission("adicionalesbyuc.comandos.sesiones.registrar");
        builderCS.executor(new RegistrarComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.string(Text.of("password"))),
                GenericArguments.onlyOne(GenericArguments.string(Text.of("passwordRepeat")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String jugadorUUID = player.getUniqueId().toString();
            String jugadorNombre = player.getName();
            Jugador jugador = new Jugador(jugadorNombre, jugadorUUID);
            Task.builder().execute(new RegistrarTask(jugador, args))
                            .async()
                            .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
