package dev.enderman.minecraft.plugins.cosmetics.navigation;

import dev.enderman.minecraft.plugins.cosmetics.AbstractCosmetic;
import dev.enderman.minecraft.plugins.cosmetics.CosmeticsPlugin;
import dev.enderman.minecraft.plugins.cosmetics.cosmetics.hats.Hat;
import dev.enderman.minecraft.plugins.cosmetics.cosmetics.hats.HatType;
import dev.enderman.minecraft.plugins.cosmetics.cosmetics.hats.HatsGUI;
import dev.enderman.minecraft.plugins.cosmetics.cosmetics.trails.Trail;
import dev.enderman.minecraft.plugins.cosmetics.cosmetics.trails.TrailType;
import dev.enderman.minecraft.plugins.cosmetics.cosmetics.trails.TrailsGUI;
import dev.enderman.minecraft.plugins.cosmetics.utility.PluginUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class CosmeticsListener implements Listener {

    private final CosmeticsPlugin plugin;

    public CosmeticsListener(@NotNull final CosmeticsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(@NotNull final InventoryClickEvent event) {
        final ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) {
            return;
        }

        final String title = event.getView().getTitle();
        final Player player = (Player) event.getWhoClicked();
        final UUID uuid = player.getUniqueId();

        int slotClicked = event.getRawSlot();

        final List<AbstractCosmetic> activePlayerCosmetics = plugin.getActiveCosmetics().get(uuid);
        List<AbstractCosmetic> activePlayerCosmeticsCopy = new ArrayList<>();

        if (activePlayerCosmetics != null) {
            activePlayerCosmeticsCopy = new ArrayList<>(activePlayerCosmetics);
        }

        final List<String> ownedCosmetics = plugin.getConfig().getStringList(player.getUniqueId().toString());

        if (title.equals(ChatColor.DARK_GRAY + String.valueOf(ChatColor.BOLD) + "Cosmetics")) {
            event.setCancelled(true);

            if (slotClicked == 12) {
                new HatsGUI(plugin, player);
            } else if (slotClicked == 14) {
                new TrailsGUI(plugin, player);
            }
        } else if (title.equals(ChatColor.AQUA + String.valueOf(ChatColor.BOLD) + "Hats")) {
            event.setCancelled(true);

            final ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            assert clickedItemMeta != null;

            final HatType hatType = HatType.valueOf(clickedItemMeta.getPersistentDataContainer().get(plugin.getHatNameKey(), PersistentDataType.STRING));

            if (!ownedCosmetics.contains(hatType.name())) {
                player.sendMessage(ChatColor.RED + "Sorry, you don't own this hat!");
                return;
            }

            if (plugin.getActiveCosmetics().containsKey(uuid)) {
                for (AbstractCosmetic cosmetic : activePlayerCosmeticsCopy) {
                    if (cosmetic instanceof Hat) {
                        cosmetic.disable();
                        plugin.getActiveCosmetics().get(uuid).remove(cosmetic);

                        if (((Hat) cosmetic).getHatType() == hatType) {
                            player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.YELLOW + "You have un-equipped your "
                                    + hatType.getDisplayName() + ChatColor.YELLOW + " hat!");

                            new HatsGUI(plugin, player);
                            return;
                        }
                    }
                }
            } else {
                plugin.getActiveCosmetics().put(uuid, new ArrayList<>());
            }

            final Hat newHat = new Hat(plugin, player, hatType);
            newHat.enable();

            plugin.getActiveCosmetics().get(uuid).add(newHat);

            player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.YELLOW + "You have equipped the "
                    + hatType.getDisplayName() + ChatColor.YELLOW + " hat!");

            new HatsGUI(plugin, player);
        } else if (title.equals(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Trails")) {
            event.setCancelled(true);

            final ItemMeta clickedItemMeta = clickedItem.getItemMeta();
            assert clickedItemMeta != null;

            final TrailType trailType = TrailType.valueOf(clickedItemMeta.getPersistentDataContainer().get(plugin.getTrailNameKey(), PersistentDataType.STRING));

            if (!ownedCosmetics.contains(trailType.name())) {
                player.sendMessage(ChatColor.RED + "Sorry, you don't own this trail!");
                return;
            }

            if (plugin.getActiveCosmetics().containsKey(uuid)) {
                for (AbstractCosmetic cosmetic : activePlayerCosmeticsCopy) {
                    if (cosmetic instanceof Trail && ((Trail) cosmetic).getTrailType() == trailType) {
                        PluginUtility.log(((Trail) cosmetic).getTrailType().name());
                        PluginUtility.log(trailType.name());

                        player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.YELLOW + "You have un-equipped your "
                                + trailType.getDisplayName() + ChatColor.YELLOW + " trail!");

                        cosmetic.disable();
                        plugin.getActiveCosmetics().get(uuid).remove(cosmetic);

                        new TrailsGUI(plugin, player);
                        return;
                    }
                }
            } else {
                plugin.getActiveCosmetics().put(uuid, new ArrayList<>());
            }

            final Trail newTrail = new Trail(plugin, player, trailType);
            newTrail.enable();

            plugin.getActiveCosmetics().get(uuid).add(newTrail);

            player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.YELLOW + "You have equipped the "
                    + trailType.getDisplayName() + ChatColor.YELLOW + " trail!");

            new TrailsGUI(plugin, player);
        } else if (slotClicked == 5 &&
                event.getInventory().getType() == InventoryType.CRAFTING &&
                plugin.getActiveCosmetics().containsKey(uuid)) {

            for (AbstractCosmetic cosmetic : activePlayerCosmeticsCopy) {
                if (cosmetic instanceof Hat) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(@NotNull final PlayerQuitEvent event) {
        final Map<UUID, ArrayList<AbstractCosmetic>> activeCosmetics = plugin.getActiveCosmetics();
        final UUID uuid = event.getPlayer().getUniqueId();

        if (plugin.getActiveCosmetics().containsKey(uuid)) {
            for (final AbstractCosmetic cosmetic : activeCosmetics.get(uuid)) {
                cosmetic.disable();
            }

            activeCosmetics.remove(uuid);
        }
    }
}
