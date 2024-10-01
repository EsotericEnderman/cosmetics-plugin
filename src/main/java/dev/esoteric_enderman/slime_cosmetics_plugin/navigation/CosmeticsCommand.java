package dev.esoteric_enderman.slime_cosmetics_plugin.navigation;

import dev.esoteric_enderman.slime_cosmetics_plugin.navigation.guis.CosmeticsGUI;
import dev.esoteric_enderman.slime_cosmetics_plugin.utility.PluginUtility;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class CosmeticsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            @NotNull final CommandSender sender,
            @NotNull final Command command,
            @NotNull final String label,
            final String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("'/cosmetics' is a player-only command!");

            if (sender instanceof BlockCommandSender) {
                final Location senderLocation = ((BlockCommandSender) sender).getBlock().getLocation();

                PluginUtility.log(
                        "Command block at (" +
                                senderLocation.getX() +
                                ", " +
                                senderLocation.getY() +
                                ", " +
                                senderLocation.getZ() +
                                ") in world " +
                                Objects.requireNonNull(senderLocation.getWorld()).getName() +
                                " ran player-only command '/cosmetics'!");
            }

            return true;
        }

        if (args.length != 0) {
            return false;
        }

        new CosmeticsGUI((Player) sender);

        return true;
    }
}
