package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ShieldSet;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemShieldSetConfigurations {
    private static final List<FileConfiguration> shieldConfigurations = new ArrayList<>();
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config.yml", true);
        createWeaponSetConfigs();
    }

    static void loadConfigs() {
        loadShieldConfigs();
    }

    private static void createConfig(String fileName, boolean isMain) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "shieldSets";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(customConfigFile);
            if (isMain) {
                fileConfiguration = yamlConfiguration;
            } else {
                shieldConfigurations.add(yamlConfiguration);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void createWeaponSetConfigs() {
        List<String> itemSetList = fileConfiguration.getStringList("itemSetList");

        for (String fileName : itemSetList) {
            createConfig(fileName + ".yml", false);
        }
    }

    private static void loadShieldConfigs() {
        for (FileConfiguration itemSetConfiguration : shieldConfigurations) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int customModelData = itemSetConfiguration.getInt("i" + i + ".customModelData");
                int level = itemSetConfiguration.getInt("i" + i + ".level");
                int health = itemSetConfiguration.getInt("i" + i + ".health");
                int defense = itemSetConfiguration.getInt("i" + i + ".defense");
                int magicDefense = itemSetConfiguration.getInt("i" + i + ".magicDefense");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                ShieldSet shieldSet = new ShieldSet(name, customModelData, level, health, defense, magicDefense);

                ShieldManager.add(shieldSet);
            }
        }
    }
}
