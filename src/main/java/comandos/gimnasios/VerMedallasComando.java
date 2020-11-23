package comandos.gimnasios;

import comandos.commandselement.GimnasioCommandElement;
import controladores.BCC;
import controladores.SesionControlador;
import entidades.Gimnasio;
import entidades.Jugador;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.List;

public class VerMedallasComando implements CommandExecutor {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        builderCS.permission("adicionalesbyuc.comandos.user.gym.vermedallas");
        builderCS.executor(new VerMedallasComando());
        return builderCS.build();
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;
            Jugador ganador = SesionControlador.getJugador(player.getName());
            this.mostrarInventario(ganador);
        }
        else {
            src.sendMessage(Text.of("Solo un jugador puede usar esto."));
        }
        return CommandResult.success();
    }



    private void mostrarInventario(Jugador source) {
        List<Gimnasio> gimnasios = source.getGimnasiosGanados();
        Inventory inventorio = Inventory.builder().property("inventorytitle", new InventoryTitle(Text.of("VERMEDALLAS" + source.getNombre().toUpperCase()))).build(BCC.getPluginContainer());
        gimnasios.stream().forEach((elem) -> {
            ItemStack medallaStack = ItemStack.builder().itemType(Sponge.getRegistry().getType(ItemType.class, elem.getNombreMedalla()).get()).build();
            medallaStack.offer(Keys.DISPLAY_NAME, Text.of("Medalla ").concat(elem.getNombreColorido()));
            inventorio.offer(medallaStack);
        });
        source.getPlayer().openInventory(inventorio, Text.of(TextColors.DARK_PURPLE, TextStyles.UNDERLINE, "Medallas de: " + source.getNombre().toUpperCase()));
    }
}
