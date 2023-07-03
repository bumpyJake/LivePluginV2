package com.bumpyjake.liveplugin.data.rank;

import com.bumpyjake.liveplugin.LivePlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RankManager {
    private static RankManager instance;

    private final Map<String, Rank> ranks = new HashMap();

    public RankManager() {
        instance = this;
    }

    public static RankManager getInstance() {
        return Objects.requireNonNullElseGet(instance, RankManager::new);
    }

    public void init() {
        FileConfiguration config = LivePlugin.getInstance().getConfig();

        Rank liveRank = new Rank("live", config.getString("live-group"),
                config.getString("live-on-message"), config.getString("live-off-message"), config.getString("live-on-message-all"),
                config.getString("live-on-title"), config.getString("live-on-subtitle"));

        Rank recRank = new Rank("rec", config.getString("rec-group"),
                config.getString("rec-on-message"), config.getString("rec-off-message"), config.getString("rec-on-message-all"),
                config.getString("rec-on-title"), config.getString("rec-on-subtitle"));

        ranks.put("live", liveRank);
        ranks.put("rec", recRank);

        LivePlugin.getInstance().getLogger().info("Initialized ranks");
    }

    public void clear() {
        ranks.clear();
    }

    public void reset() {
        clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                init();
            }
        }.runTaskLater(LivePlugin.getInstance(), 2L);
    }


    public Rank getRank(String name) {
        return ranks.get(name);
    }


}
