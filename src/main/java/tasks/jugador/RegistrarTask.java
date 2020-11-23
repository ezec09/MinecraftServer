package tasks.jugador;

import controladores.BaseDeDatosControlador;
import controladores.SesionControlador;
import entidades.Jugador;
import entidades.Sesion;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.JugadorTask;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

public class RegistrarTask extends JugadorTask {

    private CommandContext argumentos;

    public RegistrarTask(Jugador jugador, CommandContext argumentos) {
        super(jugador);
        this.argumentos = argumentos;
    }

    @Override
    public void accept(Task task) {
        Player player = jugador.getPlayer();
        player.sendMessage(Text.of("Registrando..."));
        String password = argumentos.<String>getOne("password").get();
        String passwordRepeat = argumentos.<String>getOne("passwordRepeat").get();

        if(password.equals(passwordRepeat)) {
            jugador.setPassword(password);
            EntityManager em = BaseDeDatosControlador.obtenerEntityManager();
            try {
                Sesion sesion = SesionControlador.nuevaSesion(jugador);
                em.getTransaction().begin();
                em.persist(jugador);
                em.persist(sesion);
                em.getTransaction().commit();
            }catch (Exception ex) {
                player.sendMessage(Text.of(TextColors.RED,"Usuario ya registrado."));
                em.close();
                return;
            }
            player.sendMessage(Text.of(TextColors.GREEN,"Registrado correctamente."));
        }else if(!password.equals(passwordRepeat)) {
            player.sendMessage(Text.of(TextColors.RED,"Contracenias no coinciden."));
        }else {
            player.sendMessage(Text.of(TextColors.DARK_RED,"Ups, no sabemos que paso."));
        }
    }
}
