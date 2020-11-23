package comandos.centropokemon;

import comandos.gimnasios.BorrarGymComando;
import comandos.lugardesbloqueable.GuardarLDComando;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CPComando {

    public static CommandSpec getComandSpec() {
        CommandSpec.Builder builderCS = CommandSpec.builder();
        builderCS.description(Text.of("Usar para cambair la password."));
        //builderCS.permission("adicionalesbyuc.comandos.sesiones.registrar");
        builderCS.child(BorrarCPComando.getComandSpec(),"borrar","b");
        builderCS.child(ListCpComando.getComandSpec(),"list","l");
        builderCS.child(GuardarCPComando.getComandSpec(),"crear","c");
        builderCS.child(TeleportCPComando.getComandSpec(),"teleport","tp");
        return builderCS.build();
    }
}
