package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSet;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetEffect;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.gearset.GearSetManager;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class GearSetConfiguration {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "items";
    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "gearSets.yml");
    }

    static void loadConfig() {
        for (int i = 1; i <= 1000; i++) {
            boolean contains = fileConfiguration.contains("set" + i);
            if (!contains) break;
            ConfigurationSection current = fileConfiguration.getConfigurationSection("set" + i);

            String name = current.getString("name");

            for (int pieceCount = 1; pieceCount < 6; pieceCount++) {
                if (current.contains("effectsAt" + pieceCount)) {
                    List<String> effectsAt2 = current.getStringList("effectsAt" + pieceCount);
                    GearSet gearSet = new GearSet(name, pieceCount);
                    for (String gearSetEffectStr : effectsAt2) {
                        GearSetEffect gearSetEffect = GearSetEffect.valueOf(gearSetEffectStr);
                        GearSetManager.addEffect(gearSet, gearSetEffect);
                    }
                }
            }
        }
    }
}
