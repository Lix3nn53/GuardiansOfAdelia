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

public class EntitySkillSetConfigurations {

    private static final List<FileConfiguration> questLineConfigurations = new ArrayList<>();
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("config.yml", true);
        createSkillSetConfigs();
    }

    static void loadConfigs() {
        loadSkillSetConfigs();
    }

    private static void createConfig(String fileName, boolean isMain) {
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
            if (isMain) {
                fileConfiguration = yamlConfiguration;
            } else {
                questLineConfigurations.add(yamlConfiguration);
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void createSkillSetConfigs() {
        List<String> questLineList = fileConfiguration.getStringList("entityList");

        for (String fileName : questLineList) {
            createConfig(fileName + ".yml", false);
        }
    }

    private static void loadSkillSetConfigs() {
        for (FileConfiguration skillSetConfiguration : questLineConfigurations) {
            //skill set
            String adeliaEntityKey = skillSetConfiguration.getString("mobkey");
            long cooldown = skillSetConfiguration.getInt("cooldown");

            int skillCount = skillSetConfiguration.getInt("skillCount");

            List<SkillComponent> skills = new ArrayList<>();
            List<Integer> skillLevels = new ArrayList<>();

            //skill
            for (int skill = 1; skill <= skillCount; skill++) {
                int skillLevel = skillSetConfiguration.getInt("skill" + skill + ".skillLevel");
                skillLevels.add(skillLevel);

                //trigger component
                ConfigurationSection componentSection = skillSetConfiguration.getConfigurationSection("skill" + skill + ".trigger");
                SkillComponent triggerComponent = SkillComponentLoader.loadSection(componentSection);

                skills.add(triggerComponent);
            }

            EntitySkillSet entitySkillSet = new EntitySkillSet(skills, skillLevels, cooldown);

            AdeliaEntityManager.putSkillSet(adeliaEntityKey, entitySkillSet);
        }
    }
}
