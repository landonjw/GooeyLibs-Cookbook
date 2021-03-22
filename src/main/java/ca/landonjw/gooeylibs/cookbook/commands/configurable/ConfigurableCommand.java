package ca.landonjw.gooeylibs.cookbook.commands.configurable;

import ca.landonjw.gooeylibs.api.UIManager;
import ca.landonjw.gooeylibs.api.button.GooeyButton;
import ca.landonjw.gooeylibs.api.page.GooeyPage;
import ca.landonjw.gooeylibs.api.template.types.InventoryTemplate;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class ConfigurableCommand extends CommandBase {

    @Override
    public String getName() {
        return "configurable";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "configurable";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        GooeyButton filler = GooeyButton.of(new ItemStack(Items.DIAMOND));

        GooeyButton filler2 = GooeyButton.of(new ItemStack(Items.EMERALD));

        GooeyButton filler3 = GooeyButton.of(new ItemStack(Items.SNOWBALL));

        GooeyButton filler4 = GooeyButton.of(new ItemStack(Items.APPLE));

        ConfigurableTemplate template = ConfigurableTemplate.builder(6)
                .border(0, 0, 6, 9, filler)
                .border(1, 1, 4, 7, filler2)
                .checker(2, 2, 2, 5, filler3, filler4)
                .configPath("config/gooeylibs/config.json")
                .build();

        InventoryTemplate inventoryTemplate = InventoryTemplate.builder()
                .build();

        template.load()
                .thenRun(() -> {
                    template.update();
                    GooeyPage page = GooeyPage.builder()
                            .template(template)
                            .inventory(inventoryTemplate)
                            .build();

                    UIManager.openUIForcefully((EntityPlayerMP) sender, page);
                });
    }

}
