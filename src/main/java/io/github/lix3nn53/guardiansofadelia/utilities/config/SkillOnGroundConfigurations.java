package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGroundWithLocation;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGroundWithLocationManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SkillOnGroundConfigurations {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "world";
    private static YamlConfiguration config;

    static void createConfigs() {
        config = ConfigurationUtils.createConfig(filePath, "skillsOnGround.yml");
    }

    static void loadConfigs() {
        load();
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
