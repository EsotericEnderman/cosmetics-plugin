package dev.esoteric_enderman.slime_cosmetics_plugin.commands;

import dev.esoteric_enderman.slime_cosmetics_plugin.AbstractCosmetic;
import dev.esoteric_enderman.slime_cosmetics_plugin.SlimeCosmeticsPlugin;
import dev.esoteric_enderman.slime_cosmetics_plugin.cosmetics.hats.Hat;
import dev.esoteric_enderman.slime_cosmetics_plugin.cosmetics.hats.HatType;
import dev.esoteric_enderman.slime_cosmetics_plugin.cosmetics.trails.Trail;
import dev.esoteric_enderman.slime_cosmetics_plugin.cosmetics.trails.TrailType;
import dev.esoteric_enderman.slime_cosmetics_plugin.utility.PluginUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class ManageCosmeticsCommand implements CommandExecutor {

    private final SlimeCosmeticsPlugin plugin;

    public ManageCosmeticsCommand(@NotNull final SlimeCosmeticsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("'/manage-cosmetics' is a player-only command!");

            if (sender instanceof CommandBlock) {
                final Location senderLocation = ((CommandBlock) sender).getLocation();

                PluginUtility.log(
                        "Command block at (" +
                                senderLocation.getX() +
                                ", " +
                                senderLocation.getY() +
                                ", " +
                                senderLocation.getZ() +
                                ") in world " +
                                Objects.requireNonNull(senderLocation.getWorld()).getName() +
                                " ran player-only command '/manage-cosmetics'!"
                );
            }

            return true;
        }

        if (args.length != 3) {
            return false;
        }

        final String playerName = args[1];

        final OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (player.getName() == null) {
            sender.sendMessage(ChatColor.RED + "Couldn't find player!");
        }

        final String action = args[0];
        final String cosmeticName = args[2].toUpperCase();

        boolean validCosmetic = false;

        for (final HatType hatType : HatType.values()) {
            if (hatType.name().equals(cosmeticName)) {
                validCosmetic = true;
                break;
            }
        }

        if (!validCosmetic) {
            for (final TrailType trailType : TrailType.values()) {
                if (trailType.name().equals(cosmeticName)) {
                    validCosmetic = true;
                    break;
                }
            }
        }

        if (!validCosmetic) {
            sender.sendMessage(ChatColor.RED + "Couldn't find cosmetic!");
            return true;
        }

        final List<String> ownedCosmetics = plugin.getConfig().getStringList(player.getUniqueId().toString());

        switch (action) {
            case "give" -> {
                if (ownedCosmetics.contains(cosmeticName)) {
                    sender.sendMessage(ChatColor.RED + "This player already owns this cosmetic!");
                    return true;
                }

                ownedCosmetics.add(cosmeticName);
                sender.sendMessage(ChatColor.GREEN +
                        "Successfully gave " +
                        ChatColor.UNDERLINE +
                        player.getName() +
                        ChatColor.GREEN +
                        " the " +
                        ChatColor.UNDERLINE +
                        cosmeticName.toLowerCase() +
                        ChatColor.GREEN +
                        " cosmetic!"
                );
            }
            case "remove", "take" -> {
                if (!ownedCosmetics.contains(cosmeticName)) {
                    sender.sendMessage(ChatColor.RED + "This player does not own this cosmetic!");
                    return true;
                }

                ownedCosmetics.remove(cosmeticName);
                sender.sendMessage(ChatColor.GREEN +
                        "Successfully removed " +
                        ChatColor.UNDERLINE +
                        player.getName() +
                        ChatColor.GREEN +
                        "'s " +
                        ChatColor.UNDERLINE +
                        cosmeticName.toLowerCase() +
                        ChatColor.GREEN +
                        " cosmetic!");

                if (plugin.getActiveCosmetics().containsKey(player.getUniqueId())) {
                    for (final AbstractCosmetic equippedCosmetic : plugin.getActiveCosmetics().get(player.getUniqueId())) {
                        try {
                            final TrailType trailType = TrailType.valueOf(cosmeticName);

                            if (equippedCosmetic instanceof Trail && ((Trail) equippedCosmetic).getTrailType() == trailType) {
                                equippedCosmetic.disable();
                            }
                        } catch (final IllegalArgumentException exception) {
                            final HatType hatType = HatType.valueOf(cosmeticName);

                            if (equippedCosmetic instanceof Hat && ((Hat) equippedCosmetic).getHatType() == hatType) {
                                equippedCosmetic.disable();
                            }
                        }
                    }
                }
            }

            default -> {
                return false;
            }
        }

        plugin.getConfig().set(player.getUniqueId().toString(), ownedCosmetics);

        plugin.saveConfig();

        return true;
    }
}
