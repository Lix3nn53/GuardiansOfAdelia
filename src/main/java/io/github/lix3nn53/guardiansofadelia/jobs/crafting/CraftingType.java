package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum CraftingType {
    WEAPON_MELEE,
    WEAPON_RANGED,
    ARMOR_HEAVY,
    ARMOR_LIGHT,
    POTION,
    FOOD,
    JEWEL,
    ENCHANT_STONE;

    public String getName() {
        switch (this) {
            case WEAPON_RANGED:
                return "Ranged Weapon Crafting";
            case ARMOR_HEAVY:
                return "Heavy Armor Crafting";
            case ARMOR_LIGHT:
                return "Light Armor Crafting";
            case POTION:
                return "Potion Crafting";
            case FOOD:
                return "Food Crafting";
            case JEWEL:
                return "Jewel Crafting";
            case ENCHANT_STONE:
                return "Enchant-Stone Crafting";
        }
        return "Melee Weapon";
    }

    public List<ItemStack> getItemsToCraft(int gearLevel) {
        ItemTier tier = ItemTier.MYSTIC;
        String gearSet = "Craftsman's";
        List<ItemStack> itemStackList = new ArrayList<>();

        switch (this) {
            case WEAPON_MELEE:
                itemStackList = ItemPoolGenerator.generateMeleeWeaponsNoStats(tier, gearLevel, 0, gearSet);
                break;
            case WEAPON_RANGED:
                itemStackList = ItemPoolGenerator.generateRangedWeaponsNoStats(tier, gearLevel, 0, gearSet);
                break;
            case ARMOR_HEAVY:
                itemStackList = ItemPoolGenerator.generateHeavyArmorsNoStats(tier, gearLevel, 0, gearSet);
                break;
            case ARMOR_LIGHT:
                itemStackList = ItemPoolGenerator.generateLightArmorsNoStats(tier, gearLevel, 0, gearSet);
                break;
            case POTION:
                itemStackList = ItemPoolGenerator.generatePotions(gearLevel);
                break;
            case FOOD:
                itemStackList = ItemPoolGenerator.generateFoods(gearLevel);
                break;
            case JEWEL:
                itemStackList = ItemPoolGenerator.generatePassivesNoStats(tier, gearLevel, 0, gearSet);
                break;
        }
        return itemStackList;
    }
}
