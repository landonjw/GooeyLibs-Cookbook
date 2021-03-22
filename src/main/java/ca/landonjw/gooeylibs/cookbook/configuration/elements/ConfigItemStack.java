package ca.landonjw.gooeylibs.cookbook.configuration.elements;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ConfigItemStack extends ConfigElement<ItemStack> {

    /**
     * Default constructor.
     *
     * @param path  the path to the value in configuration, periods are used to signify a jump in objects.
     * @param value the value stored in the element
     */
    public ConfigItemStack(String path, ItemStack value) {
        super(path, value);
    }

    @Override
    public void setValue(JsonElement data) {
        String itemStr = data.getAsString();
        String[] split = itemStr.split(" ");
        Item item = Item.getByNameOrId(split[0]);
        if(item == null) {
            value = ItemStack.EMPTY;
        }
        else {
            if(split.length == 2) {
                try {
                    value = new ItemStack(item, Integer.parseInt(split[1]));
                }
                catch (NumberFormatException e) {
                    value = new ItemStack(item);
                }
            }
            else{
                value = new ItemStack(item);
            }
        }
    }

    @Override
    public JsonElement serialize() {
        String namespace = value.getItem().getRegistryName().getNamespace();
        String path = value.getItem().getRegistryName().getPath();
        int amount = value.getCount();

        return new JsonPrimitive(namespace + ":" + path + " " + amount);
    }

}
