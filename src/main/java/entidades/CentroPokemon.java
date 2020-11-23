package entidades;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.Extent;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CentroPokemon  {

    @Id
    private String nombre;
    private Integer x,y,z;
    private String nombreMundo;

    public CentroPokemon(String nombre, Vector3i ubicacion, String nombreMundo) {
        this.nombre = nombre;
        this.x = ubicacion.getX(); this.y = ubicacion.getY(); this.z = ubicacion.getZ();
        this.nombreMundo = nombreMundo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Nombre: " + this.nombre + " PosX: " + this.x + " PosY: " + this.y + " PosZ: " + this.z;
    }

    public Location<World> getLocation() {
        World world = Sponge.getServer().getWorld(nombreMundo).get();
        Location<World> pos = new Location<>(world,x,y,z);
        return pos;
    }
    public Integer distanciaASinRaiz(Vector3i posicion) {
        return posicion.distanceSquared(x,y,z);
    }

    public Vector3i getUbicacion() {
        return new Vector3i(x,y,z);
    }
}
