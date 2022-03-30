package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
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
                return "Ranged Weaponsmith";
            case ARMOR_HEAVY:
                return "Heavy Armorsmith";
            case ARMOR_LIGHT:
                return "Light Armorsmith";
            case POTION:
                return "Alchemy";
            case FOOD:
                return "Cooking";
            case JEWEL:
                return "Jewelsmith";
            case ENCHANT_STONE:
                return "Enchant-Stone Crafting";
        }
        return "Melee Weaponsmith";
    }

    public List<ItemStack> getItemsToCraft(GearLevel gearLevel) {
        ItemTier tier = ItemTier.MYSTIC;
        String gearSet = "Craftsman's";
        List<ItemStack> itemStackList = new ArrayList<>();

        switch (this) {
            case WEAPON_MELEE:
                itemStackList = ItemPoolGenerator.generateWeapons(tier, gearLevel, true, true, false);
                break;
            case WEAPON_RANGED:
                itemStackList = ItemPoolGenerator.generateWeapons(tier, gearLevel, false, true, false);
                break;
            case ARMOR_HEAVY:
                itemStackList = ItemPoolGenerator.generateArmors(tier, gearLevel, true, true, false);
                break;
            case ARMOR_LIGHT:
                itemStackList = ItemPoolGenerator.generateArmors(tier, gearLevel, false, true, false);
                break;
            case POTION:
                itemStackList = ItemPoolGenerator.generatePotions(gearLevel);
                break;
            case FOOD:
                itemStackList = ItemPoolGenerator.generateFoods(gearLevel);
                break;
            case JEWEL:
                itemStackList = ItemPoolGenerator.generatePassives(tier, gearLevel, true, false);
                break;
        }
        return itemStackList;
    }
}
