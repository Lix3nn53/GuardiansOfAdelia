package io.github.lix3nn53.guardiansofadelia.bungeelistener.gui;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.WeaponOrShieldSkinScroll;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class WeaponOrShieldSkinApplyGui extends GuiGeneric {

    private final int emptySlot = 11;
    private final int confirmSlot = 15;
    private int slotOfItemInPlayerInventory;

    public WeaponOrShieldSkinApplyGui() {
        super(27, ChatPalette.GOLD + "Weapon/Shield Skin Apply", 0);

        ItemStack glass = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = glass.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "1 - Right click while holding this item");
        lore.add(ChatPalette.GRAY + "2 - Click to the item you want to apply skin on");
        lore.add(ChatPalette.GRAY + "3 - Confirm by clicking the lime wool");
        itemMeta.setLore(lore);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Weapon/Shield Skin Apply");
        glass.setItemMeta(itemMeta);
        setItem(16, glass);

        for (int i = 0; i < 27; i++) {
            if (i == emptySlot) continue;
            if (i == confirmSlot) {
                ItemStack confirm = new ItemStack(Material.LIME_WOOL);
                itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Click to apply skin!");
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

        WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);
        ShieldGearType shieldGearType = ShieldGearType.fromMaterial(type);
        boolean isWeaponOrShield = weaponGearType != null || shieldGearType != null;

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
        return ChatPalette.RED + "Item must be a weapon or shield AND have a requirement level higher than or equals to level 50";
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack current = event.getCurrentItem();
        Material currentType = current.getType();
        int slot = event.getSlot();
        Player player = (Player) event.getWhoClicked();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            if (currentType.equals(Material.LIME_WOOL)) {
                boolean b = this.onConfirm(player);
                if (b) {
                    player.closeInventory();
                    MessageUtils.sendCenteredMessage(player, ChatPalette.GRAY + "------------------------");
                    MessageUtils.sendCenteredMessage(player, "Applied Skin");
                    MessageUtils.sendCenteredMessage(player, "to " + this.getItemOnSlot().getItemMeta().getDisplayName());
                    MessageUtils.sendCenteredMessage(player, ChatPalette.GRAY + "------------------------");
                }
            }
        } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
            boolean b = this.setWeaponOrShield(current, slot);
            if (!b) player.sendMessage(this.getNotFitErrorMessage());
        }
    }
}
