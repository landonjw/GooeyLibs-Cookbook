package ca.landonjw.gooeylibs.cookbook.commands.pagination;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.List;

public class PaginationCommand extends CommandBase {

    @Override
    public String getName() {
        return "pageinv";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/pageinv";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        List<Button> buttons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Button button = GooeyButton.builder()
                    .display(new ItemStack(Item.getItemById(100 + i)))
                    .build();
            buttons.add(button);
        }

        GooeyButton filler = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.BLUE.getMetadata()))
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

        LinkedPage firstPage = PaginationHelper.createPagesFromPlaceholders(template, buttons, null);
    }

}
