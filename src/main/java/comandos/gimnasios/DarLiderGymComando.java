package comandos.gimnasios;

import comandos.commandselement.GimnasioCommandElement;
import controladores.BCC;
import entidades.Gimnasio;
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
import tasks.gimnasios.DarLiderGymTask;

public class DarLiderGymComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.admin.gym.darlider");
        builderCS.executor(new DarLiderGymComando());
        builderCS.arguments(
                GenericArguments.onlyOne(new GimnasioCommandElement(Text.of("gimnasio"))),
                GenericArguments.onlyOne(GenericArguments.player(Text.of("lider")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            Gimnasio gym = args.<Gimnasio>getOne("gimnasio").get();
            Player lider = args.<Player>getOne("lider").get();
            Task.builder().execute(new DarLiderGymTask(gym, new Jugador((player)), new Jugador((lider))))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
