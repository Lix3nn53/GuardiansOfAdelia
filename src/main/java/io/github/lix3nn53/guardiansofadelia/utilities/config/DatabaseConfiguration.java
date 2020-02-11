package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.ConnectionPool;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DatabaseConfiguration {

    private static FileConfiguration databaseConfig;

    static void createConfigs() {
        createDatabaseConfig();
    }

    static void loadConfigs() {
        loadDatabaseConfig();
    }

    private static void createDatabaseConfig() {
        File customConfigFile = new File(ConfigManager.DATA_FOLDER, "database.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("database.yml", false);
        }

        databaseConfig = new YamlConfiguration();
        try {
            databaseConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
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
