package dev.esoteric_enderman.slime_cosmetics_plugin.cosmetics.trails;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public enum TrailType {
    HEART(
            ChatColor.RED + "Hearts",
            "Spawns red hearts at your feet.",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWI3NmI0ZWU5ODg1NzIyOTdjYmQ4NzQ2ODNiZWU5NmFlM2M1NWNlOTRjMDA0ZTUxYWRjODJjZWUxNmNkMGIwYyJ9fX0=",
            Particle.HEART),

    FIRE(
            ChatColor.GOLD + "Flame",
            "Travel at blanzingly fast speeds with this trail!",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNlOTdmYWI0NzUzYjc1YmE1YjBjMDM4YmVkMzc3YjE2MmJhMjhiN2E1ZTI5MGFiZmQwMThhNTU4MWFjNTM4OCJ9fX0=",
            Particle.FLAME),
    WATER(
            ChatColor.BLUE + "Water",
            "Conquer the waves with this dripped out trail!",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ0MjE3ODJmYTllMDQxY2E4Y2NlNmFhYzNhMGIzMzdlY2MxYjk3MWIxNjhlY2JlZWY2OGI4ODEzZWUzOTZkYiJ9fX0=",
            Particle.SPLASH),
    BUBBLE(
            ChatColor.WHITE + "Bubble",
            "The bubbliest trail there is!",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjhmYjQwNGFmY2VhMTg3MTg3MmI4MWM3MTM5N2E1YzE2NTY0Mjg2MjYxZTI2NDdmZDY3NmZmYjk5MTc2MzJhZiJ9fX0=",
            Particle.BUBBLE),
    ANGER(
            ChatColor.RED + "Anger",
            "Are you infuriated? This trail is for you.",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGE0NjY0M2E0NjI5NzU3NjEwZDljMWQyMDAyYWIyYThjOGM5ZjFjYzIwNjhkNzllMzhkNWU4ODY3N2U4ODAyMSJ9fX0=",
            Particle.ANGRY_VILLAGER,
            new Vector(0, 1.795, 0)),
    EXPLOSIVE(
            ChatColor.GOLD + "Explosive",
            "You're about to blow up!",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWU0MzUyNjgwZDBiYjI5YjkxMzhhZjc4MzMwMWEzOTFiMzQwOTBjYjQ5NDFkNTJjMDg3Y2E3M2M4MDM2Y2I1MSJ9fX0=",
            Particle.EXPLOSION,
            new Vector(0, 1, 0)),

    SLIME(
            ChatColor.GREEN + "Slime",
            ChatColor.GREEN.toString() + ChatColor.BOLD + "Slqmy" + ChatColor.GREEN + "'s favourite trail!",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZThkZDdkNDM0MWExODhkZTBiOWJmMjNlZmU0MTljNjRhMzQ1ODNjNjExYzFlYzllMWJjNzVmNGJhOWJlYzY1YyJ9fX0=",
            Particle.ITEM_SLIME);

    private final String name;
    private final String description;
    private final String value;
    private final Particle particle;
    private final Vector offset;

    TrailType(
            @NotNull final String displayName,
            @NotNull final String description,
            @NotNull final String value,
            @NotNull final Particle particle,
            @NotNull final Vector offset) {
        this.name = displayName;
        this.description = ChatColor.DARK_GRAY + "| " + ChatColor.GRAY + description;
        this.value = value;
        this.particle = particle;
        this.offset = offset;
    }

    TrailType(
            @NotNull final String displayName,
            @NotNull final String description,
            @NotNull final String value,
            @NotNull final Particle particle) {
        this.name = displayName;
        this.description = ChatColor.DARK_GRAY + "| " + ChatColor.GRAY + description;
        this.value = value;
        this.particle = particle;
        this.offset = new Vector(0, 0, 0);
    }

    public String getDisplayName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getValue() {
        return value;
    }

    public Particle getParticle() {
        return particle;
    }

    public Vector getOffSet() {
        return offset;
    }
}
