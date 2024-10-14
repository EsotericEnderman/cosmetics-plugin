package dev.enderman.minecraft.plugins.cosmetics;

import dev.enderman.minecraft.plugins.cosmetics.commands.ManageCosmeticsCommand;
import dev.enderman.minecraft.plugins.cosmetics.navigation.CosmeticsCommand;
import dev.enderman.minecraft.plugins.cosmetics.navigation.CosmeticsListener;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;

import java.util.*;

public final class CosmeticsPlugin extends JavaPlugin {

    private final HashMap<UUID, ArrayList<AbstractCosmetic>> activeCosmetics = new HashMap<>();

    private final NamespacedKey hatNameKey = new NamespacedKey(this, "hat_name");

    private final NamespacedKey trailNameKey = new NamespacedKey(this, "trail_name");

    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("manage-cosmetics")).setExecutor(new ManageCosmeticsCommand(this));
        Objects.requireNonNull(getCommand("cosmetics")).setExecutor(new CosmeticsCommand());

        Bukkit.getPluginManager().registerEvents(new CosmeticsListener(this), this);
    }

    @Contract(pure = true)
    public Map<UUID, ArrayList<AbstractCosmetic>> getActiveCosmetics() {
        return activeCosmetics;
    }

    public NamespacedKey getHatNameKey() {
        return hatNameKey;
    }

    public NamespacedKey getTrailNameKey() {
        return trailNameKey;
    }
}
