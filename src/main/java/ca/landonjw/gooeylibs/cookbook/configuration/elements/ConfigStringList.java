package ca.landonjw.gooeylibs.cookbook.configuration.elements;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * A {@link ConfigElement} that stores a boolean value.
 *
 * @author landonjw
 */
public class ConfigStringList extends ConfigElement<List<String>> {

	/**
	 * Default constructor.
	 *
	 * @param path  the path to the value in configuration, periods are used to signify a jump in objects.
	 * @param value the value stored in the element
	 */
	public ConfigStringList(@Nonnull String path, @Nullable List<String> value) {
		super(path, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(@Nonnull JsonElement data) {
		if(data.isJsonArray()) {
			List<String> stringList = Lists.newArrayList();
			for(JsonElement element : data.getAsJsonArray()) {
				if(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
					stringList.add(element.getAsString());
				}
			}
			setValue(stringList);
		}
	}

	@Override
	public Optional<List<String>> getValue() {
		return (value != null) ? Optional.of(Lists.newArrayList(value)) : Optional.empty();
	}

	/** {@inheritDoc} */
	@Override
	public JsonElement serialize() {
		if(value == null) {
			return JsonNull.INSTANCE;
		}
		else {
			JsonArray array = new JsonArray();

			for(String element : value) {
				array.add(element);
			}
			return array;
		}
	}

}
