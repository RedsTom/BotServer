package eu.redstom.example;

import eu.redstom.botapi.BotPlugin;
import eu.redstom.botapi.i18n.CommonLanguages;
import eu.redstom.botapi.injector.Inject;
import eu.redstom.botapi.plugins.IPlugin;
import eu.redstom.botapi.server.IServer;
import eu.redstom.example.i18n.I18nKeys;

@BotPlugin(author = "RedsTom", id = "Example", name = "ExampleAddon")
public class Main {

    public void load(@Inject("server") IServer server, @Inject("plugin") IPlugin plugin) {
        server.getLogger().info("Plugin " + plugin.getName() + " loaded !");

        System.out.println(plugin.getTranslationManager().get(CommonLanguages.FR_FR, I18nKeys.HELLO));
    }

    public void unload(@Inject("server") IServer server, @Inject("plugin") IPlugin plugin) {
        server.getLogger().info("Plugin " + plugin.getName() + " unloaded !");
    }
}
