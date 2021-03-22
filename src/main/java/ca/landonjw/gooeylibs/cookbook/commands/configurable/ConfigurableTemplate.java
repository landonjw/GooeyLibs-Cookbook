package ca.landonjw.gooeylibs.cookbook.commands.configurable;

import ca.landonjw.gooeylibs.api.button.Button;
import ca.landonjw.gooeylibs.api.button.GooeyButton;
import ca.landonjw.gooeylibs.api.template.LineType;
import ca.landonjw.gooeylibs.api.template.slot.TemplateSlot;
import ca.landonjw.gooeylibs.api.template.types.ChestTemplate;
import ca.landonjw.gooeylibs.cookbook.configuration.Configuration;
import ca.landonjw.gooeylibs.cookbook.configuration.elements.ConfigElement;
import ca.landonjw.gooeylibs.cookbook.configuration.elements.ConfigItemStack;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConfigurableTemplate extends ChestTemplate {

    private String path;
    private List<ConfigElement> itemsForDisplay = new ArrayList<>();

    public ConfigurableTemplate(TemplateSlot[] slots, String path) {
        super(slots);
        this.path = path;
        generateConfigKeys();
    }

    private void generateConfigKeys() {
        for(int i = 0; i < getSlots().size(); i++) {
            this.itemsForDisplay.add(new ConfigItemStack("Item-Display." + i, getSlot(i).getStack()));
        }
    }

    public CompletableFuture<Void> load() {
        return CompletableFuture.runAsync(() -> {
            Configuration.load(new File(path), itemsForDisplay);
            for(int i = 0; i < getSize(); i++) {
                Button button = getSlot(i).getButton().orElse(null);
                if(button instanceof GooeyButton) {
                    ItemStack stack = (ItemStack) itemsForDisplay.get(i).getValue().orElse(ItemStack.EMPTY);
                    ((GooeyButton) button).setDisplay(stack);
                }
            }
        });
    }

    public static Builder builder(int rows) {
        return new Builder(rows);
    }

    public static class Builder extends ChestTemplate.Builder {

        private String path;

        public Builder(int rows) {
            super(rows);
        }

        @Override
        public Builder set(int index, @Nullable Button button) {
            super.set(index, button);
            return this;
        }

        @Override
        public Builder set(int row, int col, @Nullable Button button) {
            super.set(row, col, button);
            return this;
        }

        @Override
        public Builder row(int row, @Nullable Button button) {
            super.row(row, button);
            return this;
        }

        @Override
        public Builder column(int col, @Nullable Button button) {
            super.column(col, button);
            return this;
        }

        @Override
        public Builder line(@Nonnull LineType lineType, int startRow, int startCol, int length, @Nullable Button button) {
            super.line(lineType, startRow, startCol, length, button);
            return this;
        }

        @Override
        public Builder square(int startRow, int startCol, int size, @Nullable Button button) {
            super.rectangle(startRow, startCol, size, size, button);
            return this;
        }

        @Override
        public Builder rectangle(int startRow, int startCol, int length, int width, @Nullable Button button) {
            super.rectangle(startRow, startCol, length, width, button);
            return this;
        }

        @Override
        public Builder border(int startRow, int startCol, int length, int width, @Nullable Button button) {
            super.border(startRow, startCol, length, width, button);
            return this;
        }

        @Override
        public Builder checker(int startRow, int startCol, int length, int width, @Nullable Button button, @Nullable Button button2) {
            super.checker(startRow, startCol, length, width, button, button2);
            return this;
        }

        @Override
        public Builder fill(@Nullable Button button) {
            super.fill(button);
            return this;
        }

        public Builder configPath(String path) {
            this.path = path;
            return this;
        }

        @Override
        public ConfigurableTemplate build() {
            return new ConfigurableTemplate(toSlots(), path);
        }

    }

}