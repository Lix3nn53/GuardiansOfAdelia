package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.database.ConnectionPool;
import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseConfiguration {

    private static FileConfiguration databaseConfig;
    private static final String filePath = ConfigManager.DATA_FOLDER.toString();

    static void createConfigs() {
        databaseConfig = ConfigurationUtils.createConfig(filePath, "database.yml");
    }

    static void loadConfigs() {
        loadDatabaseConfig();
    }

    private static void loadDatabaseConfig() {
        String hostname = databaseConfig.getString("hostname");
        String port = databaseConfig.getString("port");
        String database = databaseConfig.getString("database");
        String username = databaseConfig.getString("username");
        String password = databaseConfig.getString("password");
        int minimumConnections = databaseConfig.getInt("minimumConnections");
        int maximumConnections = databaseConfig.getInt("maximumConnections");
        int connectionTimeout = databaseConfig.getInt("connectionTimeout");
        String testQuery = databaseConfig.getString("testQuery");

        ConnectionPool.init(hostname, port, database, username, password, minimumConnections, maximumConnections, connectionTimeout, testQuery);
    }
}
