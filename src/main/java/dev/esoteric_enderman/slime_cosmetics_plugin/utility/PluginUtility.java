package dev.esoteric_enderman.slime_cosmetics_plugin.utility;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class PluginUtility {

    private static final Logger LOGGER = Bukkit.getLogger();

    public static void log(@NotNull final String message) {
        LOGGER.info(message);
    }
}
