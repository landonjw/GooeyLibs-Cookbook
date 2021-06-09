package ca.landonjw.gooeylibs.cookbook.commands.synchronization;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs2.api.page.Page;
import ca.landonjw.gooeylibs2.api.template.Template;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

/**
 * This page demonstrates an interface that is able to be synchronized between many players.
 * This updating is done upon a button being set to the template in #setActive.
 */
public class SynchronizedPage extends UpdateEmitter<Page> implements Page {

    private final ChestTemplate template;
    private String title;

    private final GooeyButton setInactiveButton = GooeyButton.builder()
            .display(new ItemStack(Blocks.RED_WOOL))
            .title("Set Inactive")
            .onClick(() -> setActive(false))
            .build();

    private final GooeyButton setActiveButton = GooeyButton.builder()
            .display(new ItemStack(Blocks.GREEN_WOOL))
            .title("Set Active")
            .onClick(() -> setActive(true))
            .build();

    public SynchronizedPage() {
        GooeyButton filler = GooeyButton.builder()
                .display(new ItemStack(Blocks.GRAY_STAINED_GLASS_PANE))
                .build();

        this.template = ChestTemplate.builder(6)
                .fill(filler)
                .build();
        setActive(false);
    }

    private void setActive(boolean state) {
        // Toggles the button that is used to set the page active/inactive
        template.getSlot(3, 4).setButton((state) ? setInactiveButton : setActiveButton);
        this.title = state ? TextFormatting.GREEN + "Active Page!" : TextFormatting.RED + "Inactive Page!";
        // Refreshes the container for all viewers
        update();
    }

    @Override
    public Template getTemplate() {
        return template;
    }

    @Override
    public String getTitle() {
        return title;
    }

}