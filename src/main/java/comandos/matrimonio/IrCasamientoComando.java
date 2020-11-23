package comandos.matrimonio;

import controladores.EventoControlador;
import controladores.MatrimonioControlador;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.evento.EventoTask;

public class IrCasamientoComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Utilizalo para ir al casamiento en curso"));
        builderCS.permission("adicionalesbyuc.comandos.user.casamiento.ir");
        builderCS.executor(new IrCasamientoComando());
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player ply = (Player)src;
            if (MatrimonioControlador.hayMatrimonioCorriendo()) {
                ply.setLocation(MatrimonioControlador.getUbicacion());
            } else {
                ply.sendMessage(Text.of(TextColors.RED, "No hay casamiento en curso"));
            }
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
