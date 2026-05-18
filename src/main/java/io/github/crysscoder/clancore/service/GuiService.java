package io.github.crysscoder.clancore.service;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import io.github.crysscoder.clancore.inv.ClanMenu;

@RequiredArgsConstructor
public class GuiService implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event){
        if(event.getInventory().getHolder(false) instanceof ClanMenu clanMenu){
            clanMenu.onClick(event);
        }
    }

}
