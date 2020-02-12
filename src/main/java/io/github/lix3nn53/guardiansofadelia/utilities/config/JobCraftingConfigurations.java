package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.ArmorType;
import io.github.lix3nn53.guardiansofadelia.Items.list.armors.Armors;
import io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems.PassiveItemList;
import io.github.lix3nn53.guardiansofadelia.Items.list.weapons.Weapons;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobCraftingConfigurations {

    private final static HashMap<CraftingType, FileConfiguration> craftingConfigurations = new HashMap<>();

    static void createConfigs() {
        createCraftingRecipes();
    }

    static void loadConfigs() {
        loadCraftingRecipesWeapon();
        loadCraftingRecipesArmor();
        loadCraftingRecipesJewel();
        loadCraftingRecipesPotion();
        loadCraftingRecipesFood();
        loadCraftingRecipesEnchantStone();
    }

    private static void createCraftingRecipes() {
        for (CraftingType craftingType : CraftingType.values()) {
            String fileName = craftingType.toString() + ".yml";
            String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "crafting";
            File customConfigFile = new File(filePath, fileName);
            if (!customConfigFile.exists()) {
                customConfigFile.getParentFile().mkdirs();

                try {
                    customConfigFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            try {
                yamlConfiguration.load(customConfigFile);
                craftingConfigurations.put(craftingType, yamlConfiguration);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadCraftingRecipesWeapon() {
        for (CraftingType craftingType : CraftingType.values()) {
            if (!(craftingType.equals(CraftingType.WEAPON_RANGED) || craftingType.equals(CraftingType.WEAPON_MELEE)))
                continue;
            FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);
            int levelCount = fileConfiguration.getInt("levelCount");

            for (int level = 1; level <= levelCount; level++) {
                int itemSetCount = fileConfiguration.getInt("level" + level + ".itemSetCount");

                for (int itemSet = 1; itemSet <= itemSetCount; itemSet++) {
                    List<String> classesStr = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".classes");
                    int gearLevel = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".gearLevel");
                    int index = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".index");
                    String tierStr = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tier");
                    ItemTier itemTier = ItemTier.valueOf(tierStr);
                    String tag = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tag");
                    List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                    List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");

                    for (String rpgClassStr : classesStr) {
                        RPGClass rpgClass = RPGClass.valueOf(rpgClassStr);

                        int minNumberOfStatsNormal = itemTier.getMinNumberOfStatsNormal();
                        int minStatValue = GearLevel.getMinStatValue(gearLevel);
                        int maxStatValue = GearLevel.getMaxStatValue(gearLevel);
                        ItemStack weapon = Weapons.getWeapon(rpgClass, gearLevel, index, itemTier, tag, minStatValue, maxStatValue, minNumberOfStatsNormal);

                        CraftingManager.putCraftingTypeAndLevelToCraftingLine(weapon, craftingType, level, ingredients, ingredientAmounts);
                    }
                }
            }
        }
    }

    private static void loadCraftingRecipesArmor() {
        for (CraftingType craftingType : CraftingType.values()) {
            if (!(craftingType.equals(CraftingType.ARMOR_HEAVY) || craftingType.equals(CraftingType.ARMOR_LIGHT)))
                continue;
            FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);
            int levelCount = fileConfiguration.getInt("levelCount");

            for (int level = 1; level <= levelCount; level++) {
                int itemSetCount = fileConfiguration.getInt("level" + level + ".itemSetCount");

                for (int itemSet = 1; itemSet <= itemSetCount; itemSet++) {
                    List<String> classesStr = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".classes");
                    int gearLevel = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".gearLevel");
                    int index = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".index");
                    String tierStr = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tier");
                    ItemTier itemTier = ItemTier.valueOf(tierStr);
                    String tag = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tag");
                    List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                    List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");

                    for (String rpgClassStr : classesStr) {
                        RPGClass rpgClass = RPGClass.valueOf(rpgClassStr);

                        int minNumberOfStatsNormal = itemTier.getMinNumberOfStatsNormal();
                        int minStatValue = GearLevel.getMinStatValue(gearLevel);
                        int maxStatValue = GearLevel.getMaxStatValue(gearLevel);

                        for (ArmorType armorType : ArmorType.values()) {
                            ItemStack armor = Armors.getArmor(armorType, rpgClass, gearLevel, index, itemTier, tag, minStatValue, maxStatValue, minNumberOfStatsNormal);

                            double multiplier = 1;
                            if (armorType.equals(ArmorType.LEGGINGS)) {
                                multiplier = 0.75;
                            } else if (armorType.equals(ArmorType.BOOTS) || armorType.equals(ArmorType.HELMET)) {
                                multiplier = 0.5;
                            }

                            List<Integer> newList = multipleListWith(ingredientAmounts, multiplier);

                            CraftingManager.putCraftingTypeAndLevelToCraftingLine(armor, craftingType, level, ingredients, newList);
                        }
                    }
                }
            }
        }
    }

    private static List<Integer> multipleListWith(List<Integer> list, double multiplier) {
        if (multiplier == 1) return list;

        List<Integer> newList = new ArrayList<>();

        for (int i : list) {
            newList.add((int) (i * multiplier + 0.5));
        }

        return newList;
    }

    private static void loadCraftingRecipesJewel() {
        CraftingType craftingType = CraftingType.JEWEL;
        FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);
        int levelCount = fileConfiguration.getInt("levelCount");

        for (int level = 1; level <= levelCount; level++) {
            int itemSetCount = fileConfiguration.getInt("level" + level + ".itemSetCount");

            for (int itemSet = 1; itemSet <= itemSetCount; itemSet++) {
                List<String> slotTypesStr = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".slotTypes");
                int gearLevel = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".gearLevel");
                int index = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".index");
                String tierStr = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tier");
                ItemTier itemTier = ItemTier.valueOf(tierStr);
                String tag = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tag");
                List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");

                for (String rpgSlotTypeStr : slotTypesStr) {
                    RPGSlotType rpgSlotType = RPGSlotType.valueOf(rpgSlotTypeStr);

                    int minNumberOfStatsPassive = itemTier.getMinNumberOfStatsPassive();
                    int minStatValue = GearLevel.getMinStatValue(gearLevel);
                    int maxStatValue = GearLevel.getMaxStatValue(gearLevel);
                    ItemStack passive = PassiveItemList.get(gearLevel, index, rpgSlotType, itemTier, tag, minStatValue, maxStatValue, minNumberOfStatsPassive);

                    CraftingManager.putCraftingTypeAndLevelToCraftingLine(passive, craftingType, level, ingredients, ingredientAmounts);
                }
            }
        }
    }

    private static void loadCraftingRecipesPotion() {
        CraftingType craftingType = CraftingType.POTION;
        FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);
        int levelCount = fileConfiguration.getInt("levelCount");

        for (int level = 1; level <= levelCount; level++) {
            int itemSetCount = fileConfiguration.getInt("level" + level + ".itemSetCount");

            for (int itemSet = 1; itemSet <= itemSetCount; itemSet++) {
                List<String> consumableTypesStr = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".consumableTypes");
                int skillLevel = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".skillLevel");
                int uses = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".uses");
                List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");

                for (String consumableTypeStr : consumableTypesStr) {
                    Consumable consumable = Consumable.valueOf(consumableTypeStr);

                    ItemStack itemStack = consumable.getItemStack(skillLevel, uses);

                    CraftingManager.putCraftingTypeAndLevelToCraftingLine(itemStack, craftingType, level, ingredients, ingredientAmounts);
                }
            }
        }
    }

    private static void loadCraftingRecipesFood() {
        CraftingType craftingType = CraftingType.FOOD;
        FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);
        int levelCount = fileConfiguration.getInt("levelCount");

        for (int level = 1; level <= levelCount; level++) {
            int itemSetCount = fileConfiguration.getInt("level" + level + ".itemSetCount");

            for (int itemSet = 1; itemSet <= itemSetCount; itemSet++) {
                List<String> consumableTypesStr = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".consumableTypes");
                int skillLevel = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".skillLevel");
                int uses = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".uses");
                List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");

                for (String consumableTypeStr : consumableTypesStr) {
                    Consumable consumable = Consumable.valueOf(consumableTypeStr);

                    ItemStack itemStack = consumable.getItemStack(skillLevel, uses);

                    CraftingManager.putCraftingTypeAndLevelToCraftingLine(itemStack, craftingType, level, ingredients, ingredientAmounts);
                }
            }
        }
    }

    private static void loadCraftingRecipesEnchantStone() {
        CraftingType craftingType = CraftingType.ENCHANT_STONE;
        FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);
        int levelCount = fileConfiguration.getInt("levelCount");

        for (int level = 1; level <= levelCount; level++) {
            int itemSetCount = fileConfiguration.getInt("level" + level + ".itemSetCount");

            for (int itemSet = 1; itemSet <= itemSetCount; itemSet++) {
                String stoneTier = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".stoneTier");
                int stoneAmount = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".stoneAmount");
                List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");

                EnchantStone enchantStone = EnchantStone.valueOf(stoneTier);
                ItemStack itemStack = enchantStone.getItemSTack(stoneAmount);

                CraftingManager.putCraftingTypeAndLevelToCraftingLine(itemStack, craftingType, level, ingredients, ingredientAmounts);
            }
        }
    }
}
