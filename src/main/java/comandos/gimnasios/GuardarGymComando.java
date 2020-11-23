package comandos.gimnasios;

import com.flowpowered.math.vector.Vector3i;
import comandos.commandselement.MedallasCommandElement;
import comandos.commandselement.TextColorCommandElement;
import controladores.BCC;
import entidades.Gimnasio;
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
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import tasks.gimnasios.GuardarGymTask;

public class GuardarGymComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.admin.gym.crear");
        builderCS.executor(new GuardarGymComando());
        builderCS.arguments(
                GenericArguments.onlyOne(GenericArguments.string(Text.of("nombre"))),
                GenericArguments.onlyOne(new MedallasCommandElement(Text.of("medalla"))),
                GenericArguments.onlyOne(new TextColorCommandElement(Text.of("color"))),
                GenericArguments.onlyOne(GenericArguments.string(Text.of("letra"))),
                GenericArguments.onlyOne(GenericArguments.integer(Text.of("orden")))
        );
        return builderCS.build();
    }


    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            String nombreGym = args.<String>getOne("nombre").get();
            String medallaNombre = args.<String>getOne("medalla").get();
            TextColor color = args.<TextColor>getOne("color").get();
            String letra = args.<String>getOne("letra").get();
            Vector3i posicion = ((Player) src).getLocation().getBlockPosition();
            Integer orden = args.<Integer>getOne("orden").get();

            Gimnasio gym = new Gimnasio(nombreGym,medallaNombre,letra,color.getName().toUpperCase(),orden,posicion);
            Task.builder().execute(new GuardarGymTask(gym, new Jugador((player))))
                    .async()
                    .submit(BCC.getPluginContainer());
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }

}
