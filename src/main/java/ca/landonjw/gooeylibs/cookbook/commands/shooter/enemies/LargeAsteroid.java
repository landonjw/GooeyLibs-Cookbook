package ca.landonjw.gooeylibs.cookbook.commands.shooter.enemies;

import ca.landonjw.gooeylibs.cookbook.commands.shooter.ShooterEnemy;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class LargeAsteroid extends ShooterEnemy {

    public LargeAsteroid(int col) {
        super(3, col);
    }

    @Override
    public Item getDisplay() {
        return Items.PRISMARINE_SHARD;
    }

}
