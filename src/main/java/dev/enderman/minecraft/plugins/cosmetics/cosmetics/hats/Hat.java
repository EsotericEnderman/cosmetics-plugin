package dev.enderman.minecraft.plugins.cosmetics.cosmetics.hats;

import dev.enderman.minecraft.plugins.cosmetics.AbstractCosmetic;
import dev.enderman.minecraft.plugins.cosmetics.CosmeticsPlugin;
import dev.enderman.minecraft.plugins.cosmetics.utility.DebugUtility;
import dev.enderman.minecraft.plugins.cosmetics.utility.ItemUtility;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class Hat extends AbstractCosmetic {

    private final HatType hatType;

    public Hat(@NotNull CosmeticsPlugin plugin, @NotNull Player player, @NotNull final HatType hat) {
        super(plugin, player);
        this.hatType = hat;
    }

    @Override
    public void enable() {
        try {
            player.getInventory().setHelmet(
                    ItemUtility.createCustomPlayerSkull(hatType.getValue(), hatType.getDisplayName(), hatType.getDescription()));
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            player.sendMessage(ChatColor.DARK_GRAY + "| " + ChatColor.RED + "Sorry, an error occurred while giving you the "
                    + hatType.getDisplayName() + ChatColor.RED + " hat. Please report this to your server admin!");
            DebugUtility.error("Something went wrong while setting the texture value for hat " + hatType.name() + "!");
            DebugUtility.error(exception);
            exception.printStackTrace();
        }
    }

    @Override
    public void disable() {
        player.getInventory().setHelmet(null);
    }

    public HatType getHatType() {
        return hatType;
    }
}
