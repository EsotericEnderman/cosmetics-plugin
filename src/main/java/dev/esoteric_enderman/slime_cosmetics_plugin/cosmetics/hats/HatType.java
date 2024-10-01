package dev.esoteric_enderman.slime_cosmetics_plugin.cosmetics.hats;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public enum HatType {
    TOP_HAT(
            ChatColor.DARK_GREEN + "Top Hat",
            "A very fancy top hat.",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWYyYjk1ODk5OWY4YWZiY2I0OGE2YzE2Y2Q5ZGM5ZDM1NWQwNGUzNGI2ODdjZjI1NTFmZWQ1YjMyZThmODMxIn19fQ=="),

    TIGER(ChatColor.GOLD + "Tiger",
            "A fierce companion from the wild jungle... turned into a hat",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjA2N2MwZGUxOGI4OTdkZGRiMGQyNDQyZDUzMDkxZDI2MmQ1OTE0NjcwMDMwYzgwNjA2ZmFhNGM4YjE0NWI0NyJ9fX0="),

    DOG(
            ChatColor.YELLOW + "Dog",
            "Man's best friend!",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q4ZmYxMzM1NzRiNjJhNTZkOGMwMmU3ZDk5OGQxNDEwNDQzYjNkNzNmNWM0OTQ4ZDlhYzhjZTcxZGViODdmMCJ9fX0=");

    private final String name;
    private final String description;
    private final String value;

    HatType(@NotNull final String displayName, @NotNull final String description, @NotNull final String value) {
        this.name = displayName;
        this.description = ChatColor.DARK_GRAY + "| " + ChatColor.GRAY + description;
        this.value = value;
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
}
