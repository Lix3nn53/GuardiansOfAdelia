package io.github.lix3nn53.guardiansofadelia.items.enchanting;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ArmorGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSet;
import io.github.lix3nn53.guardiansofadelia.items.list.armors.ArmorSlot;
import io.github.lix3nn53.guardiansofadelia.items.list.weapons.WeaponSet;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnchantManager {

    private final static List<Float> MULTIPLIERS = new ArrayList<>();
    private final static HashMap<GearLevel, HashMap<WeaponGearType, List<Integer>>> weaponFullMap = new HashMap<>();
    private final static HashMap<GearLevel, HashMap<ArmorSlot, HashMap<ArmorGearType, List<Integer>>>> armorFullMap = new HashMap<>();
    private final static HashMap<GearLevel, List<Integer>> passiveFullMap = new HashMap<>();

    static {
        MULTIPLIERS.add(.09f); // +1
        MULTIPLIERS.add(.09f); // +2
        MULTIPLIERS.add(.09f); // +3
        MULTIPLIERS.add(.12f); // +4
        MULTIPLIERS.add(.12f); // +5
        MULTIPLIERS.add(.12f); // +6
        MULTIPLIERS.add(.15f); // +7
        MULTIPLIERS.add(.15f); // +8
        MULTIPLIERS.add(.15f); // +9
        MULTIPLIERS.add(.2f); // +10
        MULTIPLIERS.add(.2f); // +11
        MULTIPLIERS.add(.2f); // +12

        for (GearLevel gearLevel : GearLevel.values()) {
            int maxLevel = gearLevel.getMinLevel();

            int damage = StatUtils.getDamageItem(maxLevel);
            for (WeaponGearType weaponGearType : WeaponGearType.values()) {
                saveEnchantValueWeapon(weaponGearType, gearLevel, damage);
            }

            int defense = StatUtils.getDefenseItem(maxLevel);
            for (ArmorGearType armorGearType : ArmorGearType.values()) {
                for (ArmorSlot armorSlot : ArmorSlot.values()) {
                    saveEnchantValueArmor(armorGearType, armorSlot, gearLevel, defense);
                }
            }

            int passive = gearLevel.getMaxStatValue(true, true);
            saveEnchantValuePassive(gearLevel, passive);
        }
    }

    private static void saveEnchantValueWeapon(WeaponGearType gearType, GearLevel gearLevel, int baseValue) {
        HashMap<WeaponGearType, List<Integer>> gearTypeMap = new HashMap<>();
        if (weaponFullMap.containsKey(gearLevel)) {
            gearTypeMap = weaponFullMap.get(gearLevel);
        }
        List<Integer> values = new ArrayList<>();
        if (gearTypeMap.containsKey(gearType)) {
            values = gearTypeMap.get(gearType);
        }

        baseValue = WeaponSet.getElementDamage(gearType, baseValue);

        for (int enchantLevel = 1; enchantLevel <= 12; enchantLevel++) {
            int nextValue = getBonusValue(baseValue, enchantLevel);
            if (nextValue < 1) nextValue = 1;
            values.add(nextValue);
        }

        gearTypeMap.put(gearType, values);
        weaponFullMap.put(gearLevel, gearTypeMap);
    }

    private static void saveEnchantValueArmor(ArmorGearType gearType, ArmorSlot armorSlot, GearLevel gearLevel, int baseValue) {
        HashMap<ArmorSlot, HashMap<ArmorGearType, List<Integer>>> slotMap = new HashMap<>();
        if (armorFullMap.containsKey(gearLevel)) {
            slotMap = armorFullMap.get(gearLevel);
        }
        HashMap<ArmorGearType, List<Integer>> gearTypeMap = new HashMap<>();
        if (slotMap.containsKey(armorSlot)) {
            gearTypeMap = slotMap.get(armorSlot);
        }
        List<Integer> values = new ArrayList<>();
        if (gearTypeMap.containsKey(gearType)) {
            values = gearTypeMap.get(gearType);
        }

        baseValue = ArmorSet.getDefense(armorSlot, gearType, baseValue);

        for (int enchantLevel = 1; enchantLevel <= 12; enchantLevel++) {
            int nextValue = getBonusValue(baseValue, enchantLevel);
            if (nextValue < 1) nextValue = 1;
            values.add(nextValue);
        }

        gearTypeMap.put(gearType, values);
        slotMap.put(armorSlot, gearTypeMap);
        armorFullMap.put(gearLevel, slotMap);
    }

    private static void saveEnchantValuePassive(GearLevel gearLevel, int baseValue) {
        List<Integer> values = new ArrayList<>();
        if (passiveFullMap.containsKey(gearLevel)) {
            values = passiveFullMap.get(gearLevel);
        }

        for (int enchantLevel = 1; enchantLevel <= 12; enchantLevel++) {
            int nextValue = getBonusValue(baseValue, enchantLevel);
            if (nextValue < 1) nextValue = 1;
            values.add(nextValue);
        }

        passiveFullMap.put(gearLevel, values);
    }

    private static int getBonusValue(int value, int enchantLevel) {
        return (int) ((value * MULTIPLIERS.get(enchantLevel - 1)) + 0.5);
    }

    public static int getBonusWeapon(WeaponGearType gearType, GearLevel gearLevel, int enchantLevel) {
        if (enchantLevel < 1 || enchantLevel > 12) {
            GuardiansOfAdelia.getInstance().getLogger().warning("ERROR ENCHANT: ENCHANT LEVEL IS NOT BETWEEN 1-12");
            return -9999;
        }

        HashMap<WeaponGearType, List<Integer>> gearLevelMap = weaponFullMap.get(gearLevel);
        List<Integer> values = gearLevelMap.get(gearType);

        return values.get(enchantLevel - 1);
    }

    public static int getBonusArmor(ArmorGearType gearType, ArmorSlot armorSlot, GearLevel gearLevel, int enchantLevel) {
        if (enchantLevel < 1 || enchantLevel > 12) {
            GuardiansOfAdelia.getInstance().getLogger().warning("ERROR ENCHANT: ENCHANT LEVEL IS NOT BETWEEN 1-12");
            return -9999;
        }

        HashMap<ArmorSlot, HashMap<ArmorGearType, List<Integer>>> gearLevelMap = armorFullMap.get(gearLevel);
        HashMap<ArmorGearType, List<Integer>> armorSlotMap = gearLevelMap.get(armorSlot);
        List<Integer> values = armorSlotMap.get(gearType);

        return values.get(enchantLevel - 1);
    }

    public static int getBonusPassive(GearLevel gearLevel, int enchantLevel) {
        if (enchantLevel < 1 || enchantLevel > 12) {
            GuardiansOfAdelia.getInstance().getLogger().warning("ERROR ENCHANT: ENCHANT LEVEL IS NOT BETWEEN 1-12");
            return -9999;
        }

        List<Integer> values = passiveFullMap.get(gearLevel);

        return values.get(enchantLevel - 1);
    }

    public static int getEnchantLevel(ItemStack itemStack) {
        ItemMeta im = itemStack.getItemMeta();
        if (im.getDisplayName().contains("+12")) {
            return 12;
        } else if (im.getDisplayName().contains("+11")) {
            return 11;
        } else if (im.getDisplayName().contains("+10")) {
            return 10;
        } else if (im.getDisplayName().contains("+9")) {
            return 9;
        } else if (im.getDisplayName().contains("+8")) {
            return 8;
        } else if (im.getDisplayName().contains("+7")) {
            return 7;
        } else if (im.getDisplayName().contains("+6")) {
            return 6;
        } else if (im.getDisplayName().contains("+5")) {
            return 5;
        } else if (im.getDisplayName().contains("+4")) {
            return 4;
        } else if (im.getDisplayName().contains("+3")) {
            return 3;
        } else if (im.getDisplayName().contains("+2")) {
            return 2;
        } else if (im.getDisplayName().contains("+1")) {
            return 1;
        }
        return 0;
    }

    public static int getRequiredEnchantStoneLevel(int currentEnchantLevel) {
        if (currentEnchantLevel < 3) {
            return 1;
        } else if (currentEnchantLevel < 6) {
            return 2;
        } else if (currentEnchantLevel < 9) {
            return 3;
        }
        return 4;
    }

    public static float getChance(Player player, int currentEnchantLevel) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return 1;
        }

        float chance = 0;
        if (currentEnchantLevel == 0) {
            chance = 0.84f;
        } else if (currentEnchantLevel == 1) {
            chance = 0.8f;
        } else if (currentEnchantLevel == 2) {
            chance = 0.76f;
        } else if (currentEnchantLevel == 3) {
            chance = 0.72f;
        } else if (currentEnchantLevel == 4) {
            chance = 0.67f;
        } else if (currentEnchantLevel == 5) {
            chance = 0.6f;
        } else if (currentEnchantLevel == 6) {
            chance = 0.53f;
        } else if (currentEnchantLevel == 7) {
            chance = 0.47f;
        } else if (currentEnchantLevel == 8) {
            chance = 0.4f;
        } else if (currentEnchantLevel == 9) {
            chance = 0.32f;
        } else if (currentEnchantLevel == 10) {
            chance = 0.26f;
        } else if (currentEnchantLevel == 11) {
            chance = 0.2f;
        }

        if (BoostPremiumManager.isBoostActive(BoostPremium.ENCHANT)) {
            chance = BoostPremium.ENCHANT.applyTo(chance);
        }

        return chance;
    }

    public static float getSellGuiMultiplier(int enchantLevel) {
        if (enchantLevel == 1) {
            return 1;
        } else if (enchantLevel == 2) {
            return 1;
        } else if (enchantLevel == 3) {
            return 1;
        } else if (enchantLevel == 4) {
            return 1.1f;
        } else if (enchantLevel == 5) {
            return 1.2f;
        } else if (enchantLevel == 6) {
            return 1.3f;
        } else if (enchantLevel == 7) {
            return 1.4f;
        } else if (enchantLevel == 8) {
            return 1.6f;
        } else if (enchantLevel == 9) {
            return 1.8f;
        } else if (enchantLevel == 10) {
            return 2;
        } else if (enchantLevel == 11) {
            return 2.25f;
        } else if (enchantLevel == 12) {
            return 2.5f;
        }

        return 1;
    }
}
