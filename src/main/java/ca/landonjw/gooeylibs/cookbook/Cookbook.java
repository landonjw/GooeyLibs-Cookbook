package ca.landonjw.gooeylibs.cookbook;

import ca.landonjw.gooeylibs.cookbook.commands.animation.AnimatedCommand;
import ca.landonjw.gooeylibs.cookbook.commands.ratelimit.RateLimitPageCommand;
import ca.landonjw.gooeylibs.cookbook.commands.snake.SnakeCommand;
import ca.landonjw.gooeylibs.cookbook.commands.synchronization.SynchronizedCommand;
import ca.landonjw.gooeylibs.cookbook.commands.shooter.ShooterCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

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

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ShooterCommand());
        event.registerServerCommand(new RateLimitPageCommand());
        event.registerServerCommand(new SnakeCommand());
        event.registerServerCommand(new SynchronizedCommand());
        event.registerServerCommand(new AnimatedCommand());
    }

}
