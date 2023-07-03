package com.bumpyjake.liveplugin.data.playerdata;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class PlayerData {
    private final UUID uuid;

    @Setter(AccessLevel.PUBLIC)
    private String name;
    private boolean isLive;
    private boolean isRec;

    public @Nullable Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
