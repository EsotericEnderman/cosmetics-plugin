package dev.enderman.minecraft.plugins.cosmetics.navigation.guis;

import dev.enderman.minecraft.plugins.cosmetics.utility.ItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// Idea: make a GUI manager.
public final class CosmeticsGUI implements Listener {

    public CosmeticsGUI(@NotNull final Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 27,
                ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Cosmetics");

        final ItemStack hats = ItemUtility.createCustomItem(
                Material.DIAMOND_HELMET,
                ChatColor.AQUA.toString() + ChatColor.BOLD + "Hats",
                ChatColor.DARK_GRAY + "| " + ChatColor.GRAY + "Equip a custom player head!");

        inventory.setItem(12, hats);

        final ItemStack trails = ItemUtility.createCustomItem(
                Material.IRON_BOOTS,
                ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Trails",
                ChatColor.DARK_GRAY +
                        "| " +
                        ChatColor.GRAY +
                        "Equip special trails that spawn particles at your feet!");

        inventory.setItem(14, trails);

        player.openInventory(inventory);
    }
}
