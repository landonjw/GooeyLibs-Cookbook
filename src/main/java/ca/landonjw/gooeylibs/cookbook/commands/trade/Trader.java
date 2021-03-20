package ca.landonjw.gooeylibs.cookbook.commands.trade;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;

public class Trader {

    private EntityPlayerMP player;
    private List<Pokemon> tradeStorage = new ArrayList<>();
    private boolean confirmed;

    public Trader(EntityPlayerMP player) {
        this.player = player;
    }

    public EntityPlayerMP getPlayer() {
        return player;
    }

    public List<Pokemon> getTradeStorage() {
        return tradeStorage;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean equals(Object obj) {
        if (obj instanceof EntityPlayerMP) {
            return obj.equals(this.player);
        }
        return false;
    }

    public void retrieveFromStorage(int index) {
        if(tradeStorage.size() < index) return;
        Pokemon pokemon = tradeStorage.get(index);
        Pixelmon.storageManager.getParty(player).add(pokemon);
        tradeStorage.remove(index);
    }

    public void addToStorage(int partyIndex) {
        Pokemon pokemon = Pixelmon.storageManager.getParty(player).get(partyIndex);
        Pixelmon.storageManager.getParty(player).set(partyIndex, null);
        tradeStorage.add(pokemon);
    }

}
