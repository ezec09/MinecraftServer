package entidades;

import controladores.GymControlador;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.LiteralText;
import org.spongepowered.api.text.Text;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Jugador {
    @Id
    private String nombreLC;
    private String uuid;
    private String nombre;
    private String password;
    @OneToOne(fetch = FetchType.EAGER)
    private Matrimonio matrim;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Gimnasio> gimnasiosGanados = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<LugarDesbloqueable> lugaresDesbloqueados = new ArrayList<>();
    @Transient
    private Text prefijo = null;

    public Jugador(String nombre, String uuid) {
        this.nombre = nombre;
        this.uuid = uuid;
        this.nombreLC = nombre.toLowerCase();
        this.actualizarPrefijo();
    }

    public Jugador(Player player) {
        this.uuid = player.getUniqueId().toString();
        this.nombre = player.getName();
        this.nombreLC = nombre.toLowerCase();
        this.actualizarPrefijo();
    }

    public void setMatrim(Matrimonio matrim) {
        this.matrim = matrim;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Player getPlayer() {
        return Sponge.getServer().getPlayer(nombre).orElse(null);
    }

    public String getNombre() {
        return nombre;
    }

    public Text getPrefijo() {
        return prefijo;
    }

    public List<Gimnasio> getGimnasiosGanados() {
        return gimnasiosGanados;
    }

    public List<LugarDesbloqueable> getLugaresDesbloqueados() {
        return lugaresDesbloqueados;
    }

    public void setLugaresDesbloqueados(List<LugarDesbloqueable> lugaresDesbloqueados) {
        this.lugaresDesbloqueados = lugaresDesbloqueados;
    }

    public boolean ganoGym(Gimnasio gym) {
        return gimnasiosGanados.contains(gym);
    }

    public void ganasteGym(Gimnasio gym) {
        gimnasiosGanados.add(gym);
        this.actualizarPrefijo();
    }

    public void seCerroUnGym(Gimnasio gym) {
        if(this.ganoGym(gym)) {
            this.gimnasiosGanados.remove(gym);
        }
        this.actualizarPrefijo();
    }

    public void actualizarPrefijo() {
        Text matrimonioPrefijo = Text.of("");
        if(matrim!=null) {
            matrimonioPrefijo = matrim.getPrefijo();
        }
        this.prefijo = GymControlador.getPrefixForChat(this.gimnasiosGanados).concat(matrimonioPrefijo);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Jugador && ((Jugador) obj).getNombre().equalsIgnoreCase(this.getNombre());
    }

    public Boolean meTenesDesbloqueado(LugarDesbloqueable lugarDesbloqueable) {
        return this.getLugaresDesbloqueados().contains(lugarDesbloqueable);
    }
    public Boolean meTenesDesbloqueadoNCS(String nombre) {
        return this.getLugaresDesbloqueados().stream().anyMatch(elem->elem.getNombre().equalsIgnoreCase(nombre));
    }


    public void desbloqueasteUnLugar(LugarDesbloqueable lugar) {
        if(this.lugaresDesbloqueados == null)
            this.lugaresDesbloqueados = new ArrayList<>();
        this.lugaresDesbloqueados.add(lugar);
    }

    public void seBorrorUnLD(LugarDesbloqueable lugar) {
        this.lugaresDesbloqueados.remove(lugar);
    }

    public List<LugarDesbloqueable> getLugarDesbloqueados() {
        return this.lugaresDesbloqueados;
    }

    public Boolean estaCasado() {
        return this.matrim != null;
    }

    public Matrimonio getMatrimonio() {
        return this.matrim;
    }
}
