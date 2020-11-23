package states.matrimonios;

import controladores.BCC;
import entidades.Matrimonio;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class EsperaConsentimiento extends EstadoMatrimonio {

    public EsperaConsentimiento(Matrimonio matrim) {
        String parejaNombreUno = matrim.getParejaUno().getNombre();
        matrim.getParejaDos().getPlayer().sendMessage(Text.of(TextColors.AQUA, parejaNombreUno + " te ha propuesto matrimonio! Usa /acepto o /noacepto"));
    }

    @Override
    public void siguienteEstado(Matrimonio matrimonio) {
        //NO APLICA, depende si acepta o no
    }

    @Override
    public Boolean esperandoConsentimiento() {
        return true;
    }

    @Override
    public void aceptoMatrimonio(Matrimonio matrimonio) {
        Anunciando anunciado = new Anunciando();
        BCC.logger.info("GetMatrim: " +  matrimonio.toString());
        matrimonio.setEstado(anunciado);
        anunciado.aplicar(matrimonio);
    }

    @Override
    public void noAceptoMatrimonio(Matrimonio matrimonio) {
        Cancelado matrimonioCancelado = new Cancelado();
        matrimonioCancelado.aplicar(matrimonio);
        matrimonio.setEstado(matrimonioCancelado);
    }
}
