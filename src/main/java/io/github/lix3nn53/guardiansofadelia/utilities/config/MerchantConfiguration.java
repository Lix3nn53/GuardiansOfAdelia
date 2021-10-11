package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantMenu;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class MerchantConfiguration {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "world";
    private static FileConfiguration merchantConfig;

    static void createConfigs() {
        merchantConfig = ConfigurationUtils.createConfig(filePath, "merchants.yml");
    }

    static void loadConfigs() {
        load();
    }

    private static void load() {
        int count = ConfigurationUtils.getChildComponentCount(merchantConfig, "merchant");

        for (int i = 1; i <= count; i++) {
            ConfigurationSection section = merchantConfig.getConfigurationSection("merchant" + i);
            int npcId = section.getInt("npc");
            MerchantType type = MerchantType.valueOf(section.getString("type").toUpperCase());
            int level = section.getInt("level");

            MerchantMenu merchantMenu = new MerchantMenu(type, level, npcId);

            MerchantManager.setMerchant(npcId, merchantMenu);
        }
    }
}
