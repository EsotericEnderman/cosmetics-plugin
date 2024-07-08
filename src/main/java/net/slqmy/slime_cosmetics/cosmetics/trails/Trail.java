package net.slqmy.slime_cosmetics.cosmetics.trails;

import net.slqmy.slime_cosmetics.AbstractCosmetic;
import net.slqmy.slime_cosmetics.SlimeCosmeticsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public final class Trail extends AbstractCosmetic {

	private final TrailType trailType;

	private BukkitTask task;

	public Trail(@NotNull SlimeCosmeticsPlugin plugin, @NotNull Player player, @NotNull final TrailType trail) {
		super(plugin, player);

		this.plugin = plugin;
		this.trailType = trail;
	}

	@Override
	public void enable() {
		final Particle particle = trailType.getParticle();

		task = Bukkit.getScheduler().runTaskTimer(
				plugin,
				new Runnable() {

					private Location previousLocation = player.getLocation();

					@Override
					public void run() {
						final Location currentLocation = player.getLocation();

						final float MIN_DISTANCE = 0.1F;

						if ((Math.abs(currentLocation.getX() - previousLocation.getX()) >= MIN_DISTANCE) ||
								(Math.abs(currentLocation.getZ() - previousLocation.getZ()) >= MIN_DISTANCE)) {
							final Location location = player.getLocation();
							final World world = location.getWorld();
							assert world != null;

							world.spawnParticle(particle, currentLocation.clone().add(trailType.getOffSet()), 1);
						}

						previousLocation = currentLocation;
					}
				},
				0,
				0);
	}

	@Override
	public void disable() {
		task.cancel();
	}

	public TrailType getTrailType() {
		return trailType;
	}
}
