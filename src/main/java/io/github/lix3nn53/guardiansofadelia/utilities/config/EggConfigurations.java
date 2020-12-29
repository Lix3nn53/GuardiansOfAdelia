package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.Eggs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class EggConfigurations {

    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "eggs";

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
    }

    static void loadConfigs() {
        loadQuestLineConfigs();
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
