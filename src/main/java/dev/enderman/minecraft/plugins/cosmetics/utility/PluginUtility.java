package dev.enderman.minecraft.plugins.cosmetics.utility;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class PluginUtility {

    private static final Logger LOGGER = Bukkit.getLogger();

    public static void log(@NotNull final String message) {
        LOGGER.info(message);
    }
}
