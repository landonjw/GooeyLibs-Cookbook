package ca.landonjw.gooeylibs.cookbook.commands.pagination;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;

public class PaginationCommand implements Command<CommandSource> {

    @Override
    public int run(CommandContext<CommandSource> context) {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Button button = GooeyButton.builder()
                    .display(new ItemStack(Item.getItemById(100 + i)))
                    .build();
            buttons.add(button);
        }

        GooeyButton filler = GooeyButton.builder()
                .display(new ItemStack(Blocks.BLUE_STAINED_GLASS_PANE))
                .build();

        LinkedPageButton previous = LinkedPageButton.builder()
                .title("Previous")
                .display(new ItemStack(Items.DIAMOND))
                .linkType(LinkType.Previous)
                .build();

        LinkedPageButton next = LinkedPageButton.builder()
                .title("Next")
                .display(new ItemStack(Items.DIAMOND))
                .linkType(LinkType.Next)
                .build();

        PlaceholderButton placeholder = new PlaceholderButton();

        ChestTemplate template = ChestTemplate.builder(6)
                .rectangle(1, 1, 4, 7, placeholder)
                .fill(filler)
                .set(5, 3, previous)
                .set(5, 5, next)
                .build();

        LinkedPage page = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);

        try {
            UIManager.openUIForcefully(context.getSource().asPlayer(), page);
        } catch (CommandSyntaxException ignored) {}
        return 0;
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("pagination")
                        .executes(new ca.landonjw.gooeylibs.cookbook.commands.moveable.MoveableCommand())
                        .requires(src -> src.hasPermissionLevel(0))
        );
    }

}

