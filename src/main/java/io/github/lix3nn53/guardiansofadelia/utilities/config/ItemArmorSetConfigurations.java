package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSet;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemArmorSetConfigurations {
    private static final List<FileConfiguration> configurationList = new ArrayList<>();
    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "armorSets";

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
        createSetConfigs();
    }

    static void loadConfigs() {
        loadArmorSetConfigs();
    }

    private static void createSetConfigs() {
        List<String> itemSetList = fileConfiguration.getStringList("itemSetList");

        for (String fileName : itemSetList) {
            YamlConfiguration config = ConfigurationUtils.createConfig(filePath, fileName + ".yml");
            configurationList.add(config);
        }
    }

    private static void loadArmorSetConfigs() {
        for (FileConfiguration itemSetConfiguration : configurationList) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int baseLevelReq = itemSetConfiguration.getInt("i" + i + ".baseLevelReq");
                int health = itemSetConfiguration.getInt("i" + i + ".health");
                int defense = itemSetConfiguration.getInt("i" + i + ".defense");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                ArmorSet armorSet = new ArmorSet(name, baseLevelReq, health, defense);

                ArmorManager.add(armorSet);
            }
        }
    }
}
