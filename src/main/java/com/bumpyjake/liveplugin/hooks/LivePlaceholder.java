package com.bumpyjake.liveplugin.hooks;

import com.bumpyjake.liveplugin.Manager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LivePlaceholder extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "live";
    }

    @Override
    public @NotNull String getAuthor() {
        return "bumpyJake";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    public @Nullable String onPlaceholderRequest(final Player player, @NotNull final String args) {
        switch (args) {
            case "islive" -> {
                return String.valueOf((Manager.getInstance().isLive(player)));
            }
            case "isRec" -> {
                return String.valueOf((Manager.getInstance().isRec(player)));
            }
        };
        return null;
    }

}
