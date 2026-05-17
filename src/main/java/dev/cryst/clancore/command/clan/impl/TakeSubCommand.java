package dev.cryst.clancore.command.clan.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.cryst.clancore.command.SubCommand;
import dev.cryst.clancore.inv.choose.StartMenu;

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
