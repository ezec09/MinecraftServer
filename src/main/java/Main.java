import com.google.inject.Inject;
import com.pixelmonmod.pixelmon.Pixelmon;
import comandos.centropokemon.CPComando;
import comandos.eventos.EventoEstadoComando;
import comandos.eventos.ParticiparComando;
import comandos.eventos.PosponerComando;
import comandos.gimnasios.*;
import comandos.lugardesbloqueable.BorrarLDComando;
import comandos.lugardesbloqueable.GuardarLDComando;
import comandos.lugardesbloqueable.LDComando;
import comandos.lugardesbloqueable.ListarLDComando;
import comandos.matrimonio.*;
import comandos.sesiones.CambiarPasswordComando;
import comandos.sesiones.GetPasswordComando;
import comandos.sesiones.LoginComando;
import comandos.sesiones.RegistrarComando;
import controladores.*;
import entidades.LugarDesbloqueable;
import eventos.*;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import tasks.evento.EventoTask;
import tasks.lugardesbloqueable.RevisarPosTask;
import tasks.otros.AlimentarTask;

import java.util.concurrent.TimeUnit;

@Plugin(id = "adicionalesbyuc", name = "Adicionales", version = "1.0", description = "Plugin que agrega cosas a pedido")
public class Main {

    @Inject
    private Logger logger;

    @Listener
    public void onGameInitEvent(GameInitializationEvent event) {
        BaseDeDatosControlador.inicializar();
        SesionControlador.initSesionControlador();
        GymControlador.inicializar();
        LDControlador.inicializar();
        CPControlador.inicializar();
        CooldownControlador.inicializar();
        EventoControlador.crearEvenetoTask();
        logger.info("Adicionales: Controladores cargados.");
        CommandManager cmdManager = Sponge.getCommandManager();
        cmdManager.register(this, LoginComando.getComandSpec(),"login");
        cmdManager.register(this, CambiarPasswordComando.getComandSpec(),"cambiarpassword");
        cmdManager.register(this, RegistrarComando.getComandSpec(),"registrar","register");
        cmdManager.register(this, GetPasswordComando.getComandSpec(), "verpassword");
        cmdManager.register(this, GymComando.getComandSpec(), "gyms");
        cmdManager.register(this, VerMedallasComando.getComandSpec(), "vermedallas");
        cmdManager.register(this, LDComando.getComandSpec(), "ld", "lugardesbloqueados");
        cmdManager.register(this, CPComando.getComandSpec(), "centropokemon","cp");
        cmdManager.register(this, ParticiparComando.getComandSpec(), "participar");
        cmdManager.register(this, PosponerComando.getComandSpec(), "posponer");
        cmdManager.register(this, EventoEstadoComando.getComandSpec(), "eventoestado");
        cmdManager.register(this, ProponerComando.getComandSpec(), "proponer");
        cmdManager.register(this, AceptoComando.getComandSpec(), "acepto");
        cmdManager.register(this, NoAceptoComando.getComandSpec(), "noacepto");
        cmdManager.register(this, DivorsiarComando.getComandSpec(), "divorciar");
        cmdManager.register(this, IrCasamientoComando.getComandSpec(), "iracasamiento");
        logger.info("Adicionales: Comandos registrados.");
        Sponge.getEventManager().registerListeners(this, new ConexionEvent());
        Sponge.getEventManager().registerListeners(this, new OtrosEvent());
        Sponge.getEventManager().registerListeners(this, new DisconnectEvent());
        Sponge.getEventManager().registerListeners(this, new AuthEvent());
        Sponge.getEventManager().registerListeners(this, new ComandoEvent());
        Sponge.getEventManager().registerListeners(this, new ChatEvent());
        Task.builder().async().execute(new AlimentarTask()).interval(30L, TimeUnit.SECONDS).submit(this);
        Pixelmon.EVENT_BUS.register(new PixelmonEvent());
        MinecraftForge.EVENT_BUS.register(new MuerteEvent());
        logger.info("Adicionales: Escuchando eventos.");
        BCC.logger = logger;
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        RevisarPosTask.lanzarCheck();
        EventoControlador.iniciarTask();
        logger.info("Adicionales: Tareas iniciadas.");
    }
}


