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
import tasks.jugador.LoginTask;

public class LoginComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para loguearse al server."));
        //builderCS.permission("adicionalesbyuc.comandos.admin.verpass");
        builderCS.executor(new LoginComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.string(Text.of("password")))
        );
        return builderCS.build();
    }


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String password = args.<String>getOne("password").get();
            Task.builder().execute(new LoginTask(new Jugador(player), password))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
