package comandos.commandselement;

import controladores.LDControlador;
import entidades.LugarDesbloqueable;
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

public class LugarDesbloqueableCommandElement extends CommandElement {
    public LugarDesbloqueableCommandElement(@Nullable Text key) {
        super(key);
    }

    @Nullable
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String nombreLugar = args.next();
        LugarDesbloqueable lugar = LDControlador.getLugaresDesbloqueables(nombreLugar);
        if (lugar == null) {
            throw args.createError(Text.of(TextColors.RED, "No se encontro el lugar."));
        } else {
            return lugar;
        }
    }

    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        try {
            String argumento = args.peek();
            List<String> opciones = (List)LDControlador.getLugaresDesbloqueables().values().stream().map((lugar) -> {
                return lugar.getNombre();
            }).filter((nombreLugar) -> {
                return nombreLugar.contains(argumento);
            }).collect(Collectors.toList());
            return opciones;
        } catch (ArgumentParseException var6) {
            return (List)LDControlador.getLugaresDesbloqueables().values().stream().map((elem) -> {
                return elem.getNombre();
            }).collect(Collectors.toList());
        }
    }
}
