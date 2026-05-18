package io.github.crysscoder.clancore.service;

import org.bukkit.entity.Player;
import io.github.crysscoder.clancore.data.ClanData;
import io.github.crysscoder.clancore.economy.impl.VaultEco;
import io.github.crysscoder.clancore.enums.TypeClan;
import io.github.crysscoder.clancore.inv.menu.PveMenu;
import io.github.crysscoder.clancore.inv.menu.PvpMenu;
import io.github.crysscoder.clancore.storage.cache.ClanCache;
import io.github.crysscoder.clancore.storage.database.clans.ClanStorage;

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
