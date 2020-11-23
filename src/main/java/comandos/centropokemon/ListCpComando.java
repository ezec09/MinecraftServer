package comandos.centropokemon;

import controladores.BCC;
import controladores.CPControlador;
import entidades.CentroPokemon;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;

public class ListCpComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.user.cp.listar");
        builderCS.executor(new ListCpComando());
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            List<CentroPokemon> cps = CPControlador.getCps();
            Text.Builder bufferMensaje = Text.builder();
            Text separador = Text.of("-----------------------------------------------");
            bufferMensaje.append(separador).append(Text.NEW_LINE);
            cps.forEach((elem) -> {
                bufferMensaje.append(Text.of(elem.toString())).append(Text.NEW_LINE);
            });
            bufferMensaje.append(separador);
            src.sendMessage(bufferMensaje.build());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
