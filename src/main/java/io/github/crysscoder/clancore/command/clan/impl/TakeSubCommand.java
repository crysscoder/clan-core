package io.github.crysscoder.clancore.command.clan.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.github.crysscoder.clancore.command.SubCommand;
import io.github.crysscoder.clancore.inv.choose.StartMenu;

import java.util.List;

public class TakeSubCommand implements SubCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
