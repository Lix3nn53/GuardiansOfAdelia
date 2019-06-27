package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum GatheringTool {
    WOODEN_FISHING_ROD,
    STONE_FISHING_ROD,
    IRON_FISHING_ROD,
    GOLDEN_FISHING_ROD,
    DIAMOND_FISHING_ROD,
    WOODEN_HOE,
    STONE_HOE,
    IRON_HOE,
    GOLDEN_HOE,
    DIAMOND_HOE,
    WOODEN_PICKAXE,
    STONE_PICKAXE,
    IRON_PICKAXE,
    GOLDEN_PICKAXE,
    DIAMOND_PICKAXE,
    WOODEN_AXE,
    STONE_AXE,
    IRON_AXE,
    GOLDEN_AXE,
    DIAMOND_AXE;

    public ItemStack getItemStack() {
        ItemStack itemStack = null;
        int durability = getDurability();
        switch (this) {
            case WOODEN_FISHING_ROD:
                itemStack = new ItemStack(Material.FISHING_ROD);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Fishing Rod" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case STONE_FISHING_ROD:
                itemStack = new ItemStack(Material.FISHING_ROD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Fishing Rod" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.addEnchant(Enchantment.LURE, 1, false);
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case IRON_FISHING_ROD:
                itemStack = new ItemStack(Material.FISHING_ROD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Fishing Rod" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.addEnchant(Enchantment.LURE, 1, false);
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case GOLDEN_FISHING_ROD:
                itemStack = new ItemStack(Material.FISHING_ROD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Fishing Rod" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.addEnchant(Enchantment.LURE, 2, false);
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case DIAMOND_FISHING_ROD:
                itemStack = new ItemStack(Material.FISHING_ROD);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Fishing Rod" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.addEnchant(Enchantment.LURE, 3, false);
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case WOODEN_HOE:
                itemStack = new ItemStack(Material.WOODEN_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Wooden Hoe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case STONE_HOE:
                itemStack = new ItemStack(Material.STONE_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Stone Hoe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case IRON_HOE:
                itemStack = new ItemStack(Material.IRON_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Iron Hoe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case GOLDEN_HOE:
                itemStack = new ItemStack(Material.GOLDEN_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Golden Hoe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case DIAMOND_HOE:
                itemStack = new ItemStack(Material.DIAMOND_HOE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Diamond Hoe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case WOODEN_PICKAXE:
                itemStack = new ItemStack(Material.WOODEN_PICKAXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Wooden Pickaxe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case STONE_PICKAXE:
                itemStack = new ItemStack(Material.STONE_PICKAXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Stone Pickaxe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case IRON_PICKAXE:
                itemStack = new ItemStack(Material.IRON_PICKAXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Iron Pickaxe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case GOLDEN_PICKAXE:
                itemStack = new ItemStack(Material.GOLDEN_PICKAXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Golden Pickaxe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case DIAMOND_PICKAXE:
                itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Diamond Pickaxe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case WOODEN_AXE:
                itemStack = new ItemStack(Material.WOODEN_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Wooden Axe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case STONE_AXE:
                itemStack = new ItemStack(Material.STONE_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Stone Axe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case IRON_AXE:
                itemStack = new ItemStack(Material.IRON_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Iron Axe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case GOLDEN_AXE:
                itemStack = new ItemStack(Material.GOLDEN_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Golden Axe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
            case DIAMOND_AXE:
                itemStack = new ItemStack(Material.DIAMOND_AXE);
                itemMeta = itemStack.getItemMeta();
                itemMeta.setDisplayName(ChatColor.GREEN + "Diamond Axe" + " (" + durability + " Uses left)");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Material collection tool");
                }});
                itemMeta.setUnbreakable(true);
                itemStack.setItemMeta(itemMeta);
                PersistentDataContainerUtil.putInteger("toolDurability", durability, itemStack);
                break;
        }
        return itemStack;
    }

    public int getDurability() {
        switch (this) {
            case WOODEN_FISHING_ROD:
                return 10;
            case STONE_FISHING_ROD:
                return 20;
            case IRON_FISHING_ROD:
                return 35;
            case GOLDEN_FISHING_ROD:
                return 50;
            case DIAMOND_FISHING_ROD:
                return 70;
            case WOODEN_HOE:
                return 10;
            case STONE_HOE:
                return 20;
            case IRON_HOE:
                return 35;
            case GOLDEN_HOE:
                return 50;
            case DIAMOND_HOE:
                return 70;
            case WOODEN_PICKAXE:
                return 10;
            case STONE_PICKAXE:
                return 20;
            case IRON_PICKAXE:
                return 35;
            case GOLDEN_PICKAXE:
                return 50;
            case DIAMOND_PICKAXE:
                return 70;
            case WOODEN_AXE:
                return 10;
            case STONE_AXE:
                return 20;
            case IRON_AXE:
                return 35;
            case GOLDEN_AXE:
                return 50;
            case DIAMOND_AXE:
                return 70;
        }
        return 15;
    }
}
