package ca.landonjw.gooeylibs.cookbook.commands.snake;

import ca.landonjw.gooeylibs2.api.UIManager;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class SnakeCommand implements Command<CommandSource> {

    @Override
    public int run(CommandContext<CommandSource> context) {
        SnakePage page = new SnakePage();

        try {
            UIManager.openUIForcefully(context.getSource().asPlayer(), page);
        } catch (CommandSyntaxException ignored) {}
        return 0;
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("snakegame")
                        .executes(new SnakeCommand())
                        .requires(src -> src.hasPermissionLevel(0))
        );
    }

}