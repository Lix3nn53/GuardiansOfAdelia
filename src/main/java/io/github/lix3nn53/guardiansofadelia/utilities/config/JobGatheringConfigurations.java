package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringManager;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringTool;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringType;
import io.github.lix3nn53.guardiansofadelia.jobs.gathering.Ingredient;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JobGatheringConfigurations {

    private static FileConfiguration ingredientsConfig;
    private static FileConfiguration blockToIngredients;
    private static FileConfiguration gatheringTypeToIngredients;
    private static FileConfiguration gatheringToolToBlocks;

    static void createConfigs() {
        createBlockToIngredients();
        createGatheringToolToBlocks();
        createGatheringTypeToIngredients();
        createIngredients();
    }

    static void loadConfigs() {
        loadBlockToIngredients();
        loadGatheringToolToBlocks();
        loadGatheringTypeToIngredients();
        loadIngredients();
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

    private static void createGatheringToolToBlocks() {
        String fileName = "GatheringToolToBlock.yml";
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

        gatheringToolToBlocks = new YamlConfiguration();
        try {
            gatheringToolToBlocks.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadGatheringToolToBlocks() {
        int itemCount = gatheringToolToBlocks.getInt("toolCount");

        for (int i = 1; i <= itemCount; i++) {
            String gatheringToolStr = gatheringToolToBlocks.getString("t" + i + ".gatheringTool");
            List<String> targetBlocks = gatheringToolToBlocks.getStringList("i" + i + ".targetBlocks");

            GatheringTool gatheringTool = GatheringTool.valueOf(gatheringToolStr);

            for (String str : targetBlocks) {
                GatheringManager.putToolToBlock(gatheringTool, Material.valueOf(str));
            }
        }
    }

    private static void createBlockToIngredients() {
        String fileName = "BlockToIngredients.yml";
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

        blockToIngredients = new YamlConfiguration();
        try {
            blockToIngredients.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static void loadBlockToIngredients() {
        int itemCount = blockToIngredients.getInt("blockCount");

        for (int i = 1; i <= itemCount; i++) {
            List<Integer> ingredients = blockToIngredients.getIntegerList("b" + i + ".ingredients");
            List<String> sourceBlocks = blockToIngredients.getStringList("b" + i + ".sourceBlocks");

            for (String str : sourceBlocks) {
                for (int ingredient : ingredients) {
                    GatheringManager.putBlockToIngredient(Material.valueOf(str), ingredient);
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
