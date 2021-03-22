package ca.landonjw.gooeylibs.cookbook.configuration.elements;

import com.google.gson.JsonElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Represents an element from the configuration.
 *
 * @param <T> the type of value the element holds
 * @author landonjw
 */
public abstract class ConfigElement<T> {

	/** The path to the value in configuration. Periods are used to signify a jump in objects. */
	protected String path;
	/** The value stored in the element. */
	protected T value;

	/**
	 * Default constructor.
	 *
	 * @param path  the path to the value in configuration, periods are used to signify a jump in objects.
	 * @param value the value stored in the element
	 */
	public ConfigElement(@Nonnull String path, @Nullable T value) {
		this.path = path;
		this.value = value;
	}

	/**
	 * Gets the path to the value in configuration. Periods are used to signify a jump in objects.
	 *
	 * An example of the period use would be "X.Y.Z", which would look like
	 * <pre>{@code
	 * X {
	 *     Y{
	 *         Z
	 *     }
	 * }
	 * }</pre>
	 *
	 * @return path to value in configuration
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the stored value.
	 *
	 * Setting this will <strong>not</strong> save the configuration value to file.
	 *
	 * @param value the new value
	 */
	public void setValue(@Nullable T value) {
		this.value = value;
	}

	/**
	 * Sets the value from data in JSON format.
	 *
	 * This is used for deserialization purposes.
	 *
	 * @param data the json data to set value from
	 */
	public abstract void setValue(@Nonnull JsonElement data);

	/**
	 * Gets the stored value.
	 *
	 * @return the stored value
	 */
	public Optional<T> getValue() {
		return Optional.ofNullable(value);
	}

	/**
	 * Serializes the element down to JSON data.
	 *
	 * @return the configuration element value represented by JSON.
	 */
	public abstract JsonElement serialize();

}