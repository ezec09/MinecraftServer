package comandos.eventos;

import controladores.EventoControlador;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class EventoEstadoComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Utilizalo para ver el proximo evento"));
        builderCS.permission("adicionalesbyuc.comandos.user.evento.estado");
        builderCS.executor(new EventoEstadoComando());
        return builderCS.build();
    }


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Text eventoInfo = EventoControlador.getEventoTask().getEventoInfo();
        src.sendMessage(eventoInfo);
        return CommandResult.success();
    }
}
