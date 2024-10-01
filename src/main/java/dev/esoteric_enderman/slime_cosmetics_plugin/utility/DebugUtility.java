package dev.esoteric_enderman.slime_cosmetics_plugin.utility;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public final class DebugUtility {

    private static final Logger LOGGER = Bukkit.getLogger();

    public static void log(final Object @NotNull ... values) {
        for (final Object value : values) {
            String finalMessage = "[DEBUG] " + (value == null ? "NULL" : value.toString());
            LOGGER.warning(finalMessage);
        }
    }

    public static void error(final Object @NotNull ... values) {
        for (final Object value : values) {
            String finalMessage = value == null ? "NULL" : value.toString();
            LOGGER.severe(finalMessage);
        }
    }
}
