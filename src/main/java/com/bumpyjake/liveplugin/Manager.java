package com.bumpyjake.liveplugin;

import com.bumpyjake.liveplugin.data.playerdata.PlayerData;
import com.bumpyjake.liveplugin.data.playerdata.PlayerDataContainer;
import com.bumpyjake.liveplugin.data.playerdata.PlayerDataHandler;
import com.bumpyjake.liveplugin.data.rank.Rank;
import com.bumpyjake.liveplugin.data.rank.RankManager;
import com.bumpyjake.liveplugin.hooks.LPHook;
import com.marcusslover.plus.lib.sound.Note;
import com.marcusslover.plus.lib.text.Text;
import com.marcusslover.plus.lib.title.Title;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Manager {
    @Getter
    private static final Manager instance = new Manager();

    private Manager() {}

    public void giveRank(Player p, Rank rank) {
        if (LivePlugin.getInstance().getConfig().getBoolean("use-lp")) {
            LPHook.addToGroup(p, rank.getLuckPermsGroup());
        }

        String prefix = LivePlugin.getInstance().getConfig().getString("prefix");

        Text.of(prefix + rank.getOnMessage()).send(p);
        Title.of(rank.getOnTitle(), rank.getOnSubtitle()).send(p, 0, 50, 10);
        Note.of(Sound.ENTITY_EXPERIENCE_ORB_PICKUP).send(p);

        String onMessageAll = rank.getOnMessageAll().replace("%player%", p.getName());
        broadcast(onMessageAll);

        LivePlugin.getInstance().getLogger().info(p.getName() + " is now " + rank.getName());

        PlayerDataContainer playerContainer = PlayerDataHandler.getInstance().getContainer();
        PlayerData playerData = playerContainer.loadData(p.getUniqueId());
        if (rank.getName().equals("live")) {
            playerData.setLive(true);
            playerContainer.writeData(p.getUniqueId(), playerData);
        } else if (rank.getName().equals("rec")) {
            playerData.setRec(true);
            playerContainer.writeData(p.getUniqueId(), playerData);
        }
    }

    public void removeRank(Player p, Rank rank) {
        if (LivePlugin.getInstance().getConfig().getBoolean("use-lp")) {
            LPHook.removeFromGroup(p, rank.getLuckPermsGroup());
        }

        String prefix = LivePlugin.getInstance().getConfig().getString("prefix");

        Text.of(prefix + rank.getOffMessage()).send(p);

        LivePlugin.getInstance().getLogger().info(p.getName() + " is no longer " + rank.getName());

        PlayerDataContainer playerContainer = PlayerDataHandler.getInstance().getContainer();
        PlayerData playerData = playerContainer.loadData(p.getUniqueId());
        if (rank.getName().equals("live")) {
            playerData.setLive(false);
            playerContainer.writeData(p.getUniqueId(), playerData);
        } else if (rank.getName().equals("rec")) {
            playerData.setRec(false);
            playerContainer.writeData(p.getUniqueId(), playerData);
        }
    }

    public Rank getRank(Player p) {
        PlayerDataContainer playerContainer = PlayerDataHandler.getInstance().getContainer();
        PlayerData playerData = playerContainer.loadData(p.getUniqueId());

        if (playerData.isLive()) {
            return RankManager.getInstance().getRank("live");
        } else if (playerData.isRec()) {
            return RankManager.getInstance().getRank("rec");
        }

        return null;
    }

    public boolean isLive(Player p) {
        PlayerDataContainer playerContainer = PlayerDataHandler.getInstance().getContainer();
        PlayerData playerData = playerContainer.loadData(p.getUniqueId());

        return playerData.isLive();
    }

    public boolean isRec(Player p) {
        PlayerDataContainer playerContainer = PlayerDataHandler.getInstance().getContainer();
        PlayerData playerData = playerContainer.loadData(p.getUniqueId());

        return playerData.isRec();
    }

    private void broadcast(@NotNull String message) {
        Text text = Text.of(message);
        String prefix = LivePlugin.getInstance().getConfig().getString("prefix");
        text = Text.of(prefix + text.raw());
        text.send(Bukkit.getConsoleSender());
        for (Player p : Bukkit.getOnlinePlayers()) {
            text.send((CommandSender) p);
        }
    }

}
