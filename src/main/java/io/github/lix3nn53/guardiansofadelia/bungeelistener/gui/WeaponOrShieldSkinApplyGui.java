package io.github.lix3nn53.guardiansofadelia.bungeelistener.gui;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.WeaponOrShieldSkinScroll;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.RPGItemUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class WeaponOrShieldSkinApplyGui extends GuiGeneric {

    private final int emptySlot = 11;
    private final int confirmSlot = 15;
    private int slotOfItemInPlayerInventory;

    public WeaponOrShieldSkinApplyGui() {
        super(27, ChatColor.GOLD + "Weapon/Shield Skin Apply", 0);

        ItemStack glass = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "1 - Right click while holding this item");
        lore.add(ChatColor.GRAY + "2 - Click to the item you want to apply skin on");
        lore.add(ChatColor.GRAY + "3 - Confirm by clicking the lime wool");
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(ChatColor.YELLOW + "Weapon/Shield Skin Apply");
        glass.setItemMeta(itemMeta);
        setItem(16, glass);

        for (int i = 0; i < 27; i++) {
            if (i == emptySlot) continue;
            if (i == confirmSlot) {
                ItemStack confirm = new ItemStack(Material.LIME_WOOL);
                itemMeta.setDisplayName(ChatColor.GREEN + "Click to apply skin!");
                lore = new ArrayList<>();
                lore.add("");
                confirm.setItemMeta(itemMeta);
                setItem(i, confirm);
                continue;
            }
            setItem(i, glass);
        }
    }

    public boolean setWeaponOrShield(ItemStack itemStack, int slotOfItemInPlayerInventory) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!itemMeta.hasCustomModelData()) return false;

        int customModelData = itemMeta.getCustomModelData();
        if (customModelData < 6) return false;

        Material type = itemStack.getType();

        boolean isWeaponOrShield = RPGItemUtils.isWeapon(type) || RPGItemUtils.isShield(type);

        if (!isWeaponOrShield) return false;

        setItem(emptySlot, itemStack);
        this.slotOfItemInPlayerInventory = slotOfItemInPlayerInventory;

        return true;
    }

    public boolean onConfirm(Player player) {
        ItemStack itemStack = getItemOnSlot();
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.AIR)) return false;
        if (!itemStack.hasItemMeta()) return false;

        if (!InventoryUtils.removeItemFromInventory(player.getInventory(), WeaponOrShieldSkinScroll.getItemStack(1), 1))
            return false;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            if (customModelData >= 6) {
                if (customModelData % 2 == 0) { //only even numbers cuz odd numbers are already glowing
                    customModelData++;
                    itemMeta.setCustomModelData(customModelData);
                    itemStack.setItemMeta(itemMeta);
                    player.getInventory().setItem(this.slotOfItemInPlayerInventory, itemStack);
                    return true;
                }
            }
        }

        return false;
    }

    public ItemStack getItemOnSlot() {
        return getItem(emptySlot);
    }

    public String getNotFitErrorMessage() {
        return ChatColor.RED + "Item must be a weapon or shield AND have a requirement level higher than or equals to level 50";
    }
}
