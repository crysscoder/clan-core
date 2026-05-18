package io.github.crysscoder.clancore.command.clan.impl;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import io.github.crysscoder.clancore.command.SubCommand;
import io.github.crysscoder.clancore.data.ClanData;
import io.github.crysscoder.clancore.manager.ChatInputManager;
import io.github.crysscoder.clancore.manager.config.ConfigManager;
import io.github.crysscoder.clancore.storage.cache.ClanCache;
import io.github.crysscoder.clancore.storage.database.clans.ClanStorage;

import java.util.List;

public class DeleteSubCommand implements SubCommand {
    private final ChatInputManager chatInputManager;
    private final ClanStorage clanStorage;
    private final ClanCache clanCache;


    public DeleteSubCommand(ChatInputManager chatInputManager, ClanStorage clanStorage, ClanCache clanCache) {
        this.chatInputManager = chatInputManager;
        this.clanStorage = clanStorage;
        this.clanCache = clanCache;

    }


    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        final ClanData clanData = clanCache.get(player.getName());
        if(clanData == null) {
            player.sendMessage("Вы не состоите в клане");
            return;
        }

        chatInputManager.waitForInput(player, ConfigManager.MESSAGE.deleteClan, clanDelete -> {
            switch (clanDelete){
                case "ПОДТВЕРДИТЬ" -> deleteClan(player, clanData);
                case "ОТМЕНА" -> chatInputManager.cancelPrompt(player);
            }

        });
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }

    private void deleteClan(Player player, ClanData clanData){
        clanStorage.removeData(clanData.getId());
        clanCache.remove(clanData.getId());
        player.sendMessage("Клан успешно удалён");
    }

}
