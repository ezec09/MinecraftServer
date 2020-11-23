package controladores;

import org.spongepowered.api.boss.BossBarColors;
import org.spongepowered.api.boss.BossBarOverlays;
import org.spongepowered.api.boss.ServerBossBar;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BossBarControlador {

    public static ServerBossBar getSVBForLogin() {
        ServerBossBar sbb = ServerBossBar.builder()
                .color(BossBarColors.RED)
                .darkenSky(false)
                .createFog(false)
                .playEndBossMusic(false)
                .percent(1.0f)
                .name(Text.of(TextColors.RED,"Tiempo de logueo. Usa /login o /registrar!"))
                .visible(true)
                .overlay(BossBarOverlays.PROGRESS)
                .build();

        return sbb;
    }

    public static ServerBossBar getSVBForEvento() {
        ServerBossBar sbb = ServerBossBar.builder()
                .color(BossBarColors.RED)
                .darkenSky(false)
                .createFog(false)
                .playEndBossMusic(false)
                .percent(1.0f)
                .name(Text.of(TextColors.AQUA,"Evento por empezar!!. Usa /participar!!"))
                .visible(true)
                .overlay(BossBarOverlays.PROGRESS)
                .build();

        return sbb;
    }

    public static ServerBossBar getSVBForCasamiento(String jugadorUno, String jugadorDos) {
        ServerBossBar sbb = ServerBossBar.builder()
                .color(BossBarColors.WHITE)
                .darkenSky(false)
                .createFog(false)
                .playEndBossMusic(false)
                .percent(1.0f)
                .name(Text.of(TextColors.AQUA,jugadorUno + " y " + jugadorDos + " estan por casarse!"))
                .visible(true)
                .overlay(BossBarOverlays.PROGRESS)
                .build();

        return sbb;
    }

}
