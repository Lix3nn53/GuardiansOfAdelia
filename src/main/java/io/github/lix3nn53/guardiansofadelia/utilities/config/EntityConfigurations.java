package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.list.MonsterItem;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntity;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityDisguise;
import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityConfigurations {

    private final static HashMap<String, FileConfiguration> fileNameToConfiguration = new HashMap<>();

    static void createConfigs() {
        createConfig("bosses.yml");
        createConfig("friendly.yml");
        createConfig("monsters.yml");
    }

    static void loadConfigs() {
        loadEntities("bosses.yml");
        loadEntities("friendly.yml");
        loadEntities("monsters.yml");
    }

    private static void createConfig(String fileName) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "entities";
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
            fileNameToConfiguration.put(fileName, yamlConfiguration);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadEntities(String fileName) {
        FileConfiguration fileConfiguration = fileNameToConfiguration.get(fileName);
        int count = fileConfiguration.getInt("count");

        for (int i = 1; i <= count; i++) {
            String adeliaEntityKey = fileConfiguration.getString("i" + i + ".key");
            String nameStr = fileConfiguration.getString("i" + i + ".name");

            String typeStr = fileConfiguration.getString("i" + i + ".type");
            EntityType entityType = EntityType.valueOf(typeStr);

            String mainhandStr = fileConfiguration.getString("i" + i + ".mainhand");
            MonsterItem mainHand = null;
            if (mainhandStr != null) {
                mainHand = MonsterItem.valueOf(mainhandStr);
            }

            String offhandStr = fileConfiguration.getString("i" + i + ".offHand");
            MonsterItem offHand = null;
            if (offhandStr != null) {
                offHand = MonsterItem.valueOf(offhandStr);
            }

            String helmetStr = fileConfiguration.getString("i" + i + ".helmet");
            MonsterItem helmet = null;
            if (helmetStr != null) {
                helmet = MonsterItem.valueOf(helmetStr);
            }

            String chestplateStr = fileConfiguration.getString("i" + i + ".chestplate");
            MonsterItem chestplate = null;
            if (chestplateStr != null) {
                chestplate = MonsterItem.valueOf(chestplateStr);
            }

            String leggingsStr = fileConfiguration.getString("i" + i + ".leggings");
            MonsterItem leggings = null;
            if (leggingsStr != null) {
                leggings = MonsterItem.valueOf(leggingsStr);
            }

            String bootsStr = fileConfiguration.getString("i" + i + ".boots");
            MonsterItem boots = null;
            if (bootsStr != null) {
                boots = MonsterItem.valueOf(bootsStr);
            }

            int damage = fileConfiguration.getInt("i" + i + ".damage");
            int maxHealth = fileConfiguration.getInt("i" + i + ".maxHealth");
            int experience = fileConfiguration.getInt("i" + i + ".experience");
            int dropTableNumber = fileConfiguration.getInt("i" + i + ".dropTableNumber");
            double movementSpeed = fileConfiguration.getDouble("i" + i + ".movementSpeed");
            int size = fileConfiguration.getInt("i" + i + ".size");
            boolean isBaby = fileConfiguration.getBoolean("i" + i + ".isBaby");
            boolean isVillagerProfessionRandom = fileConfiguration.getBoolean("i" + i + ".isVillagerProfessionRandom");

            String villagerProfessionStr = fileConfiguration.getString("i" + i + ".villagerProfession");
            Villager.Profession villagerProfession = null;
            if (villagerProfessionStr != null) {
                villagerProfession = Villager.Profession.valueOf(villagerProfessionStr);
            }

            String disguiseStr = fileConfiguration.getString("i" + i + ".disguise");
            AdeliaEntityDisguise disguise = null;
            if (disguiseStr != null) {
                disguise = AdeliaEntityDisguise.valueOf(disguiseStr);
            }

            String mountTypeStr = fileConfiguration.getString("i" + i + ".mountType");
            EntityType mountType = null;
            if (mountTypeStr != null) {
                mountType = EntityType.valueOf(mountTypeStr);
            }

            String mountName = fileConfiguration.getString("i" + i + ".mountName");

            List<String> potionEffectTypeListStr = fileConfiguration.getStringList("i" + i + ".potionEffectTypeList");
            List<PotionEffectType> potionEffectTypeList = new ArrayList<>();
            for (String potionStr : potionEffectTypeListStr) {
                potionEffectTypeList.add(PotionEffectType.getByName(potionStr));
            }

            String name = ChatColor.translateAlternateColorCodes('&', nameStr);

            AdeliaEntity adeliaEntity = new AdeliaEntity(adeliaEntityKey, entityType, name, mainHand, offHand, helmet, chestplate, leggings, boots,
                    damage, maxHealth, experience, dropTableNumber, movementSpeed, size, isBaby, isVillagerProfessionRandom, villagerProfession,
                    disguise, mountType, mountName, potionEffectTypeList);

            AdeliaEntityManager.putEntity(adeliaEntityKey, adeliaEntity);
        }
    }
}
