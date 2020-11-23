package entidades;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LugarDesbloqueable {

    @Id
    private String nombre;
    private Integer distanciaParaDesbloquear;
    private Integer x,y,z;
    private String mundo;

    public LugarDesbloqueable(String nombre, Integer distanciaParaDesbloquear, Integer x, Integer y, Integer z, String mundo) {
        this.nombre = nombre;
        this.distanciaParaDesbloquear = distanciaParaDesbloquear;
        this.x = x;
        this.y = y;
        this.z = z;
        this.mundo = mundo;
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean tePuedoDesbloquear(Jugador jugador) {
        return  jugador.getPlayer() != null &&
                !jugador.meTenesDesbloqueado(this) &&
                jugador.getPlayer().getLocation().getExtent().getName().equalsIgnoreCase(this.mundo) &&
                jugador.getPlayer().getLocation().getPosition().distanceSquared((float)this.x, (float)this.y, (float)this.z) <= Math.pow((double)this.distanciaParaDesbloquear, 2.0D);
    }

    public String toStringDetail() {
        return "Nombre: " + this.nombre + " PosX: " + this.x + " PosY: " + this.y + " PosZ: " + this.z + " DtU: " + this.distanciaParaDesbloquear;
    }

    public Vector3i getUbicacion() {
        return new Vector3i(x,y,z);
    }

    public Location<World> getLocation() {
        World world = Sponge.getServer().getWorld(mundo).get();
        Location<World> pos = new Location<>(world,x,y,z);
        return pos;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LugarDesbloqueable && ((LugarDesbloqueable) obj).getNombre().equalsIgnoreCase(this.getNombre());
    }
}
