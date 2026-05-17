package dev.cryst.clancore.service;

import org.bukkit.entity.Player;
import dev.cryst.clancore.data.ClanData;
import dev.cryst.clancore.economy.impl.VaultEco;
import dev.cryst.clancore.enums.TypeClan;
import dev.cryst.clancore.inv.menu.PveMenu;
import dev.cryst.clancore.inv.menu.PvpMenu;
import dev.cryst.clancore.storage.cache.ClanCache;
import dev.cryst.clancore.storage.database.clans.ClanStorage;

public class ClanService {
    private final ClanStorage storage;
    private final VaultEco vaultEco;

    private final PveMenu pveMenu;
    private final PvpMenu pvpMenu;

    private final ClanCache cache;

    public ClanService(ClanStorage storage, VaultEco vaultEco, PveMenu pveMenu, PvpMenu pvpMenu, ClanCache cache) {
        this.storage = storage;
        this.vaultEco = vaultEco;
        this.pveMenu = pveMenu;
        this.pvpMenu = pvpMenu;
        this.cache = cache;
    }

    public void createClan(Player player, TypeClan type, String clanName) {
        if (!vaultEco.takeMoneyForClanCreation(player)) {
            player.sendMessage("§cНедостаточно средств для создания клана!");
            return;
        }

        ClanData clan = new ClanData(player.getName(), clanName, 0, 0, 1, type);
        storage.addData(clan);
        openClan(player, type);
        cache.put(clan);

        player.sendMessage("§aКлан успешно создан! Тип: " + type);

    }

    private void openClan(Player player, TypeClan clan){
        switch (clan){
            case PVP -> pvpMenu.open(player);
            case PVE -> pveMenu.open(player);
        }
    }
}
