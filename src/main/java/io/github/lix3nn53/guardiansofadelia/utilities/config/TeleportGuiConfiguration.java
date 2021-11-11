package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportGuiItem;
import io.github.lix3nn53.guardiansofadelia.transportation.InstantTeleportGuiManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class TeleportGuiConfiguration {

    private static FileConfiguration fileConfiguration;
    private static final String filePath = ConfigManager.DATA_FOLDER.toString();

    static void createConfig() {
        fileConfiguration = ConfigurationUtils.createConfig(filePath, "teleportGui.yml");
    }

    static void loadConfig() {
        for (int i = 1; i <= 1000; i++) {
            boolean contains = fileConfiguration.contains("p" + i);
            if (!contains) break;
            ConfigurationSection current = fileConfiguration.getConfigurationSection("p" + i);

            String worldString = current.getString("world");
            World world = Bukkit.getWorld(worldString);
            float x = (float) current.getDouble("x");
            float y = (float) current.getDouble("y");
            float z = (float) current.getDouble("z");
            float yaw = (float) current.getDouble("yaw");
            float pitch = (float) current.getDouble("pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);

            String name = current.getString("name");
            int cost = current.getInt("cost");
            int requiredQuestNoTurnedIn = current.getInt("requiredQuestNoTurnedIn");

            InstantTeleportGuiItem instantTeleportGuiItem = new InstantTeleportGuiItem(name, location, cost);
            InstantTeleportGuiManager.addTeleport(requiredQuestNoTurnedIn, instantTeleportGuiItem);
        }
    }
}
