package comandos.lugardesbloqueable;

import comandos.gimnasios.BorrarGymComando;
import comandos.gimnasios.GuardarGymComando;
import comandos.gimnasios.ListaGymComando;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class LDComando  {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Comandos para manejar los lugares."));
        builderCS.child(GuardarLDComando.getComandSpec(),"crear","add","c");
        builderCS.child(ListarLDComando.getComandSpec(),"ver","listar","l");
        builderCS.child(BorrarLDComando.getComandSpec(),"borrar","del","b");
        builderCS.child(TeleportLDComando.getComandSpec(),"teleport","tp");
        return builderCS.build();
    }

}
