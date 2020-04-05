package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorSet;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemArmorSetConfigurations {
    private static final List<FileConfiguration> armorSetConfigurations = new ArrayList<>();
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config.yml", true);
        createArmorSetConfigs();
    }

    static void loadConfigs() {
        loadArmorSetConfigs();
    }

    private static void createConfig(String fileName, boolean isMain) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "armorSets";
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
                armorSetConfigurations.add(yamlConfiguration);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void createArmorSetConfigs() {
        List<String> itemSetList = fileConfiguration.getStringList("itemSetList");

        for (String fileName : itemSetList) {
            createConfig(fileName + ".yml", false);
        }
    }

    private static void loadArmorSetConfigs() {
        for (FileConfiguration itemSetConfiguration : armorSetConfigurations) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int baseLevelReq = itemSetConfiguration.getInt("i" + i + ".baseLevelReq");
                int health = itemSetConfiguration.getInt("i" + i + ".health");
                int defense = itemSetConfiguration.getInt("i" + i + ".defense");
                int magicDefense = itemSetConfiguration.getInt("i" + i + ".magicDefense");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                ArmorSet armorSet = new ArmorSet(name, baseLevelReq, health, defense, magicDefense);

                ArmorManager.add(armorSet);
            }
        }
    }
}
