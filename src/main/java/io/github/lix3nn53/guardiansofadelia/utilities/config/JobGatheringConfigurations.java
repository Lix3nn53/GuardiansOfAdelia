package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.jobs.gathering.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.EulerAngle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

    static void writeConfigs() {
        writeGatheringModels();
    }

    private static void loadIngredients() {
        for (int i = 1; i <= 9999; i++) {
            if (!ingredientsConfig.contains("i" + i)) break;

            String nameStr = ingredientsConfig.getString("i" + i + ".name");
            Material material = Material.valueOf(ingredientsConfig.getString("i" + i + ".material"));
            int customModelData = ingredientsConfig.contains("i" + i + ".customModelData") ? ingredientsConfig.getInt("i" + i + ".customModelData") : 0;
            int ingredientLevel = ingredientsConfig.getInt("i" + i + ".ingredientLevel");
            List<String> jobsCanUse = ingredientsConfig.getStringList("i" + i + ".jobsCanUse");
            List<String> extraText = ingredientsConfig.contains("i" + i + ".extraText") ? ingredientsConfig.getStringList("i" + i + ".extraText") : null;

            Color potionColor = Color.fromRGB(ingredientsConfig.getInt("i" + i + ".potionColor"));

            String name = ChatColor.translateAlternateColorCodes('&', nameStr);
            int maxAmountPerGather = ingredientsConfig.getInt("i" + i + ".maxAmountPerGather");
            double dropRate = ingredientsConfig.contains("i" + i + ".dropRate") ? ingredientsConfig.getDouble("i" + i + ".dropRate") : 1;
            boolean enchant = ingredientsConfig.getBoolean("i" + i + ".enchant");

            Ingredient ingredient = new Ingredient(i, material, name, ingredientLevel, jobsCanUse, extraText, customModelData, potionColor, maxAmountPerGather, dropRate, enchant);

            GatheringManager.putIngredient(i, ingredient);
        }
    }

    private static void loadGatheringModels() {
        for (int i = 1; i <= 999; i++) {
            if (!gatheringModelsConfig.contains("i" + i)) break;

            String nameStr = gatheringModelsConfig.getString("i" + i + ".name");
            String name = ChatColor.translateAlternateColorCodes('&', nameStr);

            Material material = gatheringModelsConfig.contains("i" + i + ".material") ? Material.valueOf(gatheringModelsConfig.getString("i" + i + ".material")) : null;
            int customModelData = gatheringModelsConfig.getInt("i" + i + ".customModelData");
            int cooldownCustomModelData = gatheringModelsConfig.getInt("i" + i + ".cooldownCustomModelData");
            GatheringToolType gatheringToolType = GatheringToolType.valueOf(gatheringModelsConfig.getString("i" + i + ".gatheringToolType"));
            GatheringToolTier minGatheringToolTier = GatheringToolTier.valueOf(gatheringModelsConfig.getString("i" + i + ".minGatheringToolTier"));
            List<Integer> ingredients = gatheringModelsConfig.getIntegerList("i" + i + ".ingredients");
            boolean disguise = gatheringModelsConfig.contains("i" + i + ".disguise") && gatheringModelsConfig.getBoolean("i" + i + ".disguise");

            GatheringModelData gatheringModelData = new GatheringModelData(customModelData, cooldownCustomModelData, name, material, disguise, ingredients, gatheringToolType, minGatheringToolTier);
            GatheringManager.putGatheringModelData(i, gatheringModelData);

            for (int l = 1; l < 999; l++) {
                if (!gatheringModelsConfig.contains("i" + i + ".loc" + l)) break;

                String worldString = gatheringModelsConfig.getString("i" + i + ".loc" + l + ".world");
                World world = Bukkit.getWorld(worldString);
                double x = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".x");
                double y = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".y");
                double z = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".z");
                Location location = new Location(world, x, y, z);

                EulerAngle rotation = new EulerAngle(0, 0, 0);

                if (gatheringModelsConfig.contains("i" + i + ".loc" + l + ".rotation.x")) {
                    double x1 = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".rotation.x");
                    double y1 = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".rotation.y");
                    double z1 = gatheringModelsConfig.getDouble("i" + i + ".loc" + l + ".rotation.z");
                    rotation = new EulerAngle(x1, y1, z1);
                }

                GatheringModelState gatheringModelState = new GatheringModelState(i, location, rotation);
                GatheringManager.putGatheringModelState(gatheringModelState);
            }
        }
    }

    private static void writeGatheringModels() {
        HashMap<Integer, GatheringModelData> modelIdToModelData = GatheringManager.getModelIdToModelData();

        for (int id : modelIdToModelData.keySet()) {
            GatheringModelData gatheringModelData = modelIdToModelData.get(id);

            String title = gatheringModelData.getTitle();
            String titleColor = title.replaceAll(ChatColor.COLOR_CHAR + "", "&");
            gatheringModelsConfig.set("i" + id + ".name", titleColor);

            Material material = gatheringModelData.getMaterial();
            if (material != null) {
                gatheringModelsConfig.set("i" + id + ".material", material.name());
                int customModelData = gatheringModelData.getCustomModelData();
                gatheringModelsConfig.set("i" + id + ".customModelData", customModelData);
                int cooldownCustomModelData = gatheringModelData.getCooldownCustomModelData();
                gatheringModelsConfig.set("i" + id + ".cooldownCustomModelData", cooldownCustomModelData);
            }

            List<Integer> ingredients = gatheringModelData.getIngredients();
            gatheringModelsConfig.set("i" + id + ".ingredients", ingredients);
            GatheringToolType gatheringToolType = gatheringModelData.getGatheringToolType();
            gatheringModelsConfig.set("i" + id + ".gatheringToolType", gatheringToolType.name());
            GatheringToolTier minGatheringToolTier = gatheringModelData.getMinGatheringToolTier();
            gatheringModelsConfig.set("i" + id + ".minGatheringToolTier", minGatheringToolTier.name());
        }

        HashMap<String, List<GatheringModelState>> chunkKeyToGatheringModels = GatheringManager.getChunkKeyToGatheringModels();

        HashMap<Integer, Integer> modelIdToLocationCount = new HashMap<>();

        for (String chunkKey : chunkKeyToGatheringModels.keySet()) {
            List<GatheringModelState> gatheringModelStates = chunkKeyToGatheringModels.get(chunkKey);
            for (GatheringModelState gatheringModelState : gatheringModelStates) {
                int id = gatheringModelState.getId();

                int count = 1;
                if (modelIdToLocationCount.containsKey(id)) {
                    int lastCount = modelIdToLocationCount.get(id);
                    count = lastCount + 1;
                }
                modelIdToLocationCount.put(id, count);

                Location baseLocation = gatheringModelState.getBaseLocation();

                World world = baseLocation.getWorld();
                String worldName = world.getName();

                double x = baseLocation.getX();
                double y = baseLocation.getY();
                double z = baseLocation.getZ();

                gatheringModelsConfig.set("i" + id + ".loc" + count + ".world", worldName);
                gatheringModelsConfig.set("i" + id + ".loc" + count + ".x", x);
                gatheringModelsConfig.set("i" + id + ".loc" + count + ".y", y);
                gatheringModelsConfig.set("i" + id + ".loc" + count + ".z", z);

                EulerAngle rotation = gatheringModelState.getRotation();
                double x1 = rotation.getX();
                double y1 = rotation.getY();
                double z1 = rotation.getZ();
                if (x1 != 0 || y1 != 0 || z1 != 0) {
                    gatheringModelsConfig.set("i" + id + ".loc" + count + ".rotation.x", x1);
                    gatheringModelsConfig.set("i" + id + ".loc" + count + ".rotation.y", y1);
                    gatheringModelsConfig.set("i" + id + ".loc" + count + ".rotation.z", z1);
                }
            }
        }

        try {
            gatheringModelsConfig.save(JobGatheringConfigurations.filePath + File.separator + "GatheringModels.yml");
        } catch (IOException e) {
            e.printStackTrace();
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
