package comandos.commandselement;

import controladores.GymControlador;
import entidades.Gimnasio;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public class GimnasioCommandElement extends CommandElement {
    public GimnasioCommandElement(@Nullable Text key) {
        super(key);
    }

    @Nullable
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String nombreGym = args.next();
        Gimnasio gimnasio = GymControlador.getGym(nombreGym);
        if (gimnasio == null) {
            throw args.createError(Text.of(TextColors.RED, "Gym no encontrado"));
        } else {
            return gimnasio;
        }
    }

    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        try {
            String argumento = args.peek();
            List<String> opciones =  GymControlador.getElementosCacheados().stream().map((gym) -> {
                return gym.getNombreGym();
            }).filter((nombreGym) -> {
                return nombreGym.contains(argumento);
            }).collect(Collectors.toList());
            return opciones;
        } catch (ArgumentParseException var6) {
            return GymControlador.getElementosCacheados().stream().map((elem) -> {
                return elem.getNombreGym();
            }).collect(Collectors.toList());
        }
    }
}