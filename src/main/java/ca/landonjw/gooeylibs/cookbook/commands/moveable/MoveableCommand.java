package ca.landonjw.gooeylibs.cookbook.commands.moveable;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.moveable.MovableButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MoveableCommand implements Command<CommandSource> {

    @Override
    public int run(CommandContext<CommandSource> context) {
        MovableButton moveable = MovableButton.builder()
                .display(new ItemStack(Items.DIAMOND))
                .build();

        ChestTemplate template = ChestTemplate.builder(6)
                .row(0, moveable)
                .build();

        GooeyPage page = GooeyPage.builder()
                .template(template)
                .build();

        try {
            UIManager.openUIForcefully(context.getSource().asPlayer(), page);
        } catch (CommandSyntaxException ignored) {}
        return 0;
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("moveable")
                        .executes(new MoveableCommand())
                        .requires(src -> src.hasPermissionLevel(0))
        );
    }

}