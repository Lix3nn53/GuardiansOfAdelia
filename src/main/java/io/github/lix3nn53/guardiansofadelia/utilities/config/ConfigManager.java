package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.HologramManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {

    static File DATA_FOLDER;
    private static FileConfiguration characterSelectionConfig;
    private static FileConfiguration townsConfig;
    private static FileConfiguration resourcePackConfig;
    private static FileConfiguration hologramsConfig;

    public static void init() {
        if (!GuardiansOfAdelia.getInstance().getDataFolder().exists()) {
            GuardiansOfAdelia.getInstance().getDataFolder().mkdirs();
        }
        DATA_FOLDER = GuardiansOfAdelia.getInstance().getDataFolder();
    }

    public static void createConfigALL() {
        characterSelectionConfig = ConfigurationUtils.createConfig(DATA_FOLDER.toString(), "characterSelection.yml");
        townsConfig = ConfigurationUtils.createConfig(DATA_FOLDER.toString(), "towns.yml");
        resourcePackConfig = ConfigurationUtils.createConfig(DATA_FOLDER.toString(), "resourcepack.yml");
        hologramsConfig = ConfigurationUtils.createConfig(DATA_FOLDER.toString(), "holograms.yml");

        ClassConfigurations.createConfigs();
        RewardDailyConfigurations.createConfigs();
        JobGatheringConfigurations.createConfigs();
        TeleportPortalsConfiguration.createConfig();
        TeleportGuiConfiguration.createConfig();
        DatabaseConfiguration.createConfigs();
        DungeonConfiguration.createConfigs();
        ItemArmorSetConfigurations.createConfigs();
        ItemPassiveSetConfigurations.createConfigs();
        ItemShieldSetConfigurations.createConfigs();
        ItemWeaponSetConfigurations.createConfigs();
        JobCraftingConfigurations.createConfigs();
        QuestConfigurations.createConfigs();
        PetConfigurations.createConfigs();
        LootChestConfiguration.createConfigs();
        GearSetConfiguration.createConfigs();
        MobConfigurations.createConfigs();
    }

    public static void loadConfigALL() {
        ClassConfigurations.loadConfigs();
        RewardDailyConfigurations.loadConfigs();
        JobGatheringConfigurations.loadConfigs();
        loadResourcePackConfig();
        loadCharacterSelectionConfig();
        loadTowns();
        TeleportPortalsConfiguration.loadConfig();
        TeleportGuiConfiguration.loadConfig();
        loadHologramsConfig();
        DatabaseConfiguration.loadConfigs();
        DungeonConfiguration.loadConfigs();
        ItemArmorSetConfigurations.loadConfigs();
        ItemPassiveSetConfigurations.loadConfigs();
        ItemShieldSetConfigurations.loadConfigs();
        ItemWeaponSetConfigurations.loadConfigs();
        JobCraftingConfigurations.loadConfigs();
        PetConfigurations.loadConfigs();
        QuestConfigurations.loadConfigs();
        LootChestConfiguration.loadConfigs();
        GearSetConfiguration.loadConfig();
        MobConfigurations.loadConfigs();
    }

    public static void writeConfigALL() {
        RewardDailyConfigurations.writeConfigs();
        LootChestConfiguration.writeConfigs();
        JobGatheringConfigurations.writeConfigs();
        DungeonConfiguration.writeConfigs();
    }

    public static void loadResourcePackConfig() {
        GuardiansOfAdelia.ResourcePackURL = resourcePackConfig.getString("url");
    }

    public static void loadHologramsConfig() {
        for (int i = 1; i <= 999; i++) {
            boolean contains = hologramsConfig.contains("Hologram" + i + ".world");

            if (!contains) break;

            String worldString = hologramsConfig.getString("Hologram" + i + ".world");
            if (worldString == null) continue;

            World world = Bukkit.getWorld(worldString);
            double x = hologramsConfig.getDouble("Hologram" + i + ".x");
            double y = hologramsConfig.getDouble("Hologram" + i + ".y");
            double z = hologramsConfig.getDouble("Hologram" + i + ".z");
            Location location = new Location(world, x, y, z);

            List<String> texts = hologramsConfig.getStringList("Hologram" + i + ".texts");

            for (int index = texts.size() - 1; index >= 0; index--) {
                String text = texts.get(index);

                String s = ChatColor.translateAlternateColorCodes('&', text);
                Hologram hologram = new Hologram(location.clone(), s);
                location.add(0, 0.4, 0);

                HologramManager.addHologram(hologram);
            }
        }
    }

    private static void loadCharacterSelectionConfig() {
        List<Location> locationList = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            String worldName = characterSelectionConfig.getString("characterSelectionHologram" + i + ".world");
            double x = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".x");
            double y = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".y");
            double z = characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".z");
            float yaw = (float) characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".yaw");
            float pitch = (float) characterSelectionConfig.getDouble("characterSelectionHologram" + i + ".pitch");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            locationList.add(location);
        }

        CharacterSelectionScreenManager.setArmorStandLocationBases(locationList);

        String worldNameCenter = characterSelectionConfig.getString("characterSelection" + "Center" + ".world");
        double xCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".x");
        double yCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".y");
        double zCenter = characterSelectionConfig.getDouble("characterSelection" + "Center" + ".z");
        float yawCenter = (float) characterSelectionConfig.getDouble("characterSelection" + "Center" + ".yaw");
        float pitchCenter = (float) characterSelectionConfig.getDouble("characterSelection" + "Center" + ".pitch");
        Location locationCenter = new Location(Bukkit.getWorld(worldNameCenter), xCenter, yCenter, zCenter, yawCenter, pitchCenter);
        CharacterSelectionScreenManager.setCharacterSelectionCenter(locationCenter);

        String worldNameTuto = characterSelectionConfig.getString("tutorialStart" + ".world");
        double xTuto = characterSelectionConfig.getDouble("tutorialStart" + ".x");
        double yTuto = characterSelectionConfig.getDouble("tutorialStart" + ".y");
        double zTuto = characterSelectionConfig.getDouble("tutorialStart" + ".z");
        float yawTuto = (float) characterSelectionConfig.getDouble("tutorialStart" + ".yaw");
        float pitchTuto = (float) characterSelectionConfig.getDouble("tutorialStart" + ".pitch");
        Location locationTuto = new Location(Bukkit.getWorld(worldNameTuto), xTuto, yTuto, zTuto, yawTuto, pitchTuto);
        CharacterSelectionScreenManager.setTutorialStart(locationTuto);
    }

    private static void writeCharacterSelectionConfig() {
        Location characterSelectionCenter = CharacterSelectionScreenManager.getCharacterSelectionCenter();
        characterSelectionConfig.set("characterSelection" + "Center" + ".world", characterSelectionCenter.getWorld().getName());
        characterSelectionConfig.set("characterSelection" + "Center" + ".x", characterSelectionCenter.getX());
        characterSelectionConfig.set("characterSelection" + "Center" + ".y", characterSelectionCenter.getY());
        characterSelectionConfig.set("characterSelection" + "Center" + ".z", characterSelectionCenter.getZ());
        characterSelectionConfig.set("characterSelection" + "Center" + ".yaw", characterSelectionCenter.getYaw());
        characterSelectionConfig.set("characterSelection" + "Center" + ".pitch", characterSelectionCenter.getPitch());

        Location tutorialStart = CharacterSelectionScreenManager.getTutorialStart();
        characterSelectionConfig.set("tutorialStart" + ".world", tutorialStart.getWorld().getName());
        characterSelectionConfig.set("tutorialStart" + ".x", tutorialStart.getX());
        characterSelectionConfig.set("tutorialStart" + ".y", tutorialStart.getY());
        characterSelectionConfig.set("tutorialStart" + ".z", tutorialStart.getZ());
        characterSelectionConfig.set("tutorialStart" + ".yaw", tutorialStart.getYaw());
        characterSelectionConfig.set("tutorialStart" + ".pitch", tutorialStart.getPitch());

        HashMap<Integer, List<ArmorStand>> characterNoToArmorStands = CharacterSelectionScreenManager.getCharacterNoToArmorStands();
        for (Integer i : characterNoToArmorStands.keySet()) {
            Location location = characterNoToArmorStands.get(i).get(0).getLocation();
            characterSelectionConfig.set("characterSelectionHologram" + i + ".world", location.getWorld().getName());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".x", location.getX());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".y", location.add(0.0, -0.4, 0.0).getY());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".z", location.getZ());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".yaw", location.getYaw());
            characterSelectionConfig.set("characterSelectionHologram" + i + ".pitch", location.getPitch());
        }
        try {
            characterSelectionConfig.save(ConfigManager.DATA_FOLDER + "/characterSelection.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadTowns() {
        for (int i = 1; i < 1000; i++) {
            boolean contains = townsConfig.contains("town" + i);
            if (!contains) break;

            String townName = townsConfig.getString("town" + i + ".name");
            int level = townsConfig.getInt("town" + i + ".level");
            String worldName = townsConfig.getString("town" + i + ".world");
            double x = townsConfig.getDouble("town" + i + ".x");
            double y = townsConfig.getDouble("town" + i + ".y");
            double z = townsConfig.getDouble("town" + i + ".z");
            float yaw = (float) townsConfig.getDouble("town" + i + ".yaw");
            float pitch = (float) townsConfig.getDouble("town" + i + ".pitch");
            Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
            Town town = new Town(townName, location, level);
            TownManager.addTown(town, i);
        }
    }
}
