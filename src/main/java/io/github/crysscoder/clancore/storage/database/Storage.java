package io.github.crysscoder.clancore.storage.database;

import org.bukkit.entity.Player;
import io.github.crysscoder.clancore.data.ClanData;

import java.util.concurrent.CompletableFuture;

public interface Storage {
    CompletableFuture<ClanData> getClan(Player player);
    void addData(ClanData clan);
    void removeData(int id);
    void updateData(ClanData clan);
}
