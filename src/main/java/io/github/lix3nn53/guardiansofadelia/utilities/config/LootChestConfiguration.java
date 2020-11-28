package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChest;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestManager;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestTier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LootChestConfiguration {

    private static FileConfiguration lootChestsConfig;

    static void createConfigs() {
        createLootChests();
    }

    static void loadConfigs() {
        loadLootChests();
    }

    static void writeConfigs() {
        writeLootChests("lootChests.yml");
    }

    private static void createLootChests() {
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "rewards";
        File customConfigFile = new File(filePath, "lootChests.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        lootChestsConfig = new YamlConfiguration();
        try {
            lootChestsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadLootChests() {
        int themeCount = getChildComponentCount(lootChestsConfig, "chest");

        for (int i = 1; i <= themeCount; i++) {
            ConfigurationSection section = lootChestsConfig.getConfigurationSection("chest" + i);
            String worldString = section.getString("world");
            World world = Bukkit.getWorld(worldString);
            double x = section.getDouble("x");
            double y = section.getDouble("y");
            double z = section.getDouble("z");
            Location location = new Location(world, x, y, z);
            String tierStr = section.getString("tier");
            LootChestTier lootChestTier = LootChestTier.valueOf(tierStr);
            LootChest lootChest = new LootChest(location, lootChestTier);

            LootChestManager.addLootChest(lootChest);
        }
    }

    private static int getChildComponentCount(ConfigurationSection configurationSection, String text) {
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

    private static void writeLootChests(String fileName) {
        HashMap<String, List<LootChest>> chunkKeyToLootChests = LootChestManager.getChunkKeyToLootChests();

        int i = 1;
        for (String chunkKey : chunkKeyToLootChests.keySet()) {
            List<LootChest> lootChests = chunkKeyToLootChests.get(chunkKey);

            for (LootChest lootChest : lootChests) {
                Location location = lootChest.getLocation();
                LootChestTier lootChestTier = lootChest.getLootChestTier();

                lootChestsConfig.set("chest" + i + ".world", location.getWorld().getName());
                lootChestsConfig.set("chest" + i + ".x", location.getBlockX());
                lootChestsConfig.set("chest" + i + ".y", location.getBlockY());
                lootChestsConfig.set("chest" + i + ".z", location.getBlockZ());
                lootChestsConfig.set("chest" + i + ".tier", lootChestTier.name());

                i++;
            }
        }

        try {
            String filePath = ConfigManager.DATA_FOLDER + File.separator + "rewards";

            lootChestsConfig.save(filePath + File.separator + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
