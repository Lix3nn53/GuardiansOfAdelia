package io.github.lix3nn53.guardiansofadelia.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum PrizeChestType {
    WEAPON_MELEE,
    WEAPON_RANGED,
    ARMOR_HEAVY, // SHIELD AND TWO ARMORS
    ARMOR_LIGHT, // THREE ARMORS
    JEWELRY,
    PET;

    public ItemStack getChestItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();

        switch (this) {
            case WEAPON_MELEE:
                itemMeta.setCustomModelData(18);
                break;
            case WEAPON_RANGED:
                itemMeta.setCustomModelData(18);
                break;
            case ARMOR_HEAVY:
                itemMeta.setCustomModelData(16);
                break;
            case ARMOR_LIGHT:
                itemMeta.setCustomModelData(16);
                break;
            case JEWELRY:
                itemMeta.setCustomModelData(17);
                break;
            case PET:
                itemMeta.setCustomModelData(17);
                break;
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack getChestIconItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();

        switch (this) {
            case WEAPON_MELEE:
                itemMeta.setCustomModelData(20);
                break;
            case WEAPON_RANGED:
                itemMeta.setCustomModelData(23);
                break;
            case ARMOR_HEAVY:
                itemMeta.setCustomModelData(21);
                break;
            case ARMOR_LIGHT:
                itemMeta.setCustomModelData(22);
                break;
            case JEWELRY:
                itemStack.setType(Material.SHEARS);
                itemMeta.setCustomModelData(3);
                break;
            case PET:
                itemMeta.setCustomModelData(19);
                break;
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public String toString() {
        switch (this) {
            case WEAPON_MELEE:
                return "Melee Weapon";
            case WEAPON_RANGED:
                return "Ranged Weapon";
            case ARMOR_HEAVY:
                return "Heavy Armor";
            case ARMOR_LIGHT:
                return "Light Armor";
            case JEWELRY:
                return "Jewelry";
            case PET:
                return "Pet";
        }

        return "PrizeChestType ERROR";
    }
}
