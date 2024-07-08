package net.slqmy.slime_cosmetics.types;

import net.slqmy.slime_cosmetics.utility.DebugUtility;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public abstract class AbstractCustomEnchantment extends Enchantment {

	protected AbstractCustomEnchantment(@NotNull final NamespacedKey key) {
		super(key);

		try {
			final Field field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			field.set(null, true);

			Enchantment.registerEnchantment(this);
		} catch (final NoSuchFieldException | IllegalAccessException exception) {
			DebugUtility.error("There was an error registering enchantment " + this.getKey().getKey() + "!");
			DebugUtility.error(exception);
		}
	}
}
