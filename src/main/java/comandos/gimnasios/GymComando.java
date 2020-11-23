package comandos.gimnasios;

import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class GymComando  {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Comandos para manejar los gimnasio."));
        builderCS.child(GuardarGymComando.getComandSpec(),"crear","add","c");
        builderCS.child(ListaGymComando.getComandSpec(),"ver","list", "l");
        builderCS.child(BorrarGymComando.getComandSpec(),"borrar","del","b");
        builderCS.child(DarLiderGymComando.getComandSpec(),"darlider");
        builderCS.child(SacarLiderGymComando.getComandSpec(),"sacarlider");
        builderCS.child(GanoGymComando.getComandSpec(),"gano");
        builderCS.child(VerMedallasComando.getComandSpec(),"medallas");
        return builderCS.build();
    }

}
