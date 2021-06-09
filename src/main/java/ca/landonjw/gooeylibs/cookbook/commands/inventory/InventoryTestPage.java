package ca.landonjw.gooeylibs.cookbook.commands.inventory;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.helpers.InventoryHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class InventoryTestPage implements Command<CommandSource> {

    @Override
    public int run(CommandContext<CommandSource> context) {
        Button setButton = GooeyButton.builder()
                .display(new ItemStack(Items.DIAMOND))
                .title("Set diamond in first inventory slot")
                .onClick((action) -> {
                    InventoryHelper.setToInventorySlot(action.getPlayer(), 0, new ItemStack(Items.DIAMOND));
                })
                .build();

        Button setButton2 = GooeyButton.builder()
                .display(new ItemStack(Items.PUMPKIN_PIE))
                .title("Set pumpkin pie in first inventory row and third column")
                .onClick((action) -> {
                    InventoryHelper.setToInventorySlot(action.getPlayer(), 0, 2, new ItemStack(Items.PUMPKIN_PIE));
                })
                .build();

        Button addButton = GooeyButton.builder()
                .display(new ItemStack(Items.EMERALD))
                .title("Add emerald to inventory")
                .onClick((action) -> {
                    InventoryHelper.addToInventorySlot(action.getPlayer(), new ItemStack(Items.EMERALD));
                })
                .build();

        Button getButton = GooeyButton.builder()
                .display(new ItemStack(Items.APPLE))
                .title("Print out first slot in inventory")
                .onClick((action) -> {
                    System.out.println(InventoryHelper.getStackAtSlot(action.getPlayer(), 0));
                })
                .build();

        Template template = ChestTemplate.builder(3)
                .set(0, setButton)
                .set(1, setButton2)
                .set(2, addButton)
                .set(3, getButton)
                .build();

        Page page = GooeyPage.builder()
                .template(template)
                .build();

        try {
            UIManager.openUIForcefully(context.getSource().asPlayer(), page);
        } catch (CommandSyntaxException ignored) {}
        return 0;
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("inventorypage")
                        .executes(new InventoryTestPage())
                        .requires(src -> src.hasPermissionLevel(0))
        );
    }

}
