package dev.cryst.clancore.event;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import dev.cryst.clancore.data.ClanData;
import dev.cryst.clancore.storage.cache.ClanCache;
import dev.cryst.clancore.storage.database.clans.ClanRepository;

@AllArgsConstructor
public class PlayerJoinListener implements Listener {
    private final ClanRepository clanRepository;
    private final ClanCache cache;
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final Player player = event.getPlayer();
        
        final ClanData clanData =  clanRepository.getByOwner(player.getName());
        if(clanData != null){
            cache.put(clanData);
        }
    }
}
