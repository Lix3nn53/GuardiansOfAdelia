package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.*;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class JobGatheringConfigurations {

    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "gathering";
    private static FileConfiguration gatheringModelsConfig;
    private static FileConfiguration mobKeyToIngredients;
    private static FileConfiguration ingredientsConfig;

    static void createConfigs() {
        gatheringModelsConfig = ConfigurationUtils.createConfig(filePath, "GatheringModels.yml");
        ingredientsConfig = ConfigurationUtils.createConfig(filePath, "Ingredients.yml");
        mobKeyToIngredients = ConfigurationUtils.createConfig(filePath, "MobKeyToIngredients.yml");
    }

    static void loadConfigs() {
        loadIngredients();
        loadGatheringModels();
        loadMobKeyToIngredients();
    }

    private static void loadIngredients() {
        for (int i = 1; i <= 9999; i++) {
            if (!ingredientsConfig.contains("i" + i)) break;

            String nameStr = ingredientsConfig.getString("i" + i + ".name");
            Material material = Material.valueOf(ingredientsConfig.getString("i" + i + ".material"));
            int customModelData = ingredientsConfig.contains("i" + i + ".customModelData") ? ingredientsConfig.getInt("i" + i + ".customModelData") : 0;
            int ingredientLevel = ingredientsConfig.getInt("i" + i + ".ingredientLevel");
            List<String> jobsCanUse = ingredientsConfig.getStringList("i" + i + ".jobsCanUse");
            List<String> extraText = ingredientsConfig.getStringList("i" + i + ".extraText");

            String name = ChatColor.translateAlternateColorCodes('&', nameStr);

            Ingredient ingredient = new Ingredient(i, material, name, ingredientLevel, jobsCanUse, extraText, customModelData);

            GatheringManager.putIngredient(i, ingredient);
        }
    }

    private static void loadGatheringModels() {
        for (int i = 1; i <= 999; i++) {
            if (!gatheringModelsConfig.contains("i" + i)) break;

            String nameStr = gatheringModelsConfig.getString("i" + i + ".name");
            String name = ChatColor.translateAlternateColorCodes('&', nameStr);

            Material material = Material.valueOf(gatheringModelsConfig.getString("i" + i + ".material"));
            int customModelData = gatheringModelsConfig.getInt("i" + i + ".customModelData");
            int cooldownCustomModelData = gatheringModelsConfig.getInt("i" + i + ".cooldownCustomModelData");
            GatheringToolType gatheringToolType = GatheringToolType.valueOf(gatheringModelsConfig.getString("i" + i + ".gatheringToolType"));
            GatheringToolTier minGatheringToolTier = GatheringToolTier.valueOf(gatheringModelsConfig.getString("i" + i + ".minGatheringToolTier"));

            GatheringModelData gatheringModelData = new GatheringModelData(customModelData, cooldownCustomModelData, name, material, gatheringToolType, minGatheringToolTier);
            GatheringManager.putGatheringModelData(i, gatheringModelData);

            for (int l = 1; l < 999; l++) {
                if (!gatheringModelsConfig.contains("i" + i + ".loc" + l)) break;

                String worldString = gatheringModelsConfig.getString("i" + i + ".loc" + l + ".world");
                World world = Bukkit.getWorld(worldString);
                double x = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".x");
                double y = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".y");
                double z = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".z");
                float yaw = (float) gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".yaw");
                float pitch = (float) gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".pitch");
                Location location = new Location(world, x, y, z, yaw, pitch);

                GatheringModel gatheringModel = new GatheringModel(i, location);
                GatheringManager.putGatheringModel(gatheringModel);
            }
        }
    }

    private static void loadMobKeyToIngredients() {
        for (int i = 1; i <= 999; i++) {
            if (!mobKeyToIngredients.contains("m" + i)) break;

            String mobKey = mobKeyToIngredients.getString("m" + i + ".mobKey");
            List<Integer> ingredients = mobKeyToIngredients.getIntegerList("m" + i + ".ingredients");

            for (Integer ingredient : ingredients) {
                GatheringManager.putMobKeyToIngredient(mobKey, ingredient);
            }
        }
    }
}
