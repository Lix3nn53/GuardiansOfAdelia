package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveSet;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemPassiveSetConfigurations {
    private static final List<FileConfiguration> passiveSetConfigurations = new ArrayList<>();
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config.yml", true);
        createPassiveSetConfigs();
    }

    static void loadConfigs() {
        loadPassiveSetConfigs();
    }

    private static void createConfig(String fileName, boolean isMain) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "passiveSets";
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
                passiveSetConfigurations.add(yamlConfiguration);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void createPassiveSetConfigs() {
        List<String> itemSetList = fileConfiguration.getStringList("itemSetList");

        for (String fileName : itemSetList) {
            createConfig(fileName + ".yml", false);
        }
    }

    private static void loadPassiveSetConfigs() {
        for (FileConfiguration itemSetConfiguration : passiveSetConfigurations) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String gearTypeStr = itemSetConfiguration.getString("i" + i + ".gearType");
                RPGGearType gearType = RPGGearType.valueOf(gearTypeStr);
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int baseCustomModelData = itemSetConfiguration.getInt("i" + i + ".baseCustomModelData");
                int baseLevel = itemSetConfiguration.getInt("i" + i + ".baseLevel");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                PassiveSet passiveSet = new PassiveSet(name, baseCustomModelData, baseLevel, gearType);

                PassiveManager.add(passiveSet);
            }
        }
    }
}
