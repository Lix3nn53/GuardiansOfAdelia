package io.github.lix3nn53.guardiansofadelia.bungeelistener.ingame;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class WeaponOrShieldSkinApplyGui extends GuiGeneric {

    private final int emptySlot = 11;
    private final int confirmSlot = 15;
    private final String skinName;

    public WeaponOrShieldSkinApplyGui(String skinName) {
        super(27, ChatColor.GOLD + "Weapon/Shield Skin Apply (" + skinName + ")", 0);
        this.skinName = skinName;

        ItemStack glass = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = glass.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Weapon/Shield Skin Apply (" + skinName + ")");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GRAY + "1 - Click to the item you want to apply skin on");
            add(ChatColor.GRAY + "2 - Click to green wool to confirm");
        }});
        glass.setItemMeta(itemMeta);
        setItem(16, glass);

        for (int i = 0; i < 27; i++) {
            if (i == emptySlot) continue;
            if (i == confirmSlot) {
                ItemStack confirm = new ItemStack(Material.LIME_WOOL);
                itemMeta.setDisplayName(ChatColor.GREEN + "Click to apply skin!");
                itemMeta.setLore(new ArrayList() {{
                    add("");
                    add(ChatColor.GRAY + "Skin: " + skinName);
                }});
                confirm.setItemMeta(itemMeta);
                setItem(i, confirm);
                continue;
            }
            setItem(i, glass);
        }
    }

    public boolean setWeaponOrShield(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (!itemMeta.hasCustomModelData()) return false;

        int customModelData = itemMeta.getCustomModelData();
        if (customModelData < 6) return false;

        Material type = itemStack.getType();

        boolean isWeaponOrShield = type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL)
                || type.equals(Material.DIAMOND_AXE) || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT)
                || type.equals(Material.BOW) || type.equals(Material.CROSSBOW) || type.equals(Material.SHIELD);

        if (!isWeaponOrShield) return false;

        setItem(emptySlot, itemStack);

        return true;
    }

    public boolean onConfirm() {
        ItemStack itemStack = getItemOnSlot();
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.AIR)) return false;
        if (itemStack.hasItemMeta()) return false;

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta.hasCustomModelData()) {
            int customModelData = itemMeta.getCustomModelData();
            if (customModelData >= 6) {
                customModelData++;
                itemMeta.setCustomModelData(customModelData);
                itemStack.setItemMeta(itemMeta);

                return true;
            }
        }

        return false;
    }

    public ItemStack getItemOnSlot() {
        return getItem(emptySlot);
    }

    public String getSkinName() {
        return skinName;
    }

    public String getNotFitErrorMessage() {
        return net.md_5.bungee.api.ChatColor.RED + "Item must be a weapon or shield AND have a requirement level higher than or equals to level 50";
    }
}
