package com.bumpyjake.liveplugin.data.playerdata;

import com.bumpyjake.liveplugin.LivePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class PlayerDataHandler implements Listener {
    private static PlayerDataHandler instance;

    public PlayerDataHandler() {
        instance = this;
    }

    public static PlayerDataHandler getInstance() {
        return Objects.requireNonNullElseGet(instance, PlayerDataHandler::new);
    }

    public @NotNull Collection<PlayerData> getGamePlayers() {
        return getContainer().getValues();
    }

    public PlayerDataContainer getContainer() {
        if (!LivePlugin.getInstance().getContainerManager().getByType(PlayerDataContainer.class).isPresent()){
            return null;
        }
        return LivePlugin.getInstance().getContainerManager().getByType(PlayerDataContainer.class).get();
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        PlayerData gP = getContainer().loadData(uuid);
        gP.setName(p.getName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        PlayerData gP = getContainer().loadData(uuid);
        getContainer().writeData(uuid, gP);
    }
}
