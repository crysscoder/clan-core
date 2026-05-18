package io.github.crysscoder.clancore.command.clan.impl;

import org.bukkit.command.CommandSender;
import io.github.crysscoder.clancore.command.SubCommand;

import java.util.List;

public class MemberSubCommand implements SubCommand {
    @Override
    public void onExecute(CommandSender sender, String[] args) {

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
