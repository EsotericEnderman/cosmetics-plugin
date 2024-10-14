package dev.enderman.minecraft.plugins.cosmetics.cosmetics.hats;

import dev.enderman.minecraft.plugins.cosmetics.AbstractCosmetic;
import dev.enderman.minecraft.plugins.cosmetics.CosmeticsPlugin;
import dev.enderman.minecraft.plugins.cosmetics.utility.DebugUtility;
import dev.enderman.minecraft.plugins.cosmetics.utility.ItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class HatsGUI {

    public HatsGUI(@NotNull final CosmeticsPlugin plugin, @NotNull final Player player) {
        final Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.AQUA.toString() + ChatColor.BOLD + "Hats");

        HatType activeHat = null;

        final Map<UUID, ArrayList<AbstractCosmetic>> activeCosmetics = plugin.getActiveCosmetics();

        if (activeCosmetics.containsKey(player.getUniqueId())) {
            for (final AbstractCosmetic cosmetic : activeCosmetics.get(player.getUniqueId())) {
                if (cosmetic instanceof Hat) {
                    activeHat = ((Hat) cosmetic).getHatType();
                    break;
                }
            }
        }

        final List<String> ownedCosmetics = plugin.getConfig().getStringList(player.getUniqueId().toString());

        for (final HatType hat : HatType.values()) {
            final ItemStack hatItem;
            try {
                hatItem = ItemUtility.createCustomPlayerSkull(
                        hat.getValue(),
                        hat.getDisplayName() +
                                ChatColor.GRAY +
                                " - "
                                +
                                (ownedCosmetics.contains(hat.name())
                                        ? hat == activeHat
                                        ? ChatColor.RED + "Click to un-equip!"
                                        : ChatColor.GREEN + "Click to equip!"
                                        : ChatColor.DARK_RED + "Locked"),
                        hat.getDescription());

                final ItemMeta hatMeta = hatItem.getItemMeta();
                assert hatMeta != null;
                hatMeta.getPersistentDataContainer().set(plugin.getHatNameKey(), PersistentDataType.STRING, hat.name());

                hatItem.setItemMeta(hatMeta);
            } catch (NoSuchFieldException | IllegalAccessException exception) {
                player.sendMessage(ChatColor.DARK_GRAY +
                        "| " +
                        ChatColor.RED
                        +
                        "Sorry, an error occurred while loading the GUI. Please report this to your server admin!");
                DebugUtility.error("Something went wrong while setting the texture value for hat '" + hat.name() + "'!");
                DebugUtility.error(exception);
                exception.printStackTrace();

                return;
            }

            inventory.addItem(hatItem);
        }

        player.closeInventory();
        player.openInventory(inventory);
    }
}
