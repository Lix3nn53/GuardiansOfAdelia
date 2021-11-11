package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChest;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestManager;
import io.github.lix3nn53.guardiansofadelia.rewards.chest.LootChestTier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LootChestConfiguration {

    private static FileConfiguration lootChestsConfig;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "world";

    static void createConfigs() {
        lootChestsConfig = ConfigurationUtils.createConfig(filePath, "lootChests.yml");
    }

    static void loadConfigs() {
        loadLootChests();
    }

    static void writeConfigs() {
        writeLootChests("lootChests.yml");
    }

    private static void loadLootChests() {
        int themeCount = ConfigurationUtils.getChildComponentCount(lootChestsConfig, "chest");

        for (int i = 1; i <= themeCount; i++) {
            ConfigurationSection section = lootChestsConfig.getConfigurationSection("chest" + i);
            String worldString = section.getString("world");
            World world = Bukkit.getWorld(worldString);
            float x = (float) section.getDouble("x");
            float y = (float) section.getDouble("y");
            float z = (float) section.getDouble("z");
            Location location = new Location(world, x, y, z);
            String tierStr = section.getString("tier");
            LootChestTier lootChestTier = LootChestTier.valueOf(tierStr);
            LootChest lootChest = new LootChest(location, lootChestTier);

            LootChestManager.addLootChest(lootChest);
        }
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
