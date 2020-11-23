package entidades;

import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyle;
import org.spongepowered.api.text.format.TextStyles;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Gimnasio implements Comparable<Gimnasio> {

    @Id
    private String nombreGym;
    private String nombreMedalla;
    private String letraChat;
    private String color;
    private Integer x,y,z;
    private Integer orden;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Jugador> lideres = new ArrayList<>();

    public Gimnasio(String nombreGym, String nombreMedalla, String letraChat, String color, Integer orden,Vector3i posicion) {
        this.nombreGym = nombreGym;
        this.nombreMedalla = nombreMedalla;
        this.letraChat = letraChat;
        this.color = color;
        this.x = posicion.getX(); this.y = posicion.getY(); this.z = posicion.getZ();
        this.orden = orden;
    }

    public String getNombreGym() {
        return nombreGym;
    }

    public String getNombreMedalla() {
        return nombreMedalla;
    }

    public String getLetraChat() {
        return letraChat;
    }

    public Integer getOrden() {
        return orden;
    }

    public void addLider(Jugador jugador) {
        this.lideres.add(jugador);
    }

    public void sacarLider(Jugador jugador) {
        this.lideres.remove(jugador);
    }

    public boolean tieneLider(Jugador jugador) {
        return this.lideres.stream().anyMatch(jug -> jug.getNombre().equalsIgnoreCase(jugador.getNombre()));
    }

    public Text getNombreColorido() {
        TextColor color = this.getColor();
        return Text.of(color, this.nombreGym.toUpperCase());
    }

    public Boolean hayLideresConectados() {
        return Sponge.getServer().getOnlinePlayers().stream().anyMatch(player->lideres.contains(new Jugador(player)));
    }

    public TextColor getColor() {
        return Sponge.getRegistry().getType(TextColor.class, this.color).get();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Gimnasio && ((Gimnasio) obj).getNombreGym().equalsIgnoreCase(this.getNombreGym());
    }

    @Override
    public int compareTo(Gimnasio o) {
        if(o.getOrden() == this.orden)
            return 0;
        else if(o.getOrden() > this.orden)
            return -1;
        else
            return 1;
    }

    public Text descripcionGimnasio(Jugador jugador) {
        TextColor color = this.getColor();
        TextStyle estilo = jugador.ganoGym(this) ? TextStyles.STRIKETHROUGH : TextStyles.NONE;
        TextColor colorInfoLider = !this.hayLideresConectados() ? TextColors.GRAY : TextColors.WHITE;
        String textoInfoLideres = !this.hayLideresConectados() ? " No hay lideres conectados." : " Hay lideres conectados.";
        return Text.of(color, estilo, this.nombreGym.toUpperCase(), colorInfoLider, TextStyles.RESET,Text.of(textoInfoLideres));
    }
}
