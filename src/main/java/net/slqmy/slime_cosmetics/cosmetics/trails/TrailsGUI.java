package net.slqmy.slime_cosmetics.cosmetics.trails;

import net.slqmy.slime_cosmetics.AbstractCosmetic;
import net.slqmy.slime_cosmetics.SlimeCosmeticsPlugin;
import net.slqmy.slime_cosmetics.utility.DebugUtility;
import net.slqmy.slime_cosmetics.utility.ItemUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class TrailsGUI {

	public TrailsGUI(@NotNull final SlimeCosmeticsPlugin plugin, @NotNull final Player player) {
		final Inventory inventory = Bukkit.createInventory(
				null,
				27,
				ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Trails");

		final List<TrailType> activeTrails = new ArrayList<>();

		final Map<UUID, ArrayList<AbstractCosmetic>> activeCosmetics = plugin.getActiveCosmetics();

		if (activeCosmetics.containsKey(player.getUniqueId())) {
			for (final AbstractCosmetic cosmetic : activeCosmetics.get(player.getUniqueId())) {
				if (cosmetic instanceof Trail) {
					activeTrails.add(((Trail) cosmetic).getTrailType());
				}
			}
		}

		final List<String> ownedCosmetics = plugin.getConfig().getStringList(player.getUniqueId().toString());

		for (final TrailType trail : TrailType.values()) {
			final ItemStack trailItem;

			try {
				trailItem = ItemUtility.createCustomPlayerSkull(
						trail.getValue(),
						trail.getDisplayName() +
								ChatColor.GRAY +
								" - "
								+
								(ownedCosmetics.contains(trail.name())
										? activeTrails.contains(trail)
												? ChatColor.RED +
														"Click to un-equip!"
												: ChatColor.GREEN + "Click to equip!"
										: ChatColor.DARK_RED + "Locked"),
						trail.getDescription());

				final ItemMeta hatMeta = trailItem.getItemMeta();
				assert hatMeta != null;
				hatMeta.setLocalizedName(trail.name());

				trailItem.setItemMeta(hatMeta);

				// Note: this doesn't work, at least with player heads.
				if (activeTrails.contains(trail)) {
					ItemUtility.addEnchantmentGlow(trailItem);
				}
			} catch (NoSuchFieldException | IllegalAccessException exception) {
				player.sendMessage(ChatColor.DARK_GRAY +
						"| " +
						ChatColor.RED
						+
						"Sorry, an error occurred while loading the GUI. Please report this to your server admin!");
				DebugUtility.error("Something went wrong while setting the texture value for hat '" + trail.name() + "'!");
				DebugUtility.error(exception);
				exception.printStackTrace();

				return;
			}

			inventory.addItem(trailItem);
		}

		player.closeInventory();

		player.openInventory(inventory);
	}
}
