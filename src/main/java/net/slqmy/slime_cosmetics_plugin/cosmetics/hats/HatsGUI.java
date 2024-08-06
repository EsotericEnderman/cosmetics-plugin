package net.slqmy.slime_cosmetics_plugin.cosmetics.hats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import net.slqmy.slime_cosmetics_plugin.AbstractCosmetic;
import net.slqmy.slime_cosmetics_plugin.SlimeCosmeticsPlugin;
import net.slqmy.slime_cosmetics_plugin.utility.DebugUtility;
import net.slqmy.slime_cosmetics_plugin.utility.ItemUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class HatsGUI {

	public HatsGUI(@NotNull final SlimeCosmeticsPlugin plugin, @NotNull final Player player) {
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
				hatMeta.setLocalizedName(hat.name());

				hatItem.setItemMeta(hatMeta);

				// Note: this doesn't work, at least with player heads.
				if (hat == activeHat) {
					ItemUtility.addEnchantmentGlow(hatItem);
				}
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
