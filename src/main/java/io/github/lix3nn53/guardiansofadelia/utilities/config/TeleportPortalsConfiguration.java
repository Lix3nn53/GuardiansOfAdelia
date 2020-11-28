package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.database.ItemSerializer;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.InstantTeleportPortal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class TeleportPortalsConfiguration {

    private static FileConfiguration fileConfiguration;

    static void createConfig() {
        File customConfigFile = new File(ConfigManager.DATA_FOLDER, "teleportPortals.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            GuardiansOfAdelia.getInstance().saveResource("teleportPortals.yml", false);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    static void loadConfig() {
        for (int i = 1; i <= 1000; i++) {
            boolean contains = fileConfiguration.contains("p" + i);
            if (!contains) break;
            ConfigurationSection current = fileConfiguration.getConfigurationSection("p" + i);

            String worldString = current.getString("world");
            World world = Bukkit.getWorld(worldString);
            double x = current.getDouble("x");
            double y = current.getDouble("y");
            double z = current.getDouble("z");
            float yaw = (float) current.getDouble("yaw");
            float pitch = (float) current.getDouble("pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);
            PortalColor portalColor = PortalColor.valueOf(current.getString("color"));
            String title = current.getString("title");
            Portal portal = new Portal(location, portalColor, title);

            PortalManager.addPortal(portal);

            String tpWorldString = current.getString("tpWorld");
            World tpWorld = Bukkit.getWorld(tpWorldString);
            double tpX = current.getDouble("tpX");
            double tpY = current.getDouble("tpY");
            double tpZ = current.getDouble("tpZ");
            float tpYaw = (float) current.getDouble("tpYaw");
            float tpPitch = (float) current.getDouble("tpPitch");
            Location tpLocation = new Location(tpWorld, tpX, tpY, tpZ, tpYaw, tpPitch);

            int requiredQuestNoAccepted = current.getInt("requiredQuestNoAccepted");
            int requiredQuestNoTurnedIn = current.getInt("requiredQuestNoTurnedIn");

            InstantTeleportPortal instantTeleportPortal = new InstantTeleportPortal(tpLocation, requiredQuestNoAccepted, requiredQuestNoTurnedIn);
            PortalManager.addInstantTeleportPortal(portal, instantTeleportPortal);
        }
    }

    private static void writeConfig() {
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
            fileConfiguration.save("teleportPortals.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
