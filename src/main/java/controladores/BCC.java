package controladores;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;

public class BCC {
    //Basic Config Controler
    private static String pluginId = "adicionalesbyuc";
    public static Logger logger;

    public static PluginContainer getPluginContainer() {
        return Sponge.getPluginManager().getPlugin(pluginId).get();
    }
}
