package com.bumpyjake.liveplugin.hooks;

import com.bumpyjake.liveplugin.Manager;
import com.bumpyjake.liveplugin.data.rank.Rank;
import com.bumpyjake.liveplugin.data.rank.RankManager;
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
            case "live" -> {
                return String.valueOf((Manager.getInstance().isLive(player)));
            }
            case "rec" -> {
                return String.valueOf((Manager.getInstance().isRec(player)));
            }
            case "icon" -> {
                Rank rank = Manager.getInstance().getRank(player);
                if (rank != null) {
                    return rank.getIcon();
                }
                return "";
            }
        };
        return null;
    }

}
