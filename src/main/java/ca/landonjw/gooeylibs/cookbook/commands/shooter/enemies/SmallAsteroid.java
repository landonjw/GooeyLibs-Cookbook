package ca.landonjw.gooeylibs.cookbook.commands.shooter.enemies;

import ca.landonjw.gooeylibs.cookbook.commands.shooter.ShooterEnemy;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class SmallAsteroid extends ShooterEnemy {

    public SmallAsteroid(int col) {
        super(1, col);
    }

    @Override
    public Item getDisplay() {
        return Items.CLAY_BALL;
    }

}