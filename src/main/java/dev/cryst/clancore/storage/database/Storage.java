package dev.cryst.clancore.storage.database;

import org.bukkit.entity.Player;
import dev.cryst.clancore.data.ClanData;

import java.util.concurrent.CompletableFuture;

public interface Storage {
    CompletableFuture<ClanData> getClan(Player player);
    void addData(ClanData clan);
    void removeData(int id);
    void updateData(ClanData clan);
}
