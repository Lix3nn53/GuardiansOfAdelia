package io.github.lix3nn53.guardiansofadelia.items.enchanting;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public enum EnchantStone {
    TIER_ONE,
    TIER_TWO,
    TIER_THREE,
    TIER_FOUR;

    public int getCustomModelData() {
        if (this.equals(EnchantStone.TIER_TWO)) {
            return 3;
        } else if (this.equals(EnchantStone.TIER_THREE)) {
            return 4;
        } else if (this.equals(EnchantStone.TIER_FOUR)) {
            return 5;
        }

        return 2;
    }

    public ItemStack getItemStack(int amount) {
        Material material = Material.BROWN_DYE;

        if (this.equals(EnchantStone.TIER_TWO)) {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setCustomModelData(2);
            itemMeta.setDisplayName(ChatPalette.BLUE + "Enchant Stone Tier 2");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Magical stone to enchant items");
            lore.add(ChatPalette.GRAY + "For +3, +4 and +5 items");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            PersistentDataContainerUtil.putInteger("ench_stone", 2, item);
            return item;
        } else if (this.equals(EnchantStone.TIER_THREE)) {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setCustomModelData(3);
            itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Enchant Stone Tier 3");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Magical stone to enchant items");
            lore.add(ChatPalette.GRAY + "For +6, +7 and +8 items");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            PersistentDataContainerUtil.putInteger("ench_stone", 3, item);
            return item;
        } else if (this.equals(EnchantStone.TIER_FOUR)) {
            ItemStack item = new ItemStack(material, amount);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setCustomModelData(4);
            itemMeta.setDisplayName(ChatPalette.RED + "Enchant Stone Tier 4");
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Magical stone to enchant items");
            lore.add(ChatPalette.GRAY + "For +9, +10 and +11 items");
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            PersistentDataContainerUtil.putInteger("ench_stone", 4, item);
            return item;
        }
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(1);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Enchant Stone Tier 1");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Magical stone to enchant items");
        lore.add(ChatPalette.GRAY + "For +0, +1 and +2 items");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putInteger("ench_stone", 1, item);
        return item;
    }
}
