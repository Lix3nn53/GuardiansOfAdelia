package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VanillaSlotChestplate implements VanillaSlot {

    @Override
    public boolean doesFit(ItemStack itemStack) {
        Material mat = itemStack.getType();
        if (!(mat.equals(Material.CHAINMAIL_CHESTPLATE) || mat.equals(Material.DIAMOND_CHESTPLATE) || mat.equals(Material.GOLDEN_CHESTPLATE)
                || mat.equals(Material.IRON_CHESTPLATE) || mat.equals(Material.LEATHER_CHESTPLATE))) {
            return false;
        }
        return false;
    }

    @Override
    public void setItemOnSlot(Player player, ItemStack itemOnSlot) {
        player.getInventory().setChestplate(itemOnSlot);
    }

    @Override
    public boolean isEmpty(Player player) {
        return player.getInventory().getChestplate() == null;
    }

    @Override
    public void setEmpty(Player player) {
        player.getInventory().setChestplate(new ItemStack(Material.AIR));
    }

    @Override
    public ItemStack getItemOnSlot(Player player) {
        return player.getInventory().getChestplate();
    }

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Chestplate Slot");
        itemMeta.setLore(new ArrayList() {{
            add("");
        }});
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        if (itemMeta instanceof Damageable) {
            Damageable damageable = (Damageable) itemMeta;
            damageable.setDamage(5);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
