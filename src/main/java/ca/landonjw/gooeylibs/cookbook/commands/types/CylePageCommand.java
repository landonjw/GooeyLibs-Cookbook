package ca.landonjw.gooeylibs.cookbook.commands.types;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.TemplateType;
import ca.landonjw.gooeylibs2.api.template.types.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CylePageCommand extends CommandBase {

    private int ordinal = 0;

    @Override
    public String getName() {
        return "cyclepage";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/cyclepage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Template template = getTemplate();

        GooeyPage page = GooeyPage.builder()
                .template(template)
                .build();

        UIManager.openUIForcefully((EntityPlayerMP) sender, page);
    }

    private Template getTemplate() {
        ordinal = ordinal + 1;
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

}
