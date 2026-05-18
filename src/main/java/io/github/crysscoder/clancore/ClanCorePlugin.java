package io.github.crysscoder.clancore;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.crysscoder.clancore.command.clan.ClanCommand;
import io.github.crysscoder.clancore.economy.impl.VaultEco;
import io.github.crysscoder.clancore.event.PlayerJoinListener;
import io.github.crysscoder.clancore.event.PlayerQuitListener;
import io.github.crysscoder.clancore.inv.choose.ChooseMenu;
import io.github.crysscoder.clancore.inv.choose.StartMenu;
import io.github.crysscoder.clancore.inv.menu.PveMenu;
import io.github.crysscoder.clancore.inv.menu.PvpMenu;
import io.github.crysscoder.clancore.inv.sections.bottled.BottledMenu;
import io.github.crysscoder.clancore.inv.sections.effects.UniqueEffectsMenu;
import io.github.crysscoder.clancore.inv.sections.treasury.TreasuryMenu;
import io.github.crysscoder.clancore.inv.sections.treasury.manager.TreasuryManager;
import io.github.crysscoder.clancore.inv.sections.update.UpdateMenu;
import io.github.crysscoder.clancore.manager.ChatInputManager;
import io.github.crysscoder.clancore.manager.InviteManager;
import io.github.crysscoder.clancore.manager.config.ConfigDBManager;
import io.github.crysscoder.clancore.manager.config.ConfigManager;
import io.github.crysscoder.clancore.service.ClanService;
import io.github.crysscoder.clancore.service.GuiService;
import io.github.crysscoder.clancore.storage.cache.ClanCache;
import io.github.crysscoder.clancore.storage.database.clans.ClanRepository;
import io.github.crysscoder.clancore.storage.database.MySqlConnectionManager;
import io.github.crysscoder.clancore.storage.database.clans.ClanStorage;

public final class ClanCorePlugin extends JavaPlugin {

    @Getter
    private Economy economy;
    private ClanStorage clanStorage;
    private MySqlConnectionManager manager;


    @Override
    public void onEnable() {

        saveDefaultConfig();
        reloadConfig();

         if (setupEconomy()) {
             getLogger().info("Economy init");
         }else {
             getLogger().warning("❌ Экономика не инициализирована. Некоторые функции будут отключены.");

         }
        final var configManager = new ConfigManager(this);
        final var configDBManager = new ConfigDBManager(this);

        configManager.load();
        configDBManager.load();

        manager = new MySqlConnectionManager(this, manager);
        clanStorage = new ClanStorage(this, manager);
        final var clanRepository = new ClanRepository(manager);


        final var vaultEco = new VaultEco(economy);
        final var clanCache = new ClanCache();


        final var treasuryManager = new TreasuryManager(vaultEco, clanStorage, this, clanCache);

        final var uniqueEffectsMenu = new UniqueEffectsMenu();
        final var treasuryMenu = new TreasuryMenu(treasuryManager);
        final var updateMenu = new UpdateMenu();
        final var bottledMenu = new BottledMenu();


        final var pvpMenu = new PvpMenu(updateMenu, uniqueEffectsMenu, treasuryMenu, bottledMenu);
        final var pveMenu = new PveMenu();
        final var chatInputManager = new ChatInputManager(this);
        final var clanService = new ClanService(clanStorage, vaultEco, pveMenu, pvpMenu, clanCache);
        final var chooseMenu = new ChooseMenu(vaultEco, clanService, this, chatInputManager);
        final var startMenu = new StartMenu(chooseMenu);
        final var inviteManager = new InviteManager();

        getCommand("clan").setExecutor(new ClanCommand(this, clanCache, clanStorage, startMenu, chatInputManager, clanRepository, inviteManager, pveMenu, pvpMenu));
        getServer().getPluginManager().registerEvents(new GuiService(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(clanCache), this);

        getServer().getPluginManager().registerEvents(chatInputManager, this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(clanRepository, clanCache), this);

    }

    @Override
    public void onDisable() {
        if (clanStorage != null) {
            manager.shutdown();
        }
    }



    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> provider = getServer().getServicesManager().getRegistration(Economy.class);
        if (provider == null) {
            return false;
        }
        economy = provider.getProvider();
        return economy != null;
    }
}

