package controladores;

import entidades.CooldownCommand;

import java.util.ArrayList;

public class CooldownControlador {

    private static ArrayList<CooldownCommand> cooldown;

    public static void inicializar() {
        cooldown = new ArrayList<>();
        cooldown.add(new CooldownCommand("proponer",180));
    }

    public static boolean comandoTieneCd(String comando) {
        return cooldown.stream().anyMatch(elem -> elem.getComando().contains(comando.toLowerCase()));
    }

    public static CooldownCommand getCommandCd(String comando) {
        return cooldown.stream().filter(elem -> elem.getComando().contains(comando.toLowerCase())).findFirst().get();
    }


}
