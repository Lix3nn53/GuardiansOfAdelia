package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponSet;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemWeaponSetConfigurations {
    private static final List<FileConfiguration> configurationList = new ArrayList<>();
    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "weaponSets";

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
        createSetConfigs();
    }

    static void loadConfigs() {
        loadWeaponSetConfigs();
    }

    private static void createSetConfigs() {
        List<String> itemSetList = fileConfiguration.getStringList("itemSetList");

        for (String fileName : itemSetList) {
            YamlConfiguration config = ConfigurationUtils.createConfig(filePath, fileName + ".yml");
            configurationList.add(config);
        }
    }

    private static void loadWeaponSetConfigs() {
        for (FileConfiguration itemSetConfiguration : configurationList) {
            int itemCount = itemSetConfiguration.getInt("count");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = itemSetConfiguration.getString("i" + i + ".name");
                int customModelData = itemSetConfiguration.getInt("i" + i + ".customModelData");
                int level = itemSetConfiguration.getInt("i" + i + ".level");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                WeaponSet weaponSet = new WeaponSet(name, level, customModelData);

                WeaponManager.add(weaponSet);
            }
        }
    }
}
