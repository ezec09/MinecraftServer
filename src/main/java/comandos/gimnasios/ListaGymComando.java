package comandos.gimnasios;

import controladores.GymControlador;
import controladores.SesionControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ListaGymComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.user.gym.list");
        builderCS.executor(new ListaGymComando());
        return builderCS.build();
    }


    public CommandResult execute(CommandSource src, CommandContext args) {
        if (src instanceof Player) {
            Jugador jgd = SesionControlador.getJugador(src.getName());
            List<Gimnasio> gyms = GymControlador.gimnasiosOrdenados();
            Text.Builder bufferMensaje = Text.builder();
            Text separador = Text.of("-----------------------------------------------");
            Text titulo = Text.of("NOMBRE||COLOR||MEDALLA||LETRA||POS.BATALLA");
            bufferMensaje.append(separador).append(Text.NEW_LINE);
            gyms.forEach((elem) -> {
                bufferMensaje.append(Text.of(elem.descripcionGimnasio(jgd))).append(Text.NEW_LINE);
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
