package ca.landonjw.gooeylibs.cookbook.commands.trade;

import ca.landonjw.gooeylibs.api.button.GooeyButton;
import ca.landonjw.gooeylibs.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs.api.page.Page;
import ca.landonjw.gooeylibs.api.page.PageAction;
import ca.landonjw.gooeylibs.api.template.LineType;
import ca.landonjw.gooeylibs.api.template.Template;
import ca.landonjw.gooeylibs.api.template.types.InventoryTemplate;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.Optional;

public class TradePage extends UpdateEmitter<Page> implements Page {

    private TradeController controller;
    private InventoryTemplate inventoryTemplate;
    private EntityPlayerMP viewer;

    private boolean confirmed;

    public TradePage(TradeController controller, EntityPlayerMP viewer) {
        this.controller = controller;
        this.viewer = viewer;

        GooeyButton whiteGlass = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.WHITE.getMetadata()))
                .build();

        this.inventoryTemplate = InventoryTemplate.builder()
                .fill(whiteGlass)
                .line(LineType.HORIZONTAL, 1, 1, 3, null)
                .line(LineType.HORIZONTAL, 1, 5, 3, null)
                .build();
        this.controller.subscribe(this, this::refresh);
        refresh();
    }

    private void refresh() {
        refreshPartyView();

        // Prevents full container updates for all changes. Only when we need to change title.
        if(confirmed != controller.hasConfirmed(viewer)) {
            confirmed = controller.hasConfirmed(viewer);
            update();
        }
    }

    private void refreshPartyView() {
        // Clear the party slots
        for(int i = 0; i < 6; i++) {
            if(i < 3) {
                this.inventoryTemplate.getSlot(10 + i).setButton(null);
            }
            else{
                this.inventoryTemplate.getSlot(14 + i - 3).setButton(null);
            }
        }

        Pokemon[] playerParty = Pixelmon.storageManager.getParty(viewer).getAll();
        for(int i = 0; i < 6; i++) {
            Pokemon pokemon = playerParty[i];
            if(pokemon == null) continue;
            int partyIndex = i;
            GooeyButton sprite = GooeyButton.builder()
                    .display(ItemPixelmonSprite.getPhoto(pokemon))
                    .onClick(() -> controller.add(viewer, partyIndex))
                    .build();
            if(i < 3) {
                this.inventoryTemplate.getSlot(10 + i).setButton(sprite);
            }
            else{
                this.inventoryTemplate.getSlot(14 + i - 3).setButton(sprite);
            }
        }
    }

    @Override
    public Template getTemplate() {
        return controller.getTradeWindow();
    }

    @Override
    public Optional<InventoryTemplate> getInventoryTemplate() {
        return Optional.of(inventoryTemplate);
    }

    @Override
    public String getTitle() {
        if(controller.hasConfirmed(viewer)) {
            return TextFormatting.BLUE + "Trade Window" + TextFormatting.DARK_GREEN + " (Confirmed!)";
        }
        else{
            return TextFormatting.BLUE + "Trade Window";
        }
    }

    @Override
    public void onClose(@Nonnull PageAction action) {
        if(!controller.isTradeCompleted() && !controller.isTradeCancelled()) {
            controller.cancelTrade();
        }
    }

}
