package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.npc.NPCAvatar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class NPCConfiguration {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "world";
    private static FileConfiguration merchantConfig;

    static void createConfigs() {
        merchantConfig = ConfigurationUtils.createConfig(filePath, "npc.yml");
    }

    static void loadConfigs() {
        load();
    }

    private static void load() {
        int count = ConfigurationUtils.getChildComponentCount(merchantConfig, "npc");

        for (int i = 1; i <= count; i++) {
            ConfigurationSection section = merchantConfig.getConfigurationSection("npc" + i);
            int npcId = section.getInt("id");
            int customModelData = section.getInt("customModelData");
            List<String> description = section.getStringList("description");

            NPCAvatar.setAvatar(npcId, customModelData, description);
        }
    }
}
