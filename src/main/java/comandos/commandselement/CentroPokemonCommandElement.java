package comandos.commandselement;

import controladores.CPControlador;
import entidades.CentroPokemon;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class CentroPokemonCommandElement extends CommandElement {

    public CentroPokemonCommandElement(@Nullable Text key) {
        super(key);
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String nombreCP = args.next();
        CentroPokemon cp = CPControlador.getCp(nombreCP);
        if (cp == null) {
            throw args.createError(Text.of(TextColors.RED, "Gym no encontrado"));
        } else {
            return cp;
        }
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        try {
            String argumento = args.peek();
            List<String> opciones =  CPControlador.getCps().stream().map((gym) -> {
                return gym.getNombre();
            }).filter((nombreGym) -> {
                return nombreGym.contains(argumento);
            }).collect(Collectors.toList());
            return opciones;
        } catch (ArgumentParseException var6) {
            return CPControlador.getCps().stream().map((elem) -> {
                return elem.getNombre();
            }).collect(Collectors.toList());
        }
    }
}
