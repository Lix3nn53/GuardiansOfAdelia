package io.github.lix3nn53.guardiansofadelia.bungeelistener.gui;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.HelmetSkin;
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

public class HelmetSkinApplyGui extends GuiGeneric {

    private final int emptySlot = 11;
    private final int confirmSlot = 15;
    private final HelmetSkin helmetSkin;
    private int slotOfItemInPlayerInventory;

    public HelmetSkinApplyGui(HelmetSkin helmetSkin) {
        super(27, ChatPalette.GOLD + "Helmet Skin Apply", 0);
        this.helmetSkin = helmetSkin;

        ItemStack glass = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta itemMeta = glass.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Helmet Skin Apply");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "1 - Right click while holding this item");
        lore.add(ChatPalette.GRAY + "2 - Click to the item you want to apply skin on");
        lore.add(ChatPalette.GRAY + "3 - Confirm by clicking the lime wool");
        itemMeta.setLore(lore);
        glass.setItemMeta(itemMeta);
        setItem(16, glass);

        for (int i = 0; i < 27; i++) {
            if (i == emptySlot) continue;
            if (i == confirmSlot) {
                ItemStack confirm = new ItemStack(Material.LIME_WOOL);
                itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Click to apply skin!");
                lore = new ArrayList<>();
                lore.add("");
                itemMeta.setLore(lore);
                confirm.setItemMeta(itemMeta);
                setItem(i, confirm);
                continue;
            }
            setItem(i, glass);
        }
    }

    public boolean setHelmet(ItemStack itemStack, int slotOfItemInPlayerInventory) {
        Material type = itemStack.getType();

        boolean isHelmet = type.equals(Material.CHAINMAIL_HELMET) || type.equals(Material.IRON_HELMET) || type.equals(Material.DIAMOND_HELMET)
                || type.equals(Material.GOLDEN_HELMET) || type.equals(Material.LEATHER_HELMET) || type.equals(HelmetSkin.getHelmetMaterial());

        if (!isHelmet) return false;

        setItem(emptySlot, itemStack);
        this.slotOfItemInPlayerInventory = slotOfItemInPlayerInventory;

        return true;
    }

    public boolean onConfirm(Player player) {
        ItemStack itemStack = getItemOnSlot();
        if (itemStack == null) return false;
        if (itemStack.getType().equals(Material.AIR)) return false;
        if (!itemStack.hasItemMeta()) return false;

        if (!InventoryUtils.removeItemFromInventory(player.getInventory(), this.helmetSkin.getItemStack(), 1))
            return false;

        itemStack.setType(HelmetSkin.getHelmetMaterial());

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setCustomModelData(this.helmetSkin.getHelmetCustomModelData());
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(this.slotOfItemInPlayerInventory, itemStack);
        return true;
    }

    public ItemStack getItemOnSlot() {
        return getItem(emptySlot);
    }

    public String getNotFitErrorMessage() {
        return ChatPalette.RED + "Item must be a helmet";
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
            boolean b = this.setHelmet(current, slot);
            if (!b) player.sendMessage(this.getNotFitErrorMessage());
        }
    }
}
