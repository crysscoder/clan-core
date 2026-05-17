package dev.cryst.clancore.command.clan.impl;

import org.bukkit.command.CommandSender;
import dev.cryst.clancore.command.SubCommand;

import java.util.List;

public class ChatSubCommand implements SubCommand {
    @Override
    public void onExecute(CommandSender sender, String[] args) {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
