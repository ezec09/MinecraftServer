package comandos.lugardesbloqueable;

import com.flowpowered.math.vector.Vector3i;
import controladores.BCC;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.LugarDesbloqueable;
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
import tasks.lugardesbloqueable.GuardarLDTask;

public class GuardarLDComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.admin.ld.guardar");
        builderCS.executor(new GuardarLDComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.string(Text.of("nombre"))),
                GenericArguments.onlyOne(GenericArguments.integer(Text.of("distancia")))
        );
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String nombreLugar= args.<String>getOne("nombre").get();
            Integer distancia = args.<Integer>getOne("distancia").get();
            Jugador origen = SesionControlador.getJugador(player.getName());
            Vector3i pos = player.getLocation().getBlockPosition();
            String world = player.getLocation().getExtent().getName();
            LugarDesbloqueable ld = new LugarDesbloqueable(nombreLugar,distancia,pos.getX(), pos.getY(), pos.getZ(), world);
            Task.builder().execute(new GuardarLDTask(ld,origen))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }
}
