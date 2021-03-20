package ca.landonjw.gooeylibs.cookbook.commands.trade;

import ca.landonjw.gooeylibs.api.UIManager;
import ca.landonjw.gooeylibs.api.button.GooeyButton;
import ca.landonjw.gooeylibs.api.data.UpdateEmitter;
import ca.landonjw.gooeylibs.api.template.LineType;
import ca.landonjw.gooeylibs.api.template.Template;
import ca.landonjw.gooeylibs.api.template.types.ChestTemplate;
import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.items.ItemPixelmonSprite;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class TradeController extends UpdateEmitter<TradeController> {

    private Trader trader1;
    private Trader trader2;

    private boolean tradeCompleted;
    private boolean tradeCancelled;

    private ChestTemplate tradeWindow;

    public TradeController(EntityPlayerMP player1, EntityPlayerMP player2) {
        this.trader1 = new Trader(player1);
        this.trader2 = new Trader(player2);

        GooeyButton whiteGlass = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.WHITE.getMetadata()))
                .build();

        GooeyButton greenGlass = GooeyButton.builder()
                .display(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, EnumDyeColor.GREEN.getMetadata()))
                .build();

        GooeyButton confirm = GooeyButton.builder()
                .display(new ItemStack(Blocks.WOOL, 1, EnumDyeColor.LIME.getMetadata()))
                .title(TextFormatting.GREEN + "Confirm")
                .onClick((action) -> {
                    if(trader1.equals(action.getPlayer())) {
                        trader1.setConfirmed(true);
                        update();
                    }
                    else if(trader2.equals(action.getPlayer())) {
                        trader2.setConfirmed(true);
                        update();
                    }
                    if(trader1.isConfirmed() && trader2.isConfirmed()) {
                        completeTrade();
                    }
                })
                .build();

        this.tradeWindow = ChestTemplate.builder(6)
                .border(0, 0, 6, 9, whiteGlass)
                .line(LineType.VERTICAL, 1, 4, 4, greenGlass)
                .set(5, 4, confirm)
                .build();
    }

    public Template getTradeWindow() {
        return tradeWindow;
    }

    private void refreshTradeView() {
        // Clear the trade window
        for(int row = 1; row < 5; row++) {
            for(int col = 1; col < 4; col++) {
                tradeWindow.getSlot(row, col).setButton(null);
            }
        }

        resetTradePokemon(1);
        resetTradePokemon(2);
        update();
    }

    public void resetTradePokemon(int side) {
        Trader trader = (side == 1) ? trader1 : trader2;
        int colOffset = (side == 1) ? 1 : 5;

        List<Pokemon> tradeStorage = trader.getTradeStorage();
        // Iterate through the trade storage and place buttons for each pokemon in storage
        for(int i = 0; i < tradeStorage.size(); i++) {
            Pokemon pokemon = tradeStorage.get(i);
            int tradeIndex = i;
            GooeyButton sprite = GooeyButton.builder()
                    .display(ItemPixelmonSprite.getPhoto(pokemon))
                    .onClick((action) -> {
                        if (trader.equals(action.getPlayer())) {
                            trader.retrieveFromStorage(tradeIndex);
                            refreshTradeView();
                        }
                    })
                    .build();
            tradeWindow.getSlot(1 + i / 3, colOffset + i % 3).setButton(sprite);
        }
    }

    public void add(EntityPlayerMP player, int partyIndex) {
        if(trader1.equals(player)) {
            trader1.addToStorage(partyIndex);
        }
        else if(trader2.equals(player)) {
            trader2.addToStorage(partyIndex);
        }
        else{
            return;
        }
        clearConfirmations();
        refreshTradeView();
    }

    private void clearConfirmations() {
        trader1.setConfirmed(false);
        trader2.setConfirmed(false);
    }

    public boolean isTradeCompleted() {
        return tradeCompleted;
    }

    public boolean isTradeCancelled() {
        return tradeCancelled;
    }

    public void completeTrade() {
        List<Pokemon> trader1Storage = trader1.getTradeStorage();
        trader1Storage.forEach(pokemon -> {
            Pixelmon.storageManager.getParty(trader2.getPlayer()).add(pokemon);
        });
        List<Pokemon> trader2Storage = trader2.getTradeStorage();
        trader2Storage.forEach(pokemon -> {
            Pixelmon.storageManager.getParty(trader1.getPlayer()).add(pokemon);
        });
        tradeCompleted = true;
        UIManager.closeUI(trader1.getPlayer());
        UIManager.closeUI(trader2.getPlayer());
    }

    public void cancelTrade() {
        List<Pokemon> trader1Storage = trader1.getTradeStorage();
        trader1Storage.forEach(pokemon -> {
            Pixelmon.storageManager.getParty(trader1.getPlayer()).add(pokemon);
        });
        List<Pokemon> trader2Storage = trader2.getTradeStorage();
        trader2Storage.forEach(pokemon -> {
            Pixelmon.storageManager.getParty(trader2.getPlayer()).add(pokemon);
        });
        tradeCancelled = true;
        UIManager.closeUI(trader1.getPlayer());
        UIManager.closeUI(trader2.getPlayer());
    }

    public boolean hasConfirmed(EntityPlayerMP player) {
        if(trader1.equals(player)) {
            return trader1.isConfirmed();
        }
        else if(trader2.equals(player)) {
            return trader2.isConfirmed();
        }
        else{
            return false;
        }
    }

}
