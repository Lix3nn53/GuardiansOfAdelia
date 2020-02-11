package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.list.armors.*;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemTemplate;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.WeaponItemTemplate;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ItemConfigurations {

    private final static HashMap<String, FileConfiguration> armorItemsConfigurations = new HashMap<>();
    private final static HashMap<RPGSlotType, FileConfiguration> passiveItemsConfigurations = new HashMap<>();
    private final static HashMap<RPGClass, FileConfiguration> weaponItemsConfigurations = new HashMap<>();
    private static FileConfiguration shieldsConfig;

    static void createConfigs() {
        createItemArmors();
        createItemPassives();
        createItemWeapons();
        createItemShields();
    }

    static void loadConfigs() {
        loadItemArmors();
        loadItemPassives();
        loadItemWeapons();
        loadItemShields();
    }

    private static void createItemArmors() {
        for (RPGClass rpgClass : RPGClass.values()) {
            for (ArmorType armorType : ArmorType.values()) {
                String fileName = armorType.toString() + ".yml";
                String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "armors" + File.separator + rpgClass.toString();
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
                    armorItemsConfigurations.put(rpgClass.toString() + armorType.toString(), yamlConfiguration);
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void loadItemArmors() {
        for (RPGClass rpgClass : RPGClass.values()) {
            for (ArmorType armorType : ArmorType.values()) {
                FileConfiguration fileConfiguration = armorItemsConfigurations.get(rpgClass.toString() + armorType.toString());
                int itemCount = fileConfiguration.getInt("itemCount");

                for (int i = 1; i <= itemCount; i++) {
                    String nameStr = fileConfiguration.getString("i" + i + ".name");
                    int level = fileConfiguration.getInt("i" + i + ".level");
                    int health = fileConfiguration.getInt("i" + i + ".health");
                    int defense = fileConfiguration.getInt("i" + i + ".defense");
                    int magicDefense = fileConfiguration.getInt("i" + i + ".magicDefense");
                    String materialStr = fileConfiguration.getString("i" + i + ".material");

                    String name = ChatColor.translateAlternateColorCodes('&', nameStr);
                    Material material = Material.valueOf(materialStr);

                    ArmorItemTemplate armorItemTemplate = new ArmorItemTemplate(name, level, health, defense, magicDefense, material);
                    Armors.put(rpgClass, armorType, armorItemTemplate);
                }
            }
        }
    }

    private static void createItemPassives() {
        RPGSlotType[] values = RPGSlotType.values();

        for (int value = 0; value < 5; value++) {
            RPGSlotType rpgSlotType = values[value];
            String fileName = rpgSlotType.toString() + ".yml";
            String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "passives";
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
                passiveItemsConfigurations.put(rpgSlotType, yamlConfiguration);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadItemPassives() {
        RPGSlotType[] values = RPGSlotType.values();

        for (int value = 0; value < 5; value++) {
            RPGSlotType rpgSlotType = values[value];
            FileConfiguration fileConfiguration = passiveItemsConfigurations.get(rpgSlotType);
            int itemCount = fileConfiguration.getInt("itemCount");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = fileConfiguration.getString("i" + i + ".name");
                int customModelData = fileConfiguration.getInt("i" + i + ".customModelData");
                int level = fileConfiguration.getInt("i" + i + ".level");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                PassiveItemTemplate passiveItemTemplate = new PassiveItemTemplate(name, customModelData, level);
                PassiveItemList.put(rpgSlotType, passiveItemTemplate);
            }
        }
    }

    private static void createItemWeapons() {
        for (RPGClass rpgClass : RPGClass.values()) {
            if (rpgClass.equals(RPGClass.NO_CLASS)) continue;

            String fileName = rpgClass.toString() + ".yml";
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
                weaponItemsConfigurations.put(rpgClass, yamlConfiguration);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadItemWeapons() {
        for (RPGClass rpgClass : RPGClass.values()) {
            if (rpgClass.equals(RPGClass.NO_CLASS)) continue;

            FileConfiguration fileConfiguration = weaponItemsConfigurations.get(rpgClass);
            int itemCount = fileConfiguration.getInt("itemCount");

            for (int i = 1; i <= itemCount; i++) {
                String nameStr = fileConfiguration.getString("i" + i + ".name");
                int customModelData = fileConfiguration.getInt("i" + i + ".customModelData");
                int level = fileConfiguration.getInt("i" + i + ".level");
                int damage = fileConfiguration.getInt("i" + i + ".damage");

                String name = ChatColor.translateAlternateColorCodes('&', nameStr);

                WeaponItemTemplate weaponItemTemplate = new WeaponItemTemplate(name, customModelData, level, damage);
                Weapons.add(rpgClass, weaponItemTemplate);
            }
        }
    }

    private static void createItemShields() {
        String fileName = "shields.yml";
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "items" + File.separator + "armors";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        shieldsConfig = new YamlConfiguration();
        try {
            shieldsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadItemShields() {
        int itemCount = shieldsConfig.getInt("itemCount");

        for (int i = 1; i <= itemCount; i++) {
            String nameStr = shieldsConfig.getString("i" + i + ".name");
            int customModelData = shieldsConfig.getInt("i" + i + ".customModelData");
            int level = shieldsConfig.getInt("i" + i + ".level");
            int health = shieldsConfig.getInt("i" + i + ".health");
            int defense = shieldsConfig.getInt("i" + i + ".defense");
            int magicDefense = shieldsConfig.getInt("i" + i + ".magicDefense");
            String rpgClassStr = shieldsConfig.getString("i" + i + ".class");

            String name = ChatColor.translateAlternateColorCodes('&', nameStr);
            RPGClass rpgClass = RPGClass.valueOf(rpgClassStr);

            ShieldItemTemplate shieldItemTemplate = new ShieldItemTemplate(name, level, health, defense, magicDefense, customModelData);
            Shields.add(rpgClass, shieldItemTemplate);
        }
    }
}
