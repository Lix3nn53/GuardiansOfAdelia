package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.MMManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Set;

public class MobConfigurations {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "mythicmobs";
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "config.yml");
    }

    static void loadConfigs() {
        loadMobElement();
    }

    private static void loadMobElement() {
        Set<String> keys = fileConfiguration.getKeys(false);

        for (String key : keys) {
            ConfigurationSection configurationSection = fileConfiguration.getConfigurationSection(key);

            String element = configurationSection.getString("element");

            ElementType elementType = ElementType.valueOf(element);

            MMManager.addElement(key, elementType);

            ConfigurationSection resistances = configurationSection.getConfigurationSection("resistances");

            if (resistances == null) continue;

            for (ElementType type : ElementType.values()) {
                if (resistances.contains(type.name())) {
                    float resistance = (float) resistances.getDouble(type.name());

                    MMManager.addElementResistance(key, type, resistance);
                }
            }
        }
    }
}
