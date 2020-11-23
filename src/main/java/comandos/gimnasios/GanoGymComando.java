package comandos.gimnasios;

import comandos.commandselement.GimnasioCommandElement;
import controladores.BCC;
import controladores.SesionControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import entidades.Sesion;
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
import tasks.gimnasios.GanoGymTask;

public class GanoGymComando implements CommandExecutor {


    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.user.gym.gano");
        builderCS.executor(new GanoGymComando());
        builderCS.arguments(
                GenericArguments.onlyOne(new GimnasioCommandElement(Text.of("gimnasio"))),
                GenericArguments.onlyOne(GenericArguments.player(Text.of("ganador")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            Gimnasio gym = args.<Gimnasio>getOne("gimnasio").get();
            Player ganadorPL = args.<Player>getOne("ganador").get();
            Jugador ganador = SesionControlador.getJugador(ganadorPL.getName());
            if(ganador==null) {
                src.sendMessage(Text.of(TextColors.RED, "No existe ese jugador."));
                return CommandResult.empty();
            }
            Task.builder().execute(new GanoGymTask(gym, new Jugador((player)), ganador))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
