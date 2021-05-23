package ca.landonjw.gooeylibs.cookbook;

import ca.landonjw.gooeylibs.cookbook.commands.animation.AnimatedCommand;
import ca.landonjw.gooeylibs.cookbook.commands.configurable.ConfigurableCommand;
import ca.landonjw.gooeylibs.cookbook.commands.inventory.InventoryTestPage;
import ca.landonjw.gooeylibs.cookbook.commands.moveable.MoveableCommand;
import ca.landonjw.gooeylibs.cookbook.commands.ratelimit.RateLimitPageCommand;
import ca.landonjw.gooeylibs.cookbook.commands.snake.SnakeCommand;
import ca.landonjw.gooeylibs.cookbook.commands.synchronization.SynchronizedCommand;
import ca.landonjw.gooeylibs.cookbook.commands.shooter.ShooterCommand;
import ca.landonjw.gooeylibs.cookbook.commands.trade.TradeCommand;
import ca.landonjw.gooeylibs.cookbook.commands.types.CylePageCommand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Cookbook.MOD_ID,
        name = Cookbook.MOD_NAME,
        version = Cookbook.VERSION,
        acceptableRemoteVersions = "*",
        serverSideOnly = true
)
public class Cookbook {

    public static final String MOD_ID = "cookbook";
    public static final String MOD_NAME = "Cookbook";
    public static final String VERSION = "1.0-SNAPSHOT";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final Gson PRETTY_GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ShooterCommand());
        event.registerServerCommand(new RateLimitPageCommand());
        event.registerServerCommand(new SnakeCommand());
        event.registerServerCommand(new SynchronizedCommand());
        event.registerServerCommand(new AnimatedCommand());
        event.registerServerCommand(new TradeCommand());
        event.registerServerCommand(new ConfigurableCommand());
        event.registerServerCommand(new MoveableCommand());
        event.registerServerCommand(new CylePageCommand());
        event.registerServerCommand(new InventoryTestPage());
    }

}
