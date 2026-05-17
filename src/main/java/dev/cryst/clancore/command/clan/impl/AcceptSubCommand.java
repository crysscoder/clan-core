package dev.cryst.clancore.command.clan.impl;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import dev.cryst.clancore.command.SubCommand;
import dev.cryst.clancore.data.InviteData;
import dev.cryst.clancore.manager.InviteManager;
import dev.cryst.clancore.storage.database.clans.ClanRepository;

import java.util.List;

@AllArgsConstructor
public class AcceptSubCommand implements SubCommand {
    private final InviteManager inviteManager;
    private final ClanRepository clanRepository;

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        InviteData invite = inviteManager.getInvite(player);
        if (invite == null) {
            player.sendMessage("§cУ вас нет активных приглашений.");
            return;
        }
        inviteManager.removeInvite(player);

        Player inviter = Bukkit.getPlayer(invite.getInviter());
        if (inviter != null) {
            inviter.sendMessage("§aИгрок §e" + player.getName() + " §aпринял приглашение в ваш клан!");
        }

        player.sendMessage("§aВы присоединились к клану §e" + invite.getClanName() + "§a!");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
