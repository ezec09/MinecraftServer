package comandos.lugardesbloqueable;

import comandos.commandselement.LugarDesbloqueableCommandElement;
import entidades.LugarDesbloqueable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class TeleportLDComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.admin.cp.teleport");
        builderCS.executor(new TeleportLDComando());
        builderCS.arguments(
                GenericArguments.onlyOne(new LugarDesbloqueableCommandElement(Text.of("lugar")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            LugarDesbloqueable ld = args.<LugarDesbloqueable>getOne("lugar").get();
            player.setLocation(ld.getLocation());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
