package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import io.github.lix3nn53.guardiansofadelia.creatures.entitySkills.EntitySkillSet;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SCMain;
import io.github.lix3nn53.guardiansofadelia.sounds.GoaSound;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntitySkillConfigurations {

    private final static HashMap<String, FileConfiguration> fileNameToConfiguration = new HashMap<>();

    static void createConfigs() {
        createConfig("monsterSkills.yml");
    }

    static void loadConfigs() {
        loadEntities("monsterSkills.yml");
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
            String adeliaEntityKey = fileConfiguration.getString("i" + i + ".mobkey");

            List<SkillComponent> skills = new ArrayList<>();
            List<Integer> skillLevels = new ArrayList<>();

            long cooldown = fileConfiguration.getInt("i" + i + ".cooldown");

            int skillCount = fileConfiguration.getInt("i" + i + ".skillCount");

            for (int y = 1; y <= skillCount; y++) {
                int skillLevel = fileConfiguration.getInt("i" + i + ".skill" + y + ".skillLevel");
                int childComponentCount = fileConfiguration.getInt("i" + i + ".skill" + y + ".childComponentCount");

                for (int k = 1; k <= childComponentCount; k++) {
                    ConfigurationSection configurationSection = fileConfiguration.getConfigurationSection("i" + i + ".skill" + y + ".child" + k);

                }

                skillLevels.add(skillLevel);
            }

            List<SkillComponent> children = new ArrayList<>();

            int childCount = fileConfiguration.getInt("i" + i + ".componentCount");

            for (int y = 1; y <= childCount; y++) {

            }

            SkillComponent trigger = SCMain.getSkillProjectileParticle(ChatColor.DARK_GREEN + "Shoosh!", 40, children, GoaSound.SKILL_POISON_SLASH, Color.LIME, 1, 2.7);
            skills.add(trigger);

            EntitySkillSet entitySkillSet = new EntitySkillSet(skills, skillLevels, cooldown);

            AdeliaEntityManager.putSkillSet(adeliaEntityKey, entitySkillSet);
        }
    }
}
