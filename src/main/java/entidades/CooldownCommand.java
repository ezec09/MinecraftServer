package entidades;

import java.util.ArrayList;
import java.util.Optional;

public class CooldownCommand {

    private String comando;
    private Integer cooldown;
    private ArrayList<CooldownPlayer> playersEjecutar = new ArrayList<>();

    public CooldownCommand(String comando, Integer cooldown) {
        this.comando = comando;
        this.cooldown = cooldown;
    }

    public String getComando() {
        return comando;
    }

    public Integer getCooldown() {
        return cooldown;
    }

    public CooldownPlayer getPlayerCooldown(String nombreJugador) {
        Optional<CooldownPlayer> cp = playersEjecutar.stream().filter(e->e.getNombre().equalsIgnoreCase(nombreJugador.toLowerCase())).findFirst();
        if(cp.isPresent()) {
            return cp.get();
        }else {
            CooldownPlayer cdPlayer =  new CooldownPlayer(nombreJugador.toLowerCase());
            this.playersEjecutar.add(cdPlayer);
            return cdPlayer;
        }
    }
}
