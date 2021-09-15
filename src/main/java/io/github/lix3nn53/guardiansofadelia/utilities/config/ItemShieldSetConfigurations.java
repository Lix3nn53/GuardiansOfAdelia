package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.items.list.shields.ShieldSet;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemShieldSetConfigurations {
    private static final List<FileConfiguration> configurationList = new ArrayList<>();
    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "shieldSets";

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
        createSetConfigs();
    }

    static void loadConfigs() {
        loadShieldConfigs();
    }

    private static void createSetConfigs() {
        List<String> itemSetList = fileConfiguration.getStringList("itemSetList");

        for (String fileName : itemSetList) {
            YamlConfiguration config = ConfigurationUtils.createConfig(filePath, fileName + ".yml");
            configurationList.add(config);
        }
    }

    private static void loadShieldConfigs() {
        for (FileConfiguration itemSetConfiguration : configurationList) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int customModelData = itemSetConfiguration.getInt("i" + i + ".customModelData");
                int level = itemSetConfiguration.getInt("i" + i + ".level");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                ShieldSet shieldSet = new ShieldSet(name, level, customModelData);

                ShieldManager.add(shieldSet);
            }
        }
    }
}
