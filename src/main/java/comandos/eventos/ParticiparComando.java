package comandos.eventos;

import controladores.EventoControlador;
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

public class ParticiparComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Utilizalo para participar en el prox evento"));
        builderCS.permission("adicionalesbyuc.comandos.user.evento.participar");
        builderCS.executor(new ParticiparComando());
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player ply = (Player)src;
            EventoTask evOrg = EventoControlador.getEventoTask();
            if (evOrg.hayEventoCorriendo()) {
                ply.setLocation(evOrg.getUbicacionEvento());
            } else {
                ply.sendMessage(Text.of(TextColors.RED, "No hay evento corriendo, utiliza /eventoestado para ver cuanto falta."));
            }
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
