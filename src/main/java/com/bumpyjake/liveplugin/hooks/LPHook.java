package com.bumpyjake.liveplugin.hooks;

import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.bumpyjake.liveplugin.LivePlugin.luckPerms;

public class LPHook {

    public static void addToGroup(Player p, String group) {
        String groupName = ("group." + group);

        User user = luckPerms.getUserManager().getUser(p.getUniqueId());
        Node node = Node.builder(groupName).build();
        DataMutateResult result = user.data().add(node);
        luckPerms.getUserManager().saveUser(user);
    }

    public static void removeFromGroup(Player p, String group) {
        String groupName = ("group." + group);

        User user = luckPerms.getUserManager().getUser(p.getUniqueId());
        Node node = Node.builder(groupName).build();
        DataMutateResult result = user.data().remove(node);
        luckPerms.getUserManager().saveUser(user);
    }

    public static @NotNull String getPlayerGroup(@NotNull Player player) {
        User user = luckPerms.getUserManager().getUser(player.getUniqueId());
        if (user != null) return user.getPrimaryGroup();
        return "default";
    }

}
