package net.slqmy.slime_cosmetics.custom_enchantments;

import net.slqmy.slime_cosmetics.types.AbstractCustomEnchantment;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GlowEnchantment extends AbstractCustomEnchantment {

	public GlowEnchantment() {
		super(NamespacedKey.minecraft("glow"));
	}

	@NotNull
	@Override
	public String getName() {
		return "Glow";
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

	@NotNull
	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.VANISHABLE;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean conflictsWith(@NotNull final Enchantment other) {
		return false;
	}

	@Override
	public boolean canEnchantItem(@NotNull final ItemStack item) {
		return true;
	}
}
