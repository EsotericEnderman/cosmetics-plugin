package dev.enderman.minecraft.plugins.cosmetics;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCosmetic {

    protected final Player player;
    protected CosmeticsPlugin plugin;

    protected AbstractCosmetic(@NotNull final CosmeticsPlugin plugin, @NotNull final Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public abstract void enable();

    public abstract void disable();
}
