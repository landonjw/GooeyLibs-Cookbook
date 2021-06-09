package ca.landonjw.gooeylibs.cookbook;

import ca.landonjw.gooeylibs.cookbook.commands.animation.AnimatedCommand;
import ca.landonjw.gooeylibs.cookbook.commands.configurable.ConfigurableCommand;
import ca.landonjw.gooeylibs.cookbook.commands.inventory.InventoryTestPage;
import ca.landonjw.gooeylibs.cookbook.commands.moveable.MoveableCommand;
import ca.landonjw.gooeylibs.cookbook.commands.pagination.PaginationCommand;
import ca.landonjw.gooeylibs.cookbook.commands.ratelimit.RateLimitPageCommand;
import ca.landonjw.gooeylibs.cookbook.commands.shooter.ShooterCommand;
import ca.landonjw.gooeylibs.cookbook.commands.snake.SnakeCommand;
import ca.landonjw.gooeylibs.cookbook.commands.synchronization.SynchronizedCommand;
import ca.landonjw.gooeylibs.cookbook.commands.types.CyclePageCommand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Cookbook.MOD_ID)
public class Cookbook {

    public static final String MOD_ID = "cookbook";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final Gson PRETTY_GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public Cookbook() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCommandRegistration(RegisterCommandsEvent event) {
        AnimatedCommand.register(event.getDispatcher());
        ConfigurableCommand.register(event.getDispatcher());
        InventoryTestPage.register(event.getDispatcher());
        MoveableCommand.register(event.getDispatcher());
        PaginationCommand.register(event.getDispatcher());
        RateLimitPageCommand.register(event.getDispatcher());
        ShooterCommand.register(event.getDispatcher());
        SnakeCommand.register(event.getDispatcher());
        SynchronizedCommand.register(event.getDispatcher());
        CyclePageCommand.register(event.getDispatcher());
    }

}
