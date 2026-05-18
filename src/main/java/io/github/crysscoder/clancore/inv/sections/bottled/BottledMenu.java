package io.github.crysscoder.clancore.inv.sections.bottled;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import io.github.crysscoder.clancore.inv.ClanMenu;
import io.github.crysscoder.clancore.manager.config.ConfigManager;
import io.github.crysscoder.clancore.util.ItemUtil;

import java.util.List;

public class BottledMenu implements ClanMenu {
    private final Inventory inventory;

    public BottledMenu(){
        this.inventory = Bukkit.createInventory(this, 54, "Стакнуть зелья");
        build();
    }
    @Override
    public void open(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public void build() {
        for(ConfigManager.GuiItem item : ConfigManager.GUI.BOTTLED.item){
            final Material material = item.getMaterial();
            final String name = item.getName();
            final List<String> lore = item.getLore();
            final int slot = item.getSlot();

            ItemStack result = ItemUtil.ItemStack(material, name, lore);
            inventory.setItem(slot, result);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
