package net.slqmy.slime_cosmetics_plugin.utility;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.slqmy.slime_cosmetics_plugin.utility.types.ApplicableEnchantment;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public final class ItemUtility {

	public static @NotNull ItemStack createCustomItem(Material material, int count, String displayName, String lore,
			ApplicableEnchantment @NotNull [] enchantments, Multimap<Attribute, AttributeModifier> modifiers) {
		ItemStack item = new ItemStack(material, count);

		ItemMeta meta = item.getItemMeta();
		assert meta != null;

		meta.setDisplayName(ChatColor.RESET + displayName);

		meta.setLore(lore == null ? null
				: Arrays
						.stream(lore.split("\n"))
						.map((final String line) -> ChatColor.RESET + line)
						.toList());

		meta.setAttributeModifiers(modifiers);

		for (ApplicableEnchantment enchantment : enchantments) {
			meta.addEnchant(enchantment.getEnchantment(), enchantment.getLevel(), enchantment.ignoresLevelRestriction());
		}

		item.setItemMeta(meta);

		return item;
	}

	public static @NotNull ItemStack createCustomItem(Material material, int count, String displayName) {
		return createCustomItem(material, count, displayName, null);
	}

	public static @NotNull ItemStack createCustomItem(Material material, int count, String displayName, String lore) {
		return createCustomItem(material, count, displayName, lore, new ApplicableEnchantment[] {});
	}

	public static @NotNull ItemStack createCustomItem(Material material, int count, String displayName, String lore,
			ApplicableEnchantment[] enchantments) {
		return createCustomItem(material, count, displayName, lore, enchantments, ArrayListMultimap.create());
	}

	public static @NotNull ItemStack createCustomItem(Material material, String displayName) {
		return createCustomItem(material, displayName, null);
	}

	public static @NotNull ItemStack createCustomItem(Material material, String displayName, String lore) {
		return createCustomItem(material, 1, displayName, lore);
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue)
			throws NoSuchFieldException, IllegalAccessException {
		return createCustomPlayerSkull(skullTextureValue, 1);
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue, int count)
			throws NoSuchFieldException, IllegalAccessException {
		return createCustomPlayerSkull(skullTextureValue, count, null);
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue, int count, String displayName)
			throws NoSuchFieldException, IllegalAccessException {
		return createCustomPlayerSkull(skullTextureValue, count, displayName, null);
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue, int count, String displayName,
			String lore) throws NoSuchFieldException, IllegalAccessException {
		return createCustomPlayerSkull(skullTextureValue, count, displayName, lore, new ApplicableEnchantment[] {});
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue, int count, String displayName,
			String lore, ApplicableEnchantment[] enchantments) throws NoSuchFieldException, IllegalAccessException {
		return createCustomPlayerSkull(
				skullTextureValue,
				count,
				displayName,
				lore,
				enchantments,
				ArrayListMultimap.create());
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue, int count, String displayName,
			String lore, ApplicableEnchantment[] enchantments, Multimap<Attribute, AttributeModifier> modifiers)
			throws NoSuchFieldException, IllegalAccessException {
		ItemStack skull = createCustomItem(Material.PLAYER_HEAD, count, displayName, lore, enchantments, modifiers);

		Field field;

		ItemMeta skullMeta = skull.getItemMeta();
		assert skullMeta != null;

		UUID skullUUID = UUID.nameUUIDFromBytes(
				(displayName == null ? skullMeta.getDisplayName() : displayName).getBytes());

		GameProfile profile = new GameProfile(skullUUID, "name");
		profile.getProperties().put("textures", new Property("textures", skullTextureValue));

		field = skullMeta.getClass().getDeclaredField("profile");
		field.setAccessible(true);
		field.set(skullMeta, profile);

		skull.setItemMeta(skullMeta);
		return skull;
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue, String displayName)
			throws NoSuchFieldException, IllegalAccessException {
		return createCustomPlayerSkull(skullTextureValue, displayName, null);
	}

	public static @NotNull ItemStack createCustomPlayerSkull(String skullTextureValue, String displayName, String lore)
			throws NoSuchFieldException, IllegalAccessException {
		return createCustomPlayerSkull(skullTextureValue, 1, displayName, lore);
	}
}
