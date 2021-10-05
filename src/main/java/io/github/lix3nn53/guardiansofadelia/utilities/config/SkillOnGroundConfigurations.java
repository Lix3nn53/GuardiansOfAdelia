package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.SkillComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.config.SkillComponentLoader;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillListForGround;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGroundWithLocation;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGroundWithLocationManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillOnGroundConfigurations {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "world" + File.separator + "skillsOnGround";
    private static final String skillListPath = filePath + File.separator + "skillList";
    private static HashMap<String, YamlConfiguration> skillListConfigs;
    private static YamlConfiguration config;

    public static void createConfigs() {
        skillListConfigs = ConfigurationUtils.getAllConfigsInFile(skillListPath);
        config = ConfigurationUtils.createConfig(filePath, "skillsOnGround.yml");
    }

    public static void loadConfigs() {
        loadSkillList();
        load();
    }

    private static void loadSkillList() {
        for (String key : skillListConfigs.keySet()) {
            YamlConfiguration configurationSection = skillListConfigs.get(key);

            ArrayList<Integer> cooldowns = new ArrayList<>();
            cooldowns.add(0);
            List<String> description = new ArrayList<>();

            Skill skill = new Skill("skillOnGround", 1, Material.IRON_HOE, 1, description,
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), cooldowns);

            SkillComponent triggerComponent = SkillComponentLoader.loadSection(configurationSection.getConfigurationSection("trigger"));
            skill.addTrigger(triggerComponent);

            int triggerCount = ConfigurationUtils.getChildComponentCount(configurationSection, "trigger");
            for (int t = 1; t <= triggerCount; t++) {
                SkillComponent triggerComponentExtra = SkillComponentLoader.loadSection(configurationSection.getConfigurationSection("trigger" + t));
                skill.addTrigger(triggerComponentExtra);
            }

            SkillListForGround.addSkill(key, skill);
        }
    }

    private static void load() {
        int count = ConfigurationUtils.getChildComponentCount(config, "skillOnGround");

        for (int i = 1; i <= count; i++) {
            ConfigurationSection configurationSection = config.getConfigurationSection("skillOnGround" + i);

            SkillOnGroundWithLocation skillOnGroundWithLocation = new SkillOnGroundWithLocation(configurationSection);

            SkillOnGroundWithLocationManager.add(skillOnGroundWithLocation);
        }
    }
}
