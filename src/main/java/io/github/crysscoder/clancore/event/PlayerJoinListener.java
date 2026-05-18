package io.github.crysscoder.clancore.event;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import io.github.crysscoder.clancore.data.ClanData;
import io.github.crysscoder.clancore.storage.cache.ClanCache;
import io.github.crysscoder.clancore.storage.database.clans.ClanRepository;

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
