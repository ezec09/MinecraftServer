package comandos.eventos;

import controladores.EventoControlador;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.time.LocalDateTime;

public class PosponerComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Utilizalo para posponer proximo evento"));
        builderCS.permission("adicionalesbyuc.comandos.admin.evento.posponer");
        builderCS.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("minutosPosponer"))));
        builderCS.executor(new PosponerComando());
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        LocalDateTime nextEvento = EventoControlador.getEventoTask().getProximoEvento();
        Integer minutosAPosponer = args.<Integer>getOne("minutosPosponer").get();
        EventoControlador.getEventoTask().cambiarTiempoDeEvento(nextEvento.plusMinutes(minutosAPosponer.longValue()));
        Sponge.getServer().getBroadcastChannel().send(Text.of(TextColors.YELLOW, "Se cambio el horario del proximo spawn de bosses. /eventoestado para mas info"));
        return CommandResult.success();
    }
}
