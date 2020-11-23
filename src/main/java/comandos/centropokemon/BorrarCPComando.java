package comandos.centropokemon;

import comandos.commandselement.CentroPokemonCommandElement;
import controladores.BCC;
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
import tasks.centropokemon.BorrarCPTask;
import tasks.gimnasios.BorrarGymTask;

public class BorrarCPComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.admin.cp.borrar");
        builderCS.executor(new BorrarCPComando());
        builderCS.arguments(
                GenericArguments.onlyOne(new CentroPokemonCommandElement(Text.of("centropokemon")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            CentroPokemon cp = args.<CentroPokemon>getOne("centropokemon").get();
            Task.builder().execute(new BorrarCPTask(cp, new Jugador((player))))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
