package io.github.lix3nn53.guardiansofadelia.jobs.gathering;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum GatheringToolType {
    FISHING_ROD,
    HOE,
    PICKAXE,
    AXE,
    BOTTLE;

    public static GatheringToolType materialToGatheringTool(Material material) {
        if (material.equals(Material.WOODEN_PICKAXE) || material.equals(Material.STONE_PICKAXE) || material.equals(Material.IRON_PICKAXE)
                || material.equals(Material.GOLDEN_PICKAXE) || material.equals(Material.DIAMOND_PICKAXE)) {
            return PICKAXE;
        } else if (material.equals(Material.WOODEN_HOE) || material.equals(Material.STONE_HOE) || material.equals(Material.IRON_HOE)
                || material.equals(Material.GOLDEN_HOE) || material.equals(Material.DIAMOND_HOE)) {
            return HOE;
        } else if (material.equals(Material.WOODEN_AXE) || material.equals(Material.STONE_AXE) || material.equals(Material.IRON_AXE)
                || material.equals(Material.GOLDEN_AXE) || material.equals(Material.DIAMOND_AXE)) {
            return AXE;
        } else if (material.equals(Material.FISHING_ROD)) {
            return FISHING_ROD;
        } else if (material.equals(Material.GLASS_BOTTLE)) {
            return BOTTLE;
        }

        return null;
    }

    public ItemStack getItemStack(GatheringToolTier gatheringToolTier) {
        ItemStack itemStack = null;
        int durability = gatheringToolTier.getDurability();

        String tierName = gatheringToolTier.toString(this);
        String tierNameEnum = gatheringToolTier.name();

        switch (this) {
            case FISHING_ROD:
                itemStack = new ItemStack(Material.FISHING_ROD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + tierName + " Fishing Rod" + " (" + durability + " Uses left)");
                ArrayList<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.GRAY + "Used for fishing");
                itemMeta.setLore(lore);
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                PersistentDataContainerUtil.putString("toolTier", tierNameEnum, itemStack);
                break;
            case HOE:
                String materialStr = gatheringToolTier.name() + "_" + this.name();
                itemStack = new ItemStack(Material.valueOf(materialStr));
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + tierName + " Hoe" + " (" + durability + " Uses left)");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.GRAY + "Used for gathering flowers");
                itemMeta.setLore(lore);
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                PersistentDataContainerUtil.putString("toolTier", tierNameEnum, itemStack);
                break;
            case PICKAXE:
                materialStr = gatheringToolTier.name() + "_" + this.name();
                itemStack = new ItemStack(Material.valueOf(materialStr));
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + tierName + " Pickaxe" + " (" + durability + " Uses left)");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.GRAY + "Used for mining ores");
                itemMeta.setLore(lore);
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                PersistentDataContainerUtil.putString("toolTier", tierNameEnum, itemStack);
                break;
            case AXE:
                materialStr = gatheringToolTier.name() + "_" + this.name();
                itemStack = new ItemStack(Material.valueOf(materialStr));
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + tierName + " Axe" + " (" + durability + " Uses left)");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.GRAY + "Used for wood cutting");
                itemMeta.setLore(lore);
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                PersistentDataContainerUtil.putString("toolTier", tierNameEnum, itemStack);
                break;
            case BOTTLE:
                itemStack = new ItemStack(Material.GLASS_BOTTLE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + tierName + " Bottle" + " (" + durability + " Uses left)");
                lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatColor.GRAY + "Used for gathering magic source");
                itemMeta.setLore(lore);
                itemMeta.setUnbreakable(true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                PersistentDataContainerUtil.putString("toolTier", tierNameEnum, itemStack);
                break;
        }
        return itemStack;
    }
}
