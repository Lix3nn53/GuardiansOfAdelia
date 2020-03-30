package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.creatures.AdeliaEntityManager;
import io.github.lix3nn53.guardiansofadelia.creatures.entitySkills.EntitySkillSet;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SkillComponentLoader;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntitySkillConfigurations {

    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("monsterSkills.yml");
    }

    static void loadConfigs() {
        loadConfig("monsterSkills.yml");
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
            fileConfiguration = yamlConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfig(String fileName) {
        int count = fileConfiguration.getInt("count");

        for (int i = 1; i <= count; i++) {
            //skill set
            String adeliaEntityKey = fileConfiguration.getString("i" + i + ".mobkey");
            long cooldown = fileConfiguration.getInt("i" + i + ".cooldown");

            int skillCount = fileConfiguration.getInt("i" + i + ".skillCount");

            List<SkillComponent> skills = new ArrayList<>();
            List<Integer> skillLevels = new ArrayList<>();

            //skill
            for (int skill = 1; skill <= skillCount; skill++) {
                int skillLevel = fileConfiguration.getInt("i" + i + ".skill" + skill + ".skillLevel");
                skillLevels.add(skillLevel);

                //trigger component
                ConfigurationSection componentSection = fileConfiguration.getConfigurationSection("i" + i + ".skill" + skill + ".trigger");
                SkillComponent triggerComponent = SkillComponentLoader.loadSection(componentSection);

                skills.add(triggerComponent);
            }

            EntitySkillSet entitySkillSet = new EntitySkillSet(skills, skillLevels, cooldown);

            AdeliaEntityManager.putSkillSet(adeliaEntityKey, entitySkillSet);
        }
    }
}
