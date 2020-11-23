package controladores;

import entidades.Gimnasio;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class GymControlador {

    private static ConcurrentHashMap<String, Gimnasio> gimnasios ;

    public static void inicializar() {
        gimnasios = new ConcurrentHashMap<>();
        EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
        TypedQuery<Gimnasio> gymsQuery = em.createQuery("SELECT g FROM Gimnasio g",Gimnasio.class);
        List<Gimnasio> gyms = gymsQuery.getResultList();
        gyms.stream().forEach(elem -> gimnasios.put(elem.getNombreGym(),elem));
        em.close();
    }

    public static void nuevoGym(Gimnasio gym) {
        gimnasios.put(gym.getNombreGym(),gym);
    }

    public static Gimnasio removerGym(String nombre) {
        return gimnasios.remove(nombre);
    }

    public static Gimnasio getGym(String nombre) {
        return gimnasios.get(nombre);
    }

    public static Collection<Gimnasio> getElementosCacheados() {
        return gimnasios.values();
    }

    public static List<Gimnasio> gimnasiosOrdenados() {
        List<Gimnasio> gimnasiosOrdenados = new ArrayList<>(gimnasios.values());
        Collections.sort(gimnasiosOrdenados);
        return gimnasiosOrdenados;
    }

    public static Text getPrefixForChat(List<Gimnasio> ganados) {
        Text.Builder buffer = Text.builder().append(Text.of(TextColors.BLACK, TextStyles.BOLD, "["));
        Iterator var3 = gimnasiosOrdenados().iterator();

        while(var3.hasNext()) {
            Gimnasio gym = (Gimnasio)var3.next();
            if (!buffer.build().toPlain().equals("[")) {
                buffer.append(Text.of("|"));
            }

            TextColor color = gym.getColor();
            if (ganados.contains(gym)) {
                buffer.append(Text.of(color, gym.getLetraChat()));
            } else {
                buffer.append(Text.of(gym.getLetraChat()));
            }
        }

        buffer.append(Text.of(TextColors.BLACK, TextStyles.BOLD, "]"));
        return buffer.build();
    }

}
