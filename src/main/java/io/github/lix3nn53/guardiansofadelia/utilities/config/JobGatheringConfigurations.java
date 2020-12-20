package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JobGatheringConfigurations {

    private static FileConfiguration ingredientsConfig;
    private static FileConfiguration gatheringModelsConfig;
    private static FileConfiguration customModelDataToIngredients;
    private static FileConfiguration gatheringTypeToIngredients;
    private static FileConfiguration gatheringToolToCustomModelDatas;

    static void createConfigs() {
        createIngredients();
        createGatheringModels();
        createCustomModelDataToIngredients();
        createGatheringToolToCustomModelDatas();
        createGatheringTypeToIngredients();
    }

    static void loadConfigs() {
        loadIngredients();
        loadGatheringModels();
        loadBlockToIngredients();
        loadGatheringToolToCustomModelDatas();
        loadGatheringTypeToIngredients();
    }

    private static void createIngredients() {
        String fileName = "Ingredients.yml";
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "gathering";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ingredientsConfig = new YamlConfiguration();
        try {
            ingredientsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadIngredients() {
        int itemCount = ingredientsConfig.getInt("ingredientCount");

        for (int i = 1; i <= itemCount; i++) {
            String nameStr = ingredientsConfig.getString("i" + i + ".name");
            int customModelData = ingredientsConfig.getInt("i" + i + ".customModelData");
            int ingredientLevel = ingredientsConfig.getInt("i" + i + ".ingredientLevel");
            List<String> jobsCanUse = ingredientsConfig.getStringList("i" + i + ".jobsCanUse");
            List<String> extraText = ingredientsConfig.getStringList("i" + i + ".extraText");

            String name = ChatColor.translateAlternateColorCodes('&', nameStr);

            Ingredient ingredient = new Ingredient(i, name, ingredientLevel, jobsCanUse, extraText, customModelData);

            GatheringManager.putIngredient(i, ingredient);
        }
    }

    private static void createGatheringModels() {
        String fileName = "GatheringModels.yml";
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "gathering";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gatheringModelsConfig = new YamlConfiguration();
        try {
            gatheringModelsConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadGatheringModels() {
        int itemCount = gatheringModelsConfig.getInt("count");

        for (int i = 1; i <= itemCount; i++) {
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

            String name = ChatColor.translateAlternateColorCodes('&', nameStr);

            GatheringModel gatheringModel = new GatheringModel(location, customModelData, name);

            GatheringManager.putGatheringModel(gatheringModel);
        }
    }

    private static void createGatheringToolToCustomModelDatas() {
        String fileName = "GatheringToolToCustomModelDatas.yml";
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "gathering";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gatheringToolToCustomModelDatas = new YamlConfiguration();
        try {
            gatheringToolToCustomModelDatas.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadGatheringToolToCustomModelDatas() {
        int itemCount = gatheringToolToCustomModelDatas.getInt("toolCount");

        for (int i = 1; i <= itemCount; i++) {
            String gatheringToolStr = gatheringToolToCustomModelDatas.getString("t" + i + ".gatheringTool");
            List<Integer> targetCustomModelDatas = gatheringToolToCustomModelDatas.getIntegerList("t" + i + ".targetCustomModelDatas");

            GatheringTool gatheringTool = GatheringTool.valueOf(gatheringToolStr);

            for (int customModelData : targetCustomModelDatas) {
                GatheringManager.putToolToCustomModelData(gatheringTool, customModelData);
            }
        }
    }

    private static void createCustomModelDataToIngredients() {
        String fileName = "CustomModelDataToIngredients.yml";
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "gathering";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        customModelDataToIngredients = new YamlConfiguration();
        try {
            customModelDataToIngredients.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadBlockToIngredients() {
        int itemCount = customModelDataToIngredients.getInt("blockCount");

        for (int i = 1; i <= itemCount; i++) {
            List<Integer> ingredients = customModelDataToIngredients.getIntegerList("b" + i + ".ingredients");
            List<Integer> sourceCustomModelDatas = customModelDataToIngredients.getIntegerList("b" + i + ".sourceCustomModelDatas");

            for (int customModelData : sourceCustomModelDatas) {
                for (int ingredient : ingredients) {
                    GatheringManager.putCustomModelDataToIngredient(customModelData, ingredient);
                }
            }
        }
    }

    private static void createGatheringTypeToIngredients() {
        String fileName = "GatheringTypeToIngredients.yml";
        String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "gathering";
        File customConfigFile = new File(filePath, fileName);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();

            try {
                customConfigFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        gatheringTypeToIngredients = new YamlConfiguration();
        try {
            gatheringTypeToIngredients.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadGatheringTypeToIngredients() {
        int itemCount = gatheringTypeToIngredients.getInt("ingredientCount");

        for (int i = 1; i <= itemCount; i++) {
            List<Integer> ingredients = gatheringTypeToIngredients.getIntegerList("g" + i + ".ingredients");
            List<String> sourceGatheringTypes = gatheringTypeToIngredients.getStringList("i" + i + ".gatheringTypes");

            for (String str : sourceGatheringTypes) {
                for (Integer ingredient : ingredients) {
                    GatheringManager.putGatheringTypeToIngredient(GatheringType.valueOf(str), ingredient);
                }
            }
        }
    }
}
