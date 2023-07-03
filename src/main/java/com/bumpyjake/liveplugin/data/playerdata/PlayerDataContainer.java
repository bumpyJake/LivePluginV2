package com.bumpyjake.liveplugin.data.playerdata;

import com.marcusslover.plus.lib.container.extra.InitialLoading;
import com.marcusslover.plus.lib.container.type.MapContainer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@InitialLoading
public class PlayerDataContainer extends MapContainer<UUID, PlayerData> {

    public PlayerDataContainer() {
        super(UUID::toString, UUID::fromString, PlayerData.class);
    }

    @Override
    protected @NotNull PlayerData emptyValue(@NotNull UUID key) {
        return new PlayerData(key);
    }


}
