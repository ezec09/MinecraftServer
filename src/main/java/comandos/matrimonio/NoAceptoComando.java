package comandos.matrimonio;

import controladores.MatrimonioControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class NoAceptoComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para no aceptar el casamiento."));
        builderCS.permission("adicionalesbyuc.comandos.user.noacepto");
        builderCS.executor(new NoAceptoComando());
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            Jugador parejaDos = SesionControlador.getJugador(player.getName());
            if(MatrimonioControlador.hayMatrimonioCorriendo() && MatrimonioControlador.esLaPareja(parejaDos)) {
                player.sendMessage(Text.of(TextColors.GREEN, "No has aceptado la propuesta de casamiento!"));
                MatrimonioControlador.getMatrim().noAcepto();
            }else {
                player.sendMessage(Text.of(TextColors.RED, "No tienes nada q rechazar"));
            }
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }

        return CommandResult.success();
    }
}
