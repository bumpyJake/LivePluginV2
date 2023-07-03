package com.bumpyjake.liveplugin.commands;

import com.bumpyjake.liveplugin.Manager;
import com.bumpyjake.liveplugin.data.rank.RankManager;
import com.marcusslover.plus.lib.command.Command;
import com.marcusslover.plus.lib.command.CommandContext;
import com.marcusslover.plus.lib.command.ICommand;
import org.jetbrains.annotations.NotNull;

@Command(name="recording", aliases = "rec")
public class RecCommand implements ICommand {
    @Override
    public boolean execute(@NotNull CommandContext cmd) {

        cmd.asPlayer(p -> {
            Manager manager = Manager.getInstance();
            RankManager rankManager = RankManager.getInstance();

            if (manager.isRec(p)) {
                manager.removeRank(p, rankManager.getRank("rec"));
            } else {
                manager.giveRank(p, rankManager.getRank("rec"));
            }

            if (manager.isLive(p)) {
                manager.removeRank(p, rankManager.getRank("live"));
            }
        });

        return true;
    }
}
