package dev.esoteric_enderman.slime_cosmetics_plugin;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCosmetic {

	protected SlimeCosmeticsPlugin plugin;
	protected final Player player;

	protected AbstractCosmetic(@NotNull final SlimeCosmeticsPlugin plugin, @NotNull final Player player) {
		this.plugin = plugin;
		this.player = player;
	}

	public abstract void enable();

	public abstract void disable();
}
