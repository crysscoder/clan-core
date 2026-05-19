package io.github.crysscoder.clancore.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.crysscoder.clancore.manager.config.ConfigDBManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlConnectionManager {
    private final JavaPlugin plugin;
    private HikariDataSource dataSource;

    public MySqlConnectionManager(JavaPlugin plugin, MySqlConnectionManager connectionManager) {
        this.plugin = plugin;
        setup();
        plugin.getLogger().info("MySQL connected");
    }

    public void setup() {
        final String url = "jdbc:mysql://" + ConfigDBManager.MySQL.HOST + ":" +
                ConfigDBManager.MySQL.PORT + "/" + ConfigDBManager.MySQL.DATABASE;

        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(ConfigDBManager.MySQL.USER);
        config.setPassword(ConfigDBManager.MySQL.PASSWORD);
        config.setMaximumPoolSize(3);
        config.setMinimumIdle(1);

        dataSource = new HikariDataSource(config);
        initDatabase();
    }

    public void initDatabase() {
        final String clanTable = """
                CREATE TABLE IF NOT EXISTS clans (
                id int AUTO_INCREMENT PRIMARY KEY,
                owner VARCHAR(36) UNIQUE NOT NULL,
                clanName VARCHAR(32) NOT NULL,
                balance BIGINT DEFAULT 0,
                point INT DEFAULT 0,
                level INT DEFAULT 1,
                typeClan VARCHAR(16) NOT NULL)""";

        final String memberTable = """
                CREATE TABLE IF NOT EXISTS members(
                member VARCHAR(36) UNIQUE NOT NULL,
                clan_id int NOT NULL,
                FOREIGN KEY (clan_id) REFERENCES clans(id) ON DELETE CASCADE)""";

        try (final Connection conn = getConnection();
             final Statement st = conn.createStatement()) {
            st.executeUpdate(clanTable);
            st.executeUpdate(memberTable);
        } catch (SQLException e) {
            throw new RuntimeException("Database schema was not created", e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Database is not ready");
        }
        return dataSource.getConnection();
    }

    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
