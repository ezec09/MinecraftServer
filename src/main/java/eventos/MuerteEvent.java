package eventos;

import com.flowpowered.math.vector.Vector3i;
import controladores.BCC;
import controladores.CPControlador;
import controladores.SesionControlador;
import entidades.CentroPokemon;
import entidades.Jugador;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MuerteEvent {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDeathEvent(LivingDeathEvent evento) {
        if (evento.getEntity() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP)evento.getEntity();
            player.sendMessage(new TextComponentString(TextFormatting.GRAY + "" + TextFormatting.BOLD + "Has muerto, tu y tus pokemons seran atendidos en el centro pokemon mas cercano"));
            Jugador jugador =  SesionControlador.getJugador(player.getName());
            CentroPokemon lugarMasCercano = CPControlador.closestTo(jugador);
            BlockPos spawnPoint = new BlockPos(lugarMasCercano.getUbicacion().getX(), lugarMasCercano.getUbicacion().getY(), lugarMasCercano.getUbicacion().getZ());
            player.setSpawnPoint(spawnPoint,true);
        }

    }

}
