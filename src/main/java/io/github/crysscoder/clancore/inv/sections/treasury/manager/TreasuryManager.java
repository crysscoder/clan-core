package io.github.crysscoder.clancore.inv.sections.treasury.manager;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.crysscoder.clancore.data.ClanData;
import io.github.crysscoder.clancore.economy.impl.VaultEco;
import io.github.crysscoder.clancore.enums.InvestmentType;
import io.github.crysscoder.clancore.storage.cache.ClanCache;
import io.github.crysscoder.clancore.storage.database.clans.ClanStorage;

@AllArgsConstructor
public class TreasuryManager {

    private final VaultEco vaultEco;
    private final ClanStorage clanStorage;
    private final JavaPlugin plugin;
    private final ClanCache clanCache;


    public void takeInvestment(Player player, InvestmentType type) {
        int amount = type.getAmount();

        if (vaultEco.getBalance(player) < amount) {
            player.sendMessage("§cУ вас недостаточно денег!");
            return;
        }

        ClanData clanData = clanCache.get(player.getName());
        if (clanData != null) {
            processInvestment(player, clanData, amount);
        } else {
            clanStorage.getClan(player).thenAccept(loadedClan -> {
                if (loadedClan != null) {
                    clanCache.put(loadedClan);
                    processInvestment(player, loadedClan, amount);
                } else {
                    player.sendMessage("§cВаш клан не найден!");
                }
            });
        }
    }

        public void takeBalanceTreasury(Player player, InvestmentType type){
        int amount = type.getAmount();

        ClanData clanData = clanCache.get(player.getName());
        if(clanData != null){
            processTakeBalanceTreasury(player, clanData, amount);
        } else {
            clanStorage.getClan(player).thenAccept(loadedClan -> {
                if (loadedClan != null) {
                    clanCache.put(loadedClan);
                    processTakeBalanceTreasury(player, loadedClan, amount);
                } else {
                    player.sendMessage("§cВаш клан не найден!");
                }
            });
        }

    }

    private void processTakeBalanceTreasury(Player player, ClanData clanData, int amount){
        long balance = clanData.getBalance();

        if(balance < amount){
            player.sendMessage("§cВ казне недостаточно монеток");
            return;
        }

        clanData.setBalance(balance - amount);
        vaultEco.addBalanse(player, amount);
        clanCache.put(clanData);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            clanStorage.updateData(clanData);
        });

        player.sendMessage("§aВы сняли с казны: " + amount + " монеток.");
    }


    private void processInvestment(Player player, ClanData clanData, int amount) {
        if (!vaultEco.withdraw(player, amount)) {
            player.sendMessage("§cОшибка снятия денег!");
            return;
        }

        clanData.setBalance(clanData.getBalance() + amount);
        clanCache.put(clanData);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            clanStorage.updateData(clanData);
        });

        player.sendMessage("§aВы вложили " + amount + " в казну!");
    }

}


