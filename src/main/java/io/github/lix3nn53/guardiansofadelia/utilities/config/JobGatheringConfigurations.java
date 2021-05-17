package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.*;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

public class JobGatheringConfigurations {

    private static FileConfiguration customModelDataToIngredients;
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "gathering";
    private static FileConfiguration gatheringToolToCustomModelDatas;
    private static FileConfiguration gatheringModelsConfig;
    private static FileConfiguration mobKeyToIngredients;
    private static FileConfiguration ingredientsConfig;

    static void createConfigs() {
        customModelDataToIngredients = ConfigurationUtils.createConfig(filePath, "CustomModelDataToIngredients.yml");
        gatheringModelsConfig = ConfigurationUtils.createConfig(filePath, "GatheringModels.yml");
        gatheringToolToCustomModelDatas = ConfigurationUtils.createConfig(filePath, "GatheringToolToCustomModelDatas.yml");
        ingredientsConfig = ConfigurationUtils.createConfig(filePath, "Ingredients.yml");
        mobKeyToIngredients = ConfigurationUtils.createConfig(filePath, "MobKeyToIngredients.yml");
    }

    static void loadConfigs() {
        loadIngredients();
        loadGatheringModels();
        loadBlockToIngredients();
        loadGatheringToolToCustomModelDatas();
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

            String worldString = gatheringModelsConfig.getString("i" + i + ".world");
            World world = Bukkit.getWorld(worldString);
            double x = gatheringModelsConfig.getDouble("i" + i + ".x");
            double y = gatheringModelsConfig.getDouble("i" + i + ".y");
            double z = gatheringModelsConfig.getDouble("i" + i + ".z");
            float yaw = (float) gatheringModelsConfig.getDouble("i" + i + ".yaw");
            float pitch = (float) gatheringModelsConfig.getDouble("i" + i + ".pitch");
            Location location = new Location(world, x, y, z, yaw, pitch);

            String nameStr = gatheringModelsConfig.getString("i" + i + ".name");
            int customModelData = gatheringModelsConfig.getInt("i" + i + ".customModelData");
            int cooldownCustomModelData = gatheringModelsConfig.getInt("i" + i + ".cooldownCustomModelData");

            String name = ChatColor.translateAlternateColorCodes('&', nameStr);

            GatheringModel gatheringModel = new GatheringModel(location, customModelData, cooldownCustomModelData, name);

            GatheringManager.putGatheringModel(gatheringModel);
        }
    }

    private static void loadGatheringToolToCustomModelDatas() {
        for (int i = 1; i <= 999; i++) {
            if (!gatheringToolToCustomModelDatas.contains("t" + i)) break;

            String gatheringToolTypeStr = gatheringToolToCustomModelDatas.getString("t" + i + ".gatheringToolType");
            String gatheringToolTierStr = gatheringToolToCustomModelDatas.getString("t" + i + ".gatheringToolTier");
            List<Integer> targetCustomModelDatas = gatheringToolToCustomModelDatas.getIntegerList("t" + i + ".targetCustomModelDatas");

            GatheringToolType gatheringToolType = GatheringToolType.valueOf(gatheringToolTypeStr);
            GatheringToolTier gatheringToolTier = GatheringToolTier.valueOf(gatheringToolTierStr);

            for (int customModelData : targetCustomModelDatas) {
                GatheringManager.putToolToCustomModelData(gatheringToolType, gatheringToolTier, customModelData);
            }
        }
    }

    private static void loadBlockToIngredients() {
        for (int i = 1; i <= 999; i++) {
            if (!customModelDataToIngredients.contains("i" + i)) break;

            List<Integer> ingredients = customModelDataToIngredients.getIntegerList("i" + i + ".ingredients");
            int customModelData = customModelDataToIngredients.getInt("i" + i + ".customModelData");

            for (int ingredient : ingredients) {
                GatheringManager.putCustomModelDataToIngredient(customModelData, ingredient);
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
