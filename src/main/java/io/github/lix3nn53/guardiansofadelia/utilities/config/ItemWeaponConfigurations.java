package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.RPGGearType;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponItemTemplate;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemWeaponConfigurations {
    private static final List<FileConfiguration> weaponSetConfigurations = new ArrayList<>();
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config.yml", true);
        createWeaponSetConfigs();
    }

    static void loadConfigs() {
        loadWeaponSetConfigs();
    }

    private static void createConfig(String fileName, boolean isMain) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "weapons";
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
                weaponSetConfigurations.add(yamlConfiguration);
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

    private static void loadWeaponSetConfigs() {
        for (FileConfiguration itemSetConfiguration : weaponSetConfigurations) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String gearTypeStr = itemSetConfiguration.getString("i" + i + ".gearType");
                RPGGearType gearType = RPGGearType.valueOf(gearTypeStr);
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int customModelData = itemSetConfiguration.getInt("i" + i + ".customModelData");
                int level = itemSetConfiguration.getInt("i" + i + ".level");
                int damage = itemSetConfiguration.getInt("i" + i + ".damage");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                WeaponItemTemplate weaponItemTemplate = new WeaponItemTemplate(name, customModelData, level, damage, gearType);

                WeaponManager.add(weaponItemTemplate);
            }
        }
    }
}
