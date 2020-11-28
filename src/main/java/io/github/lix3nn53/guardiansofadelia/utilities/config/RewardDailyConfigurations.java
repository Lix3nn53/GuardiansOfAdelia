package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.database.ItemSerializer;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class RewardDailyConfigurations {

    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createConfig("daily.yml");
    }

    static void loadConfigs() {
        try {
            loadDailyRewardsConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeConfigs() {
        writeDailyRewardsConfig("daily.yml");
    }

    private static void createConfig(String fileName) {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "rewards";
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

            fileConfiguration = yamlConfiguration;
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadDailyRewardsConfig() throws IOException {
        int itemCount = fileConfiguration.getInt("count");

        for (int i = 1; i <= itemCount; i++) {
            String itemStr = fileConfiguration.getString("i" + i);

            ItemStack itemStack = ItemSerializer.itemStackFromBase64(itemStr);

            DailyRewardHandler.setReward(i, itemStack);
        }
    }

    private static void writeDailyRewardsConfig(String fileName) {
        ItemStack[] rewards = DailyRewardHandler.getRewards();

        int i = 1;
        for (ItemStack itemStack : rewards) {
            if (itemStack == null) continue;

            String s = ItemSerializer.itemStackToBase64(itemStack);
            fileConfiguration.set("i" + i, s);

            i++;
        }

        fileConfiguration.set("count", i - 1);

        try {
            String filePath = ConfigManager.DATA_FOLDER + File.separator + "rewards";

            fileConfiguration.save(filePath + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
