package io.github.lix3nn53.guardiansofadelia.Items.list.weapons;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeaponManager {

    private final static HashMap<String, List<WeaponItemTemplate>> gearTypeAndLevelToWeapons = new HashMap<>();

    public static ItemStack get(RPGGearType gearType, int gearLevel, int itemIndex, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String key = gearType.toString() + gearLevel;
        List<WeaponItemTemplate> templates = gearTypeAndLevelToWeapons.get(key);

        AttackSpeed attackSpeed = gearType.getAttackSpeed();

        WeaponItemTemplate template = templates.get(itemIndex);

        Material material = gearType.getMaterial();
        String name = template.getName();
        int customModelData = template.getCustomModelData();
        int level = template.getRequiredLevel();
        int mainDamage = template.getDamage();

        WeaponType weaponType = gearType.getWeaponType();
        double bonusPercent = tier.getBonusMultiplier();

        if (weaponType.equals(WeaponType.MELEE)) {
            return new WeaponMelee(name, tier, itemTag, material, customModelData, level, gearType, mainDamage, bonusPercent,
                    attackSpeed, minStatValue, maxStatValue, minNumberofStats).getItemStack();
        } else if (weaponType.equals(WeaponType.RANGED)) {
            int rangedDamage = (int) ((mainDamage * 0.8) + 0.5); //do not add bonusPercent to rangedDamage because WeaponRanged constructor already does that
            mainDamage = (int) ((mainDamage * bonusPercent) + 0.5); //add bonusPercent to meleeDamage because WeaponRanged constructor adds only to rangedDamage

            return new WeaponRanged(name, tier, itemTag, material, customModelData, level, gearType, mainDamage, rangedDamage, bonusPercent,
                    attackSpeed, minStatValue, maxStatValue, minNumberofStats).getItemStack();
        } else if (weaponType.equals(WeaponType.MAGICAL)) {
            int meleeDamage = mainDamage / 4;

            return new WeaponMagical(name, tier, itemTag, material, customModelData, level, gearType, meleeDamage, mainDamage, bonusPercent,
                    attackSpeed, minStatValue, maxStatValue, minNumberofStats).getItemStack();
        }

        return null;
    }

    public static void add(WeaponItemTemplate weaponItemTemplate) {
        RPGGearType gearType = weaponItemTemplate.getGearType();
        int gearLevel = GearLevel.getGearLevel(weaponItemTemplate.getRequiredLevel());

        String key = gearType.toString() + gearLevel;

        List<WeaponItemTemplate> list = new ArrayList<>();
        if (gearTypeAndLevelToWeapons.containsKey(key)) {
            list = gearTypeAndLevelToWeapons.get(key);
        }
        list.add(weaponItemTemplate);

        gearTypeAndLevelToWeapons.put(key, list);
    }
}
