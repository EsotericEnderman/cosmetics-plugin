package net.slqmy.slime_cosmetics;

import net.slqmy.slime_cosmetics.commands.ManageCosmeticsCommand;
import net.slqmy.slime_cosmetics.custom_enchantments.GlowEnchantment;
import net.slqmy.slime_cosmetics.navigation.CosmeticsCommand;
import net.slqmy.slime_cosmetics.navigation.CosmeticsListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class SlimeCosmeticsPlugin extends JavaPlugin {

	private final HashMap<UUID, ArrayList<AbstractCosmetic>> activeCosmetics = new HashMap<>();

	@Override
	public void onEnable() {
		getDataFolder().mkdir();

		getConfig().options().copyDefaults();
		saveDefaultConfig();

		Objects.requireNonNull(getCommand("manage-cosmetics")).setExecutor(new ManageCosmeticsCommand(this));
		Objects.requireNonNull(getCommand("cosmetics")).setExecutor(new CosmeticsCommand());

		Bukkit.getPluginManager().registerEvents(new CosmeticsListener(this), this);

		new GlowEnchantment();
	}

	@Contract(pure = true)
	public Map<UUID, ArrayList<AbstractCosmetic>> getActiveCosmetics() {
		return activeCosmetics;
	}
}
