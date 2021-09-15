package io.github.lix3nn53.guardiansofadelia.utilities.config;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.items.Consumable;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.enchanting.EnchantStone;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorManager;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.list.passiveItems.PassiveManager;
import io.github.lix3nn53.guardiansofadelia.items.list.shields.ShieldManager;
import io.github.lix3nn53.guardiansofadelia.items.list.weapons.WeaponManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingManager;
import io.github.lix3nn53.guardiansofadelia.jobs.crafting.CraftingType;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobCraftingConfigurations {

    private final static HashMap<CraftingType, FileConfiguration> craftingConfigurations = new HashMap<>();
    private static final String filePath = ConfigManager.DATA_FOLDER + File.separator + "jobs" + File.separator + "crafting";

    static void createConfigs() {
        for (CraftingType craftingType : CraftingType.values()) {
            String fileName = craftingType.toString() + ".yml";

            YamlConfiguration config = ConfigurationUtils.createConfig(filePath, fileName);
            craftingConfigurations.put(craftingType, config);
        }
    }

    static void loadConfigs() {
        loadCraftingRecipesWeapon();
        loadCraftingRecipesArmor();
        loadCraftingRecipesJewel();
        loadCraftingRecipesPotion();
        loadCraftingRecipesFood();
        loadCraftingRecipesEnchantStone();
    }

    private static void loadCraftingRecipesWeapon() {
        for (CraftingType craftingType : CraftingType.values()) {
            if (!(craftingType.equals(CraftingType.WEAPON_RANGED) || craftingType.equals(CraftingType.WEAPON_MELEE)))
                continue;
            FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);
            for (int level = 1; level <= 999; level++) {
                if (!fileConfiguration.contains("level" + level)) break;

                for (int itemSet = 1; itemSet <= 999; itemSet++) {
                    if (!fileConfiguration.contains("level" + level + ".itemSet" + itemSet)) break;

                    List<String> gearTypeStrList = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".weaponGearType");
                    int gearLevelIndex = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".gearLevel");
                    GearLevel gearLevel = GearLevel.values()[gearLevelIndex];
                    int index = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".index");
                    String tierStr = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tier");
                    ItemTier itemTier = ItemTier.valueOf(tierStr);
                    List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                    List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");
                    String gearSet = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".gearSet");

                    for (String gearTypeStr : gearTypeStrList) {
                        WeaponGearType gearType = WeaponGearType.valueOf(gearTypeStr);

                        ItemStack weapon = WeaponManager.get(gearType, gearLevel, itemTier, true, gearSet).get(index);

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

            for (int level = 1; level <= 999; level++) {
                if (!fileConfiguration.contains("level" + level)) break;

                for (int itemSet = 1; itemSet <= 999; itemSet++) {
                    if (!fileConfiguration.contains("level" + level + ".itemSet" + itemSet)) break;

                    List<String> gearTypeStrList = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".armorGearType");
                    int gearLevelIndex = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".gearLevel");
                    GearLevel gearLevel = GearLevel.values()[gearLevelIndex];
                    int index = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".index");
                    String tierStr = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tier");
                    ItemTier itemTier = ItemTier.valueOf(tierStr);
                    List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                    List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");
                    String gearSet = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".gearSet");

                    for (String gearTypeStr : gearTypeStrList) {
                        ArmorGearType gearType = ArmorGearType.valueOf(gearTypeStr);

                        for (ArmorSlot armorSlot : ArmorSlot.values()) {
                            ItemStack armor = ArmorManager.get(armorSlot, gearType, gearLevel, itemTier, true, gearSet).get(index);

                            if (armor == null) {
                                GuardiansOfAdelia.getInstance().getLogger().info("CRAFTING RECIPE NULL ARMOR");
                                continue;
                            }

                            double multiplier = 1;
                            if (armorSlot.equals(ArmorSlot.LEGGINGS)) {
                                multiplier = 0.75;
                            } else if (armorSlot.equals(ArmorSlot.BOOTS) || armorSlot.equals(ArmorSlot.HELMET)) {
                                multiplier = 0.5;
                            }

                            List<Integer> newList = multipleListWith(ingredientAmounts, multiplier);

                            CraftingManager.putCraftingTypeAndLevelToCraftingLine(armor, craftingType, level, ingredients, newList);
                        }
                    }

                    // Add shield
                    if (craftingType.equals(CraftingType.ARMOR_HEAVY)) {
                        ShieldGearType shieldGear = ShieldGearType.SHIELD;
                        ItemStack shield = ShieldManager.get(shieldGear, gearLevel, itemTier, true, gearSet).get(index);
                        double multiplier = 1;

                        List<Integer> newList = multipleListWith(ingredientAmounts, multiplier);

                        CraftingManager.putCraftingTypeAndLevelToCraftingLine(shield, craftingType, level, ingredients, newList);
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

        for (int level = 1; level <= 999; level++) {
            if (!fileConfiguration.contains("level" + level)) break;

            for (int itemSet = 1; itemSet <= 999; itemSet++) {
                if (!fileConfiguration.contains("level" + level + ".itemSet" + itemSet)) break;

                List<String> slotTypesStr = fileConfiguration.getStringList("level" + level + ".itemSet" + itemSet + ".slotTypes");
                int gearLevelIndex = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".gearLevel");
                GearLevel gearLevel = GearLevel.values()[gearLevelIndex];
                int index = fileConfiguration.getInt("level" + level + ".itemSet" + itemSet + ".index");
                String tierStr = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tier");
                ItemTier itemTier = ItemTier.valueOf(tierStr);
                List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");
                String gearSet = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".gearSet");

                for (String rpgSlotTypeStr : slotTypesStr) {
                    RPGSlotType rpgSlotType = RPGSlotType.valueOf(rpgSlotTypeStr);

                    ItemStack passive = PassiveManager.get(gearLevel, rpgSlotType, itemTier, true, gearSet).get(index);

                    CraftingManager.putCraftingTypeAndLevelToCraftingLine(passive, craftingType, level, ingredients, ingredientAmounts);
                }
            }
        }
    }

    private static void loadCraftingRecipesPotion() {
        CraftingType craftingType = CraftingType.POTION;
        FileConfiguration fileConfiguration = craftingConfigurations.get(craftingType);

        for (int level = 1; level <= 999; level++) {
            if (!fileConfiguration.contains("level" + level)) break;

            for (int itemSet = 1; itemSet <= 999; itemSet++) {
                if (!fileConfiguration.contains("level" + level + ".itemSet" + itemSet)) break;

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

        for (int level = 1; level <= 999; level++) {
            if (!fileConfiguration.contains("level" + level)) break;

            for (int itemSet = 1; itemSet <= 999; itemSet++) {
                if (!fileConfiguration.contains("level" + level + ".itemSet" + itemSet)) break;

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

        for (int level = 1; level <= 999; level++) {
            if (!fileConfiguration.contains("level" + level)) break;

            for (int itemSet = 1; itemSet <= 999; itemSet++) {
                if (!fileConfiguration.contains("level" + level + ".itemSet" + itemSet)) break;

                String tier = fileConfiguration.getString("level" + level + ".itemSet" + itemSet + ".tier");
                EnchantStone enchantStone = EnchantStone.valueOf(tier);

                ItemStack itemStack = enchantStone.getItemStack(1);

                List<Integer> ingredients = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredients");
                List<Integer> ingredientAmounts = fileConfiguration.getIntegerList("level" + level + ".itemSet" + itemSet + ".ingredientAmounts");

                CraftingManager.putCraftingTypeAndLevelToCraftingLine(itemStack, craftingType, level, ingredients, ingredientAmounts);
            }
        }
    }
}
