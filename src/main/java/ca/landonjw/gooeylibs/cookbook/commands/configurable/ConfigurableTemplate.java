package ca.landonjw.gooeylibs.cookbook.commands.configurable;

import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.helpers.TemplateHelper;
import ca.landonjw.gooeylibs2.api.template.LineType;
import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlot;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
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

    public static Builder configurableBuilder(int rows) {
        return new Builder(rows);
    }

    public static class Builder {

        private int rows;
        private String path;
        private ConfigurableTemplate templateInstance;

        public Builder(int rows) {
            this.rows = rows;
            this.templateInstance = new ConfigurableTemplate(TemplateHelper.slotsOf(rows * COLUMNS), path);
        }

        public Builder set(int index, @Nullable Button button) {
            this.templateInstance.set(index, button);
            return this;
        }

        public Builder set(int row, int col, @Nullable Button button) {
            this.templateInstance.set(row, col, button);
            return this;
        }

        public Builder row(int row, @Nullable Button button) {
            this.templateInstance.row(row, button);
            return this;
        }

        public Builder rowFromList(int row, @Nonnull List<Button> buttons) {
            this.templateInstance.rowFromList(row, buttons);
            return this;
        }

        public Builder column(int col, @Nullable Button button) {
            this.templateInstance.column(col, button);
            return this;
        }

        public Builder columnFromList(int col, @Nonnull List<Button> buttons) {
            this.templateInstance.columnFromList(col, buttons);
            return this;
        }

        public Builder line(@Nonnull LineType lineType, int startRow, int startCol, int length, @Nullable Button button) {
            this.templateInstance.line(lineType, startRow, startCol, length, button);
            return this;
        }

        public Builder lineFromList(@Nonnull LineType lineType, int startRow, int startCol, int length, @Nonnull List<Button> buttons) {
            this.templateInstance.lineFromList(lineType, startRow, startCol, length, buttons);
            return this;
        }

        public Builder square(int startRow, int startCol, int size, @Nullable Button button) {
            this.templateInstance.square(startRow, startCol, size, button);
            return this;
        }

        public Builder squareFromList(int startRow, int startCol, int size, @Nonnull List<Button> buttons) {
            this.templateInstance.squareFromList(startRow, startCol, size, buttons);
            return this;
        }

        public Builder rectangle(int startRow, int startCol, int length, int width, @Nullable Button button) {
            this.templateInstance.rectangle(startRow, startCol, length, width, button);
            return this;
        }

        public Builder rectangleFromList(int startRow, int startCol, int length, int width, @Nonnull List<Button> buttons) {
            this.templateInstance.rectangleFromList(startRow, startCol, length, width, buttons);
            return this;
        }

        public Builder border(int startRow, int startCol, int length, int width, @Nullable Button button) {
            this.templateInstance.border(startRow, startCol, length, width, button);
            return this;
        }

        public Builder borderFromList(int startRow, int startCol, int length, int width, @Nonnull List<Button> buttons) {
            this.templateInstance.borderFromList(startRow, startCol, length, width, buttons);
            return this;
        }

        public Builder checker(int startRow, int startCol, int length, int width, @Nullable Button button, @Nullable Button button2) {
            this.templateInstance.checker(startRow, startCol, length, width, button, button2);
            return this;
        }

        public Builder checkerFromList(int startRow, int startCol, int length, int width, @Nonnull List<Button> buttons, @Nonnull List<Button> buttons2) {
            this.templateInstance.checkerFromList(startRow, startCol, length, width, buttons, buttons2);
            return this;
        }

        public Builder fill(@Nullable Button button) {
            this.templateInstance.fill(button);
            return this;
        }

        public Builder fillFromList(@Nonnull List<Button> buttons) {
            this.templateInstance.fillFromList(buttons);
            return this;
        }

        public Builder configPath(String path) {
            this.path = path;
            return this;
        }

        public ConfigurableTemplate build() {
            ConfigurableTemplate templateToReturn = templateInstance;
            templateInstance = new ConfigurableTemplate(TemplateHelper.slotsOf(rows * COLUMNS), path);
            return templateToReturn;
        }

    }

}