package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveSet;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemPassiveSetConfigurations {
    private static final List<FileConfiguration> configurationList = new ArrayList<>();
    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "passiveSets";

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
        createSetConfigs();
    }

    static void loadConfigs() {
        loadPassiveSetConfigs();
    }

    private static void createSetConfigs() {
        List<String> itemSetList = fileConfiguration.getStringList("itemSetList");

        for (String fileName : itemSetList) {
            YamlConfiguration config = ConfigurationUtils.createConfig(filePath, fileName + ".yml");
            configurationList.add(config);
        }
    }

    private static void loadPassiveSetConfigs() {
        for (FileConfiguration itemSetConfiguration : configurationList) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int baseCustomModelData = itemSetConfiguration.getInt("i" + i + ".baseCustomModelData");
                int baseLevel = itemSetConfiguration.getInt("i" + i + ".baseLevel");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                PassiveSet passiveSet = new PassiveSet(name, baseCustomModelData, baseLevel);

                PassiveManager.add(passiveSet);
            }
        }
    }
}
