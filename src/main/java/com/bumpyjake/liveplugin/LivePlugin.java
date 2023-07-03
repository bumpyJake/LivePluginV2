package com.bumpyjake.liveplugin;

import com.bumpyjake.liveplugin.commands.LiveCommand;
import com.bumpyjake.liveplugin.commands.RecCommand;
import com.bumpyjake.liveplugin.commands.ReloadConfigCommand;
import com.bumpyjake.liveplugin.data.playerdata.PlayerDataContainer;
import com.bumpyjake.liveplugin.data.playerdata.PlayerDataHandler;
import com.bumpyjake.liveplugin.data.rank.RankManager;
import com.bumpyjake.liveplugin.hooks.LivePlaceholder;
import com.marcusslover.plus.lib.command.CommandManager;
import com.marcusslover.plus.lib.container.ContainerManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class LivePlugin extends JavaPlugin {
    private CommandManager commandManager;
    private ContainerManager containerManager;
    private LivePlaceholder placeHolder;
    public static LuckPerms luckPerms;

    public static LivePlugin getInstance() {
        return LivePlugin.getPlugin(LivePlugin.class);
    }

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        RankManager.getInstance().init();

        commandManager = CommandManager.get(this);
        commandManager.register(new LiveCommand());
        commandManager.register(new RecCommand());
        commandManager.register(new ReloadConfigCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerDataHandler(), this);

        containerManager = new ContainerManager();
        containerManager.register("players", new PlayerDataContainer());
        containerManager.init(this);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeHolder = new LivePlaceholder();
            placeHolder.register();
        }
    }

    @Override
    public void onDisable() {
        commandManager.clearCommands();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeHolder.unregister();
        }

        RankManager.getInstance().clear();
    }

    public ContainerManager getContainerManager() {
        return containerManager;
    }
}
