package io.github.lix3nn53.guardiansofadelia.Items.list;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum MonsterItem {
    SWORD_DARK,
    SWORD_WOODEN,
    SWORD_STEEL,
    SWORD_BROAD,
    SWORD_FIRE,
    DAGGER_WOOD,
    DAGGER_CRIMSON,
    DAGGER_DARKEST,
    SPEAR_STEEL,
    SPEAR_RED,
    KATANA,
    STAR,
    BOW_DARK,
    BOW_WOODEN,
    BOW_SATET,
    CROSSBOW_DARK,
    PIRATE_PISTOL,
    PIRATE_HAT,
    AXE_WOODEN,
    AXE_BATTLE,
    AXE_FROST,
    AXE_TITAN,
    STAFF_WOODEN,
    STAFF_LEAF,
    STAFF_BONE,
    STAFF_FIRE,
    STAFF_OCEAN,
    STAFF_MOON,
    HELMET_GOLDEN,
    HELMET_CHAINMAIL,
    HELMET_IRON,
    CHESTPLATE_IRON,
    CHESTPLATE_GOLDEN,
    LEGGINGS_IRON,
    LEGGINGS_GOLDEN,
    MACE_DARK,
    BOOTS_IRON,
    BOOTS_GOLDEN,
    SHIELD_WOODEN,
    SHIELD_KNIGHT,
    SHIELD_FIRE,
    SHIELD_DARK;

    public ItemStack getItem(int rangedDamage) {
        switch (this) {
            case SPEAR_STEEL:
                ItemStack itemStack = new ItemStack(Material.TRIDENT);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(2);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SPEAR_RED:
                itemStack = new ItemStack(Material.TRIDENT);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(3);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case MACE_DARK:
                itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(6);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SWORD_DARK:
                itemStack = new ItemStack(Material.DIAMOND_SWORD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(6);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SWORD_WOODEN:
                itemStack = new ItemStack(Material.DIAMOND_SWORD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(1);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SWORD_STEEL:
                itemStack = new ItemStack(Material.DIAMOND_SWORD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(2);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SWORD_BROAD:
                itemStack = new ItemStack(Material.DIAMOND_SWORD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(4);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SWORD_FIRE:
                itemStack = new ItemStack(Material.DIAMOND_SWORD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(8);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case BOW_DARK:
                itemStack = new ItemStack(Material.BOW);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(8);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, itemStack);
                return itemStack;
            case BOW_SATET:
                itemStack = new ItemStack(Material.BOW);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(3);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, itemStack);
                return itemStack;
            case BOW_WOODEN:
                itemStack = new ItemStack(Material.BOW);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(1);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, itemStack);
                return itemStack;
            case CROSSBOW_DARK:
                itemStack = new ItemStack(Material.CROSSBOW);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(8);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, itemStack);
                return itemStack;
            case PIRATE_PISTOL:
                itemStack = new ItemStack(Material.BOW);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(16);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case PIRATE_HAT:
                itemStack = new ItemStack(Material.IRON_SHOVEL);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(8);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case AXE_WOODEN:
                itemStack = new ItemStack(Material.DIAMOND_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(1);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case AXE_BATTLE:
                itemStack = new ItemStack(Material.DIAMOND_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(2);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case AXE_FROST:
                itemStack = new ItemStack(Material.DIAMOND_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(4);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case AXE_TITAN:
                itemStack = new ItemStack(Material.DIAMOND_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(14);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case STAFF_WOODEN:
                itemStack = new ItemStack(Material.DIAMOND_SHOVEL);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(1);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case STAFF_LEAF:
                itemStack = new ItemStack(Material.DIAMOND_SHOVEL);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(2);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case STAFF_BONE:
                itemStack = new ItemStack(Material.DIAMOND_SHOVEL);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(3);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case STAFF_FIRE:
                itemStack = new ItemStack(Material.DIAMOND_SHOVEL);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(4);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case STAFF_OCEAN:
                itemStack = new ItemStack(Material.DIAMOND_SHOVEL);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(10);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case STAFF_MOON:
                itemStack = new ItemStack(Material.DIAMOND_SHOVEL);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(12);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case HELMET_GOLDEN:
                itemStack = new ItemStack(Material.GOLDEN_HELMET);
                return itemStack;
            case HELMET_CHAINMAIL:
                itemStack = new ItemStack(Material.CHAINMAIL_HELMET);
                return itemStack;
            case HELMET_IRON:
                itemStack = new ItemStack(Material.IRON_HELMET);
                return itemStack;
            case CHESTPLATE_IRON:
                itemStack = new ItemStack(Material.IRON_CHESTPLATE);
                return itemStack;
            case CHESTPLATE_GOLDEN:
                itemStack = new ItemStack(Material.GOLDEN_CHESTPLATE);
                return itemStack;
            case LEGGINGS_IRON:
                itemStack = new ItemStack(Material.IRON_LEGGINGS);
                return itemStack;
            case LEGGINGS_GOLDEN:
                itemStack = new ItemStack(Material.GOLDEN_LEGGINGS);
                return itemStack;
            case BOOTS_IRON:
                itemStack = new ItemStack(Material.IRON_BOOTS);
                return itemStack;
            case BOOTS_GOLDEN:
                itemStack = new ItemStack(Material.GOLDEN_BOOTS);
                return itemStack;
            case SHIELD_WOODEN:
                itemStack = new ItemStack(Material.SHIELD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(1);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SHIELD_FIRE:
                itemStack = new ItemStack(Material.SHIELD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(8);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SHIELD_DARK:
                itemStack = new ItemStack(Material.SHIELD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(6);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case SHIELD_KNIGHT:
                itemStack = new ItemStack(Material.SHIELD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(4);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case KATANA:
                itemStack = new ItemStack(Material.DIAMOND_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(3);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case DAGGER_WOOD:
                itemStack = new ItemStack(Material.DIAMOND_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(1);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case DAGGER_CRIMSON:
                itemStack = new ItemStack(Material.DIAMOND_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(4);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case DAGGER_DARKEST:
                itemStack = new ItemStack(Material.DIAMOND_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setCustomModelData(11);
                itemStack.setItemMeta(itemMeta);
                return itemStack;
            case STAR:
                itemStack = new ItemStack(Material.EGG);
                return itemStack;
        }
        return null;
    }

}
