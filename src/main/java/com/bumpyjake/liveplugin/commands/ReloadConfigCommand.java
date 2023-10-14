package com.bumpyjake.liveplugin.commands;

import com.bumpyjake.liveplugin.LivePlugin;
import com.bumpyjake.liveplugin.data.rank.RankManager;
import com.marcusslover.plus.lib.command.Command;
import com.marcusslover.plus.lib.command.CommandContext;
import com.marcusslover.plus.lib.command.ICommand;
import com.marcusslover.plus.lib.text.Text;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Command(name="livereload")
public class ReloadConfigCommand implements ICommand {
    @Override
    public boolean execute(@NotNull CommandContext cmd) {
        CommandSender sender = cmd.sender();

        if (sender.hasPermission("live.admin")) {
            LivePlugin.getInstance().reloadConfig();
            RankManager.getInstance().reset();
            String prefix = LivePlugin.getInstance().getConfig().getString("prefix");
            Text.of(prefix + "&aReloaded configuration").send(sender);
            LivePlugin.getInstance().getLogger().info("Reloaded live configuration");
        }


        return true;
    }
}
