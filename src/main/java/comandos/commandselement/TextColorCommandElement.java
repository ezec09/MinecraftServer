package comandos.commandselement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public class TextColorCommandElement extends CommandElement {
    public TextColorCommandElement(@Nullable Text key) {
        super(key);
    }

    @Nullable
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String argumento = args.next().toUpperCase();
        if (Sponge.getRegistry().getType(TextColor.class, argumento.toUpperCase()).isPresent()) {
            return Sponge.getRegistry().getType(TextColor.class, argumento.toUpperCase()).get();
        } else {
            throw args.createError(Text.of(new Object[]{TextColors.RED, "No se encontro el color."}));
        }
    }

    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        Collection<TextColor> colores = Sponge.getRegistry().getAllOf(TextColor.class);
        ArrayList<String> coloresAFiltrar = new ArrayList();
        coloresAFiltrar.add("RESET");
        coloresAFiltrar.add("NONE");

        List coloresDisponibles;
        try {
            String argumento = args.peek().toUpperCase();
            coloresDisponibles = (List)colores.stream().map((elem) -> {
                return elem.getName().toUpperCase();
            }).filter((elem) -> {
                return !coloresAFiltrar.contains(elem.toUpperCase());
            }).filter((elem) -> {
                return elem.contains(argumento);
            }).collect(Collectors.toList());
            return coloresDisponibles;
        } catch (ArgumentParseException var8) {
            coloresDisponibles = (List)colores.stream().filter((elem) -> {
                return !coloresAFiltrar.contains(elem.getName().toUpperCase());
            }).map((elem) -> {
                return elem.getName().toUpperCase();
            }).collect(Collectors.toList());
            return coloresDisponibles;
        }
    }
}
