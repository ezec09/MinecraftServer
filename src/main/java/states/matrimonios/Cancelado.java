package states.matrimonios;

import entidades.Matrimonio;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Cancelado extends EstadoMatrimonio {

    @Override
    public void siguienteEstado(Matrimonio matrimonio) {
        //NO SE HACE NADA MAS

    }

    public void aplicar(Matrimonio matrimonio) {
        matrimonio.getParejaUno().getPlayer().sendMessage(Text.of(TextColors.RED,"No acepto tu propuesta :("));
        matrimonio.getParejaDos().getPlayer().sendMessage(Text.of(TextColors.RED,"No aceptaste la propuesta"));
    }

    @Override
    public Boolean enProceso() {
        return false;
    }
}
