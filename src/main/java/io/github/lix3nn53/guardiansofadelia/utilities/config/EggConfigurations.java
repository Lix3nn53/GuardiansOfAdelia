package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.Eggs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class EggConfigurations {

    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config.yml");
    }

    static void loadConfigs() {
        loadQuestLineConfigs();
    }

    private static void createConfig(String fileName) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "eggs";
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
            fileConfiguration = yamlConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadQuestLineConfigs() {
        int petCount = ConfigurationUtils.getChildComponentCount(fileConfiguration, "egg");

        for (int i = 1; i <= petCount; i++) {
            ConfigurationSection section = fileConfiguration.getConfigurationSection("egg" + i);
            String key = section.getString("mythicMobCode");
            int customModelData = section.getInt("customModelData");
            String itemTierStr = section.getString("itemTier");
            ItemTier itemTier = ItemTier.valueOf(itemTierStr);

            Eggs.add(key, customModelData, itemTier);
        }
    }
}
