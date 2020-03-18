package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.InstantTeleportPortal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.Portal;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TeleportPortalsConfiguration {

    private static FileConfiguration fileConfiguration;

    static void createConfigs() {
        createDatabaseConfig();
    }

    static void loadConfigs() {
        loadDatabaseConfig();
    }

    private static void createDatabaseConfig() {
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

    private static void loadDatabaseConfig() {
        int portalNumber = fileConfiguration.getInt("PortalNumber");
        for (int i = 1; i <= portalNumber; i++) {
            String worldString = fileConfiguration.getString("p" + i + ".world");
            World world = Bukkit.getWorld(worldString);
            double x = fileConfiguration.getDouble("p" + i + ".x");
            double y = fileConfiguration.getDouble("p" + i + ".y");
            double z = fileConfiguration.getDouble("p" + i + ".z");
            float yaw = (float) fileConfiguration.getDouble("p" + i + ".yaw");
            float pitch = (float) fileConfiguration.getDouble("p" + i + ".pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);
            PortalColor portalColor = PortalColor.valueOf(fileConfiguration.getString("p" + i + ".color"));
            Portal portal = new Portal(location, portalColor);

            PortalManager.addPortal(portal);

            String tpWorldString = fileConfiguration.getString("p" + i + ".tpWorld");
            World tpWorld = Bukkit.getWorld(tpWorldString);
            double tpX = fileConfiguration.getDouble("p" + i + ".tpX");
            double tpY = fileConfiguration.getDouble("p" + i + ".tpY");
            double tpZ = fileConfiguration.getDouble("p" + i + ".tpZ");
            float tpYaw = (float) fileConfiguration.getDouble("p" + i + ".tpYaw");
            float tpPitch = (float) fileConfiguration.getDouble("p" + i + ".tpPitch");
            Location tpLocation = new Location(tpWorld, tpX, tpY, tpZ, tpYaw, tpPitch);

            int requiredQuestNoAccepted = fileConfiguration.getInt("p" + i + ".requiredQuestNoAccepted");
            int requiredQuestNoTurnedIn = fileConfiguration.getInt("p" + i + ".requiredQuestNoTurnedIn");

            InstantTeleportPortal instantTeleportPortal = new InstantTeleportPortal(tpLocation, requiredQuestNoAccepted, requiredQuestNoTurnedIn);
            PortalManager.addInstantTeleportPortal(portal, instantTeleportPortal);
        }
    }
}
