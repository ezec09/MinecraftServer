package tasks.jugador;

import Database.JugadoresDb;
import com.google.inject.Inject;
import controladores.BCC;
import controladores.SesionControlador;
import entidades.Jugador;
import org.slf4j.Logger;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import tasks.JugadorTask;

public class PreLoginTask extends JugadorTask {

    private Integer segundosAviso =  50;
    private Integer segundosTotales =  50;
    private Text mensaje = null;
    private Integer deltaMovimiento;
    private ServerBossBar contadorBar;

    public PreLoginTask(Jugador jugador, Integer deltaMovimiento) {
        super(jugador);
        this.deltaMovimiento = deltaMovimiento;
    }

    public void setContadorBar(ServerBossBar contadorBar) {
        this.contadorBar = contadorBar;
    }

    @Override
    public void accept(Task task) {
        inicializarMensaje();
        inicializarBarraContador();
        if (hayTiempo() && !SesionControlador.jugadorEstaLogueado(jugador.getNombre())) {
            try {
                jugador.getPlayer().sendMessage(mensaje);
            } catch (Exception ex) {
                //nada, para q no imprima en la consola
            }
        }else if(hayTiempo() && SesionControlador.jugadorEstaLogueado(jugador.getNombre())){
            contadorBar.setVisible(false);
            contadorBar = null;
            task.cancel();
        }else if(!hayTiempo()) {
            //kick
            try {
                contadorBar.setVisible(false);
                contadorBar = null;
                jugador.getPlayer().kick(Text.of("No te registraste/logueaste."));
            } catch (Exception ex) {
                //nada, para q no imprima en la consola
            }
            task.cancel();
        }

        segundosAviso = segundosAviso - deltaMovimiento;
        if(contadorBar != null) {
            contadorBar.setPercent(Math.max(contadorBar.getPercent() - (1.0f / segundosTotales * deltaMovimiento),0.0f));
        }
    }

    private boolean hayTiempo() {
        return segundosAviso > 0;
    }

    private void inicializarMensaje() {
        if(mensaje == null &&   this.jugadorRegistrado()) {
            this.mensaje = Text.of(TextColors.AQUA,"Usa: /login password");
        } else if (mensaje == null) {
            this.mensaje = Text.of(TextColors.AQUA,"Usa: /registrar password password");
        }
    }

    private void inicializarBarraContador() {
        if(contadorBar != null && jugador.getPlayer() != null && contadorBar.getPlayers().size() == 0) {
            contadorBar.addPlayer(jugador.getPlayer());
        }
    }

    private boolean jugadorRegistrado() {
        JugadoresDb database = new JugadoresDb();
        boolean existe = database.existeJugador(jugador.getNombre());
        database.cerrarEntityManager();
        return existe;
    }
}
