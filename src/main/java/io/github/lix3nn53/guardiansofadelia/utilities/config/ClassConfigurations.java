package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.guardian.attribute.AttributeType;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClassManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SkillComponentLoader;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassConfigurations {

    private static final HashMap<String, FileConfiguration> classNameToConfiguration = new HashMap<>();
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config", true);
        createQuestLineConfigs();
    }

    static void loadConfigs() {
        loadClassConfigs();
    }

    private static void createConfig(String className, boolean isMain) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "classes";
        File customConfigFile = new File(filePath, className + ".yml");
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
                classNameToConfiguration.put(className, yamlConfiguration);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void createQuestLineConfigs() {
        List<String> classList = fileConfiguration.getStringList("classList");

        for (String fileName : classList) {
            createConfig(fileName + ".yml", false);
        }
    }

    private static void loadClassConfigs() {
        for (String className : classNameToConfiguration.keySet()) {
            FileConfiguration fileConfiguration = classNameToConfiguration.get(className);

            String colorStr = fileConfiguration.getString("color");
            ChatColor color = ChatColor.valueOf(colorStr);

            int classIconCustomModelData = fileConfiguration.getInt("classIconCustomModelData");

            int attributeTierFire = fileConfiguration.getInt("attributeTierFire");
            int attributeTierWater = fileConfiguration.getInt("attributeTierWater");
            int attributeTierEarth = fileConfiguration.getInt("attributeTierEarth");
            int attributeTierLightning = fileConfiguration.getInt("attributeTierLightning");
            int attributeTierWind = fileConfiguration.getInt("attributeTierWind");
            HashMap<AttributeType, Integer> attributeTiers = new HashMap<>();
            attributeTiers.put(AttributeType.FIRE, attributeTierFire);
            attributeTiers.put(AttributeType.WATER, attributeTierWater);
            attributeTiers.put(AttributeType.EARTH, attributeTierEarth);
            attributeTiers.put(AttributeType.LIGHTNING, attributeTierLightning);
            attributeTiers.put(AttributeType.WIND, attributeTierWind);


            String defaultWeaponGearTypeStr = fileConfiguration.getString("defaultWeaponGearType");
            WeaponGearType defaultWeaponGearType = WeaponGearType.valueOf(defaultWeaponGearTypeStr);

            String defaultArmorGearTypeStr = fileConfiguration.getString("defaultArmorGearType");
            ArmorGearType defaultArmorGearType = ArmorGearType.valueOf(defaultArmorGearTypeStr);


            HashMap<Integer, Skill> skillSet = new HashMap<>();

            if (fileConfiguration.contains("skillOne")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillOne");
                Skill skill = loadSkill(skillSection, 0);
                skillSet.put(0, skill);
            }
            if (fileConfiguration.contains("skillTwo")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillTwo");
                Skill skill = loadSkill(skillSection, 0);
                skillSet.put(1, skill);
            }
            if (fileConfiguration.contains("skillThree")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillThree");
                Skill skill = loadSkill(skillSection, 0);
                skillSet.put(2, skill);
            }
            if (fileConfiguration.contains("skillPassive")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillPassive");
                Skill skill = loadSkill(skillSection, 0);
                skillSet.put(3, skill);
            }
            if (fileConfiguration.contains("skillUltimate")) {
                ConfigurationSection skillSection = fileConfiguration.getConfigurationSection("skillUltimate");
                Skill skill = loadSkill(skillSection, 0);
                skillSet.put(4, skill);
            }

            List<ShieldGearType> shieldGearTypes = new ArrayList<>();
            if (fileConfiguration.contains("shieldGearTypes")) {
                List<String> gearTypes = fileConfiguration.getStringList("shieldGearTypes");
                for (String gearType : gearTypes) {
                    ShieldGearType shieldGearType = ShieldGearType.valueOf(gearType);
                    shieldGearTypes.add(shieldGearType);
                }
            }
            List<WeaponGearType> weaponGearTypes = new ArrayList<>();
            if (fileConfiguration.contains("weaponGearTypes")) {
                List<String> gearTypes = fileConfiguration.getStringList("weaponGearTypes");
                for (String gearType : gearTypes) {
                    WeaponGearType weaponGearType = WeaponGearType.valueOf(gearType);
                    weaponGearTypes.add(weaponGearType);
                }
            }
            List<ArmorGearType> armorGearTypes = new ArrayList<>();
            if (fileConfiguration.contains("armorGearTypes")) {
                List<String> gearTypes = fileConfiguration.getStringList("armorGearTypes");
                for (String gearType : gearTypes) {
                    ArmorGearType armorGearType = ArmorGearType.valueOf(gearType);
                    armorGearTypes.add(armorGearType);
                }
            }

            boolean hasDefaultOffhand = fileConfiguration.getBoolean("hasDefaultOffhand");
            boolean isDefaultOffhandWeapon = false;
            if (hasDefaultOffhand) {
                isDefaultOffhandWeapon = fileConfiguration.getBoolean("isDefaultOffhandWeapon");
            }

            RPGClass rpgClass = new RPGClass(color, className, classIconCustomModelData, attributeTiers, skillSet, defaultWeaponGearType,
                    defaultArmorGearType, shieldGearTypes, weaponGearTypes, armorGearTypes, hasDefaultOffhand, isDefaultOffhandWeapon);

            RPGClassManager.addClass(className, rpgClass);
        }
    }

    private static Skill loadSkill(ConfigurationSection skillSection, int skillIndex) {
        String name = skillSection.getString("name");
        List<String> description = skillSection.getStringList("description");
        int customModelData = skillSection.getInt("customModelData");
        List<Integer> reqLevels = getDefaultReqLevels(skillIndex);
        List<Integer> reqPoints = getDefaultReqPoints(skillIndex);
        List<Integer> manaCosts = skillSection.getIntegerList("manaCosts");
        List<Integer> cooldowns = skillSection.getIntegerList("cooldowns");

        Skill skill = new Skill(name, 6, Material.IRON_HOE, customModelData, description, reqLevels, reqPoints, manaCosts, cooldowns);
        SkillComponent triggerComponent = SkillComponentLoader.loadSection(skillSection.getConfigurationSection("trigger"));
        skill.addTrigger(triggerComponent);

        return skill;
    }

    private static List<Integer> getDefaultReqLevels(int skillIndex) {
        List<Integer> reqLevels = new ArrayList<>();
        if (skillIndex == 0) {
            reqLevels.add(1);
            reqLevels.add(13);
            reqLevels.add(25);
            reqLevels.add(37);
            reqLevels.add(49);
            reqLevels.add(61);
        } else if (skillIndex == 1) {
            reqLevels.add(8);
            reqLevels.add(20);
            reqLevels.add(32);
            reqLevels.add(44);
            reqLevels.add(56);
            reqLevels.add(68);
        } else if (skillIndex == 2) {
            reqLevels.add(12);
            reqLevels.add(24);
            reqLevels.add(36);
            reqLevels.add(48);
            reqLevels.add(60);
            reqLevels.add(72);
        } else if (skillIndex == 3) {
            reqLevels.add(20);
            reqLevels.add(35);
            reqLevels.add(50);
            reqLevels.add(60);
            reqLevels.add(70);
            reqLevels.add(80);
        } else if (skillIndex == 4) {
            reqLevels.add(40);
            reqLevels.add(50);
            reqLevels.add(60);
            reqLevels.add(70);
            reqLevels.add(80);
            reqLevels.add(90);
        }

        return reqLevels;
    }

    private static List<Integer> getDefaultReqPoints(int skillIndex) {
        List<Integer> reqPoints = new ArrayList<>();
        if (skillIndex == 0) {
            reqPoints.add(1);
            reqPoints.add(1);
            reqPoints.add(2);
            reqPoints.add(2);
            reqPoints.add(3);
            reqPoints.add(3);
        } else if (skillIndex == 1) {
            reqPoints.add(2);
            reqPoints.add(2);
            reqPoints.add(3);
            reqPoints.add(3);
            reqPoints.add(4);
            reqPoints.add(4);
        } else if (skillIndex == 2) {
            reqPoints.add(2);
            reqPoints.add(2);
            reqPoints.add(3);
            reqPoints.add(3);
            reqPoints.add(4);
            reqPoints.add(4);
        } else if (skillIndex == 3) {
            reqPoints.add(2);
            reqPoints.add(3);
            reqPoints.add(3);
            reqPoints.add(4);
            reqPoints.add(4);
            reqPoints.add(5);
        } else if (skillIndex == 4) {
            reqPoints.add(5);
            reqPoints.add(6);
            reqPoints.add(7);
            reqPoints.add(8);
            reqPoints.add(9);
            reqPoints.add(10);
        }

        return reqPoints;
    }
}