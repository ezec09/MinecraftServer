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
import tasks.jugador.CambiarPasswordTask;

public class CambiarPasswordComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.user.cambiarpassword");
        builderCS.executor(new CambiarPasswordComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.string(Text.of("passwordOld"))),
                GenericArguments.onlyOne(GenericArguments.string(Text.of("passwordNew")))
        );
        return builderCS.build();
    }


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String passwordNew = args.<String>getOne("passwordNew").get();
            String passwordOld = args.<String>getOne("passwordOld").get();
            Task.builder().execute(new CambiarPasswordTask(new Jugador(player), passwordNew, passwordOld))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
