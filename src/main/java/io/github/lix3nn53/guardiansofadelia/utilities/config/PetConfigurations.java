package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetData;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetSkillManager;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.list.Eggs;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

public class PetConfigurations {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "pets";
    private static HashMap<String, YamlConfiguration> fileConfigurations;

    static void createConfigs() {
        fileConfigurations = ConfigurationUtils.getAllConfigsInFile(filePath);
    }

    static void loadConfigs() {
        load();
    }

    private static void load() {
        for (String key : fileConfigurations.keySet()) {
            YamlConfiguration fileConfiguration = fileConfigurations.get(key);

            int customModelData = fileConfiguration.getInt("customModelData");
            String itemTierStr = fileConfiguration.getString("itemTier");
            ItemTier itemTier = ItemTier.valueOf(itemTierStr);

            Eggs.add(key, customModelData, itemTier);

            PetData petData = new PetData(fileConfiguration);
            PetSkillManager.put(key, petData);
        }
    }
}
