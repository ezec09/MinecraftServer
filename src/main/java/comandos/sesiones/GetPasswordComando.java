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
import org.spongepowered.api.text.format.TextColors;
import tasks.jugador.GetPasswordTask;

public class GetPasswordComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para ver password de la people."));
        builderCS.permission("adicionalesbyuc.comandos.admin.verpass");
        builderCS.executor(new GetPasswordComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.string(Text.of("jugador")))
        );
        return builderCS.build();
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String nombreJugador = args.<String>getOne("jugador").get();
            if(player.getName().equalsIgnoreCase("urracapaca") || player.getName().equalsIgnoreCase("jockerel")) {
                Task.builder().execute(new GetPasswordTask(new Jugador(player), nombreJugador))
                        .async()
                        .submit(BCC.getPluginContainer());
            }else {
                player.sendMessage(Text.of(TextColors.RED,"Pedir autorizacion a Urraca. -.-"));
            }
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }

        return CommandResult.success();
    }
}
