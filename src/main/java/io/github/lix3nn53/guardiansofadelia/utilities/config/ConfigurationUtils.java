package io.github.lix3nn53.guardiansofadelia.utilities.config;

import org.bukkit.configuration.ConfigurationSection;

public class ConfigurationUtils {

    public static int getChildComponentCount(ConfigurationSection configurationSection, String text) {
        int count = 0;
        while (true) {
            boolean contains = configurationSection.contains(text + (count + 1));
            if (contains) {
                count++;
            } else {
                break;
            }
        }

        return count;
    }
}
