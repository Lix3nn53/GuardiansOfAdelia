package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.list.Eggs;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetData;
import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetSkillManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class PetConfigurations {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "pets";
    private static List<YamlConfiguration> fileConfigurations;

    static void createConfigs() {
        fileConfigurations = ConfigurationUtils.getAllConfigsInFile(filePath);
    }

    static void loadConfigs() {
        loadQuestLineConfigs();
    }

    private static void loadQuestLineConfigs() {
        for (YamlConfiguration fileConfiguration : fileConfigurations) {
            String key = fileConfiguration.getString("mythicMobCode");
            int customModelData = fileConfiguration.getInt("customModelData");
            String itemTierStr = fileConfiguration.getString("itemTier");
            ItemTier itemTier = ItemTier.valueOf(itemTierStr);

            Eggs.add(key, customModelData, itemTier);

            PetData petData = new PetData(fileConfiguration);
            PetSkillManager.put(key, petData);

        }
    }
}
