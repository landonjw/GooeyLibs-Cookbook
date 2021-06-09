package ca.landonjw.gooeylibs.cookbook.commands.types;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.TemplateType;
import ca.landonjw.gooeylibs2.api.template.types.*;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class CyclePageCommand implements Command<CommandSource> {

    private int ordinal = 0;

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity sender = context.getSource().asPlayer();

        Template template = getTemplate();

        GooeyPage page = GooeyPage.builder()
                .template(template)
                .build();

        UIManager.openUIForcefully(sender, page);
        return 0;
    }

    private Template getTemplate() {

        ArrayList<String> arr = new ArrayList<>();
        for (int i = 0; i < ordinal; i++) {
            arr.add("Foo!");
        }
        arr.add("Bar!");
        ordinal = arr.size();

        Button button = GooeyButton.builder()
                .display(new ItemStack(Items.DIAMOND))
                .build();

        switch (ordinal % TemplateType.values().length) {
            case 0:
                return ChestTemplate.builder(5)
                        .border(0, 0, 1, 1, button)
                        .checker(0, 0, 1, 2, button, button)
                        .column(0, button)
                        .row(0, button)
                        .set(0, button)
                        .set(0, 0, button)
                        .rectangle(0, 0, 1, 1, button)
                        .square(0, 0, 0, button)
                        .fill(button)
                        .build();
            case 1:
                return FurnaceTemplate.builder()
                        .fuel(button)
                        .inputMaterial(button)
                        .outputMaterial(button)
                        .build();
            case 2:
                return BrewingStandTemplate.builder()
                        .bottle(0, button)
                        .bottles(button)
                        .fuel(button)
                        .ingredient(button)
                        .build();
            case 3:
                return HopperTemplate.builder()
                        .set(0, button)
                        .build();
            case 4:
                return DispenserTemplate.builder()
                        .set(0, button)
                        .set(0, 1, button)
                        .fill(button)
                        .build();
            case 5:
                return CraftingTableTemplate.builder()
                        .set(0, button)
                        .setGrid(0, 0, button)
                        .setResultItem(button)
                        .fill(button)
                        .build();
        }
        throw new IllegalStateException("shouldn't have reached here");
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                Commands.literal("cyclepage")
                        .executes(new CyclePageCommand())
                        .requires(src -> src.hasPermissionLevel(0))
        );
    }

}