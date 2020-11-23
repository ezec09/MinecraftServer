package comandos.commandselement;

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
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class MedallasCommandElement extends CommandElement {
    
    public MedallasCommandElement(@Nullable Text key) {
        super(key);
    }

    @Nullable
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String argumento = args.next().toLowerCase();
        if (argumento.length() > 0) {
            return argumento.toLowerCase();
        } else {
            throw args.createError(Text.of(TextColors.RED, "Desbes completar el nombre de la medalla."));
        }
    }

    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        Collection<ItemType> medallas = Sponge.getRegistry().getAllOf(ItemType.class);
        List<String> medallasNombre = medallas.stream().map((elem) ->
             elem.getName().toLowerCase()
        ).filter((elem) ->
            elem.contains("pixelmon") && elem.contains("_badge")
        ).collect(Collectors.toList());

        try {
            String argumento = args.peek().toLowerCase();
            return medallasNombre.stream().filter((elem) ->
                elem.contains(argumento)
            ).collect(Collectors.toList());
        } catch (ArgumentParseException var7) {
            return medallasNombre;
        }
    }
}
