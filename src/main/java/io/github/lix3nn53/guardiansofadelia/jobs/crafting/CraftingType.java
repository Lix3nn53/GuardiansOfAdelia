package io.github.lix3nn53.guardiansofadelia.jobs.crafting;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantStone;
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
                return "Ranged Weapon";
            case ARMOR_HEAVY:
                return "Heavy Armor";
            case ARMOR_LIGHT:
                return "Light Armor";
            case POTION:
                return "Potion";
            case FOOD:
                return "Food";
            case JEWEL:
                return "Jewel";
            case ENCHANT_STONE:
                return "Enchant Stone";
        }
        return "Melee Weapon";
    }

    public List<ItemStack> getItemsToCraft(int gearLevel) {
        ItemTier tier = ItemTier.MYSTIC;
        String itemTag = "Weaponsmith's";
        List<ItemStack> itemStackList = new ArrayList<>();

        switch (this) {
            case WEAPON_MELEE:
                itemStackList = ItemPoolGenerator.generateMeleeWeaponsNoStats(tier, itemTag, gearLevel, 0);
                break;
            case WEAPON_RANGED:
                itemStackList = ItemPoolGenerator.generateRangedWeaponsNoStats(tier, itemTag, gearLevel, 0);
                break;
            case ARMOR_HEAVY:
                itemTag = "Armorsmith's";
                itemStackList = ItemPoolGenerator.generateHeavyArmorsNoStats(tier, itemTag, gearLevel, 0);
                break;
            case ARMOR_LIGHT:
                itemTag = "Armorsmith's";
                itemStackList = ItemPoolGenerator.generateLightArmorsNoStats(tier, itemTag, gearLevel, 0);
                break;
            case POTION:
                itemStackList = ItemPoolGenerator.generatePotions(gearLevel);
                break;
            case FOOD:
                itemStackList = ItemPoolGenerator.generateFoods(gearLevel);
                break;
            case JEWEL:
                itemTag = "Jeweller's";
                itemStackList = ItemPoolGenerator.generatePassivesNoStats(tier, itemTag, gearLevel, 0);
                break;
            case ENCHANT_STONE:
                itemStackList = new ArrayList<>();
                itemStackList.add(EnchantStone.TIER_ONE.getItemSTack(1));
                itemStackList.add(EnchantStone.TIER_TWO.getItemSTack(1));
                itemStackList.add(EnchantStone.TIER_THREE.getItemSTack(1));
                itemStackList.add(EnchantStone.TIER_FOUR.getItemSTack(1));
                break;
        }
        return itemStackList;
    }
}
