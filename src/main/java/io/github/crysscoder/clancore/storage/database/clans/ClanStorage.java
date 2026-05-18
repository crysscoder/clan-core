package io.github.crysscoder.clancore.storage.database.clans;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.crysscoder.clancore.data.ClanData;
import io.github.crysscoder.clancore.storage.cache.ClanCache;
import io.github.crysscoder.clancore.storage.database.MySqlConnectionManager;
import io.github.crysscoder.clancore.storage.database.Storage;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClanStorage implements Storage {
    private final ClanCache clanCache = new ClanCache();
    private final ClanRepository clanRepository;
    private final JavaPlugin plugin;
    private final MySqlConnectionManager manager;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public ClanStorage(JavaPlugin plugin, MySqlConnectionManager manager) {
        this.plugin = plugin;
        this.manager = manager;
        this.clanRepository = new ClanRepository(manager);
    }

    @Override
    public CompletableFuture<ClanData> getClan(Player player) {
        ClanData cached = clanCache.get(player.getName());
        if (cached != null) {
             Bukkit.getLogger().info("Клан найден в БД: " + cached.getOwner() + ", id=" + cached.getId());
            return CompletableFuture.completedFuture(cached);
        }

        return CompletableFuture.supplyAsync(() -> {
            ClanData clan = clanRepository.getByOwner(player.getName());
            if (clan != null) clanCache.put(clan);
            return clan;
        }, executor);
    }

    @Override
    public void addData(ClanData clanData) {
        CompletableFuture.runAsync(() -> {
            clanRepository.insert(clanData);
            clanCache.put(clanData);
        }, executor);
    }

    @Override
    public void removeData(int id) {
        CompletableFuture.runAsync(() -> {
            clanRepository.delete(id);
            clanCache.remove(id);
        }, executor);
    }

    @Override
    public void updateData(ClanData clan) {
        CompletableFuture.runAsync(() -> {
            clanRepository.update(clan);
            clanCache.put(clan);
        }, executor);
    }

}