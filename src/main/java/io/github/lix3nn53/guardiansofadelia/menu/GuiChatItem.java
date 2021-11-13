package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.rpginventory.RPGInventory;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GuiChatItem extends GuiGeneric {

    public GuiChatItem(Player player, GuardianData guardianData) {
        super(54, ChatPalette.GRAY_DARK + "Select item to show in chat", 0);

        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = filler.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GRAY + "Items in inventory");
        filler.setItemMeta(itemMeta);
        this.addItem(filler);

        PlayerInventory inventory = player.getInventory();

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack == null) continue;
            Material type = itemStack.getType();
            if (type.equals(Material.BOOK) || type.equals(Material.IRON_HOE)) continue;
            this.addItem(itemStack);
        }

        filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        itemMeta = filler.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GRAY + "Equipped armors");
        filler.setItemMeta(itemMeta);
        this.addItem(filler);

        ItemStack[] armorContents = inventory.getArmorContents();

        for (ItemStack itemStack : armorContents) {
            if (itemStack == null) continue;
            this.addItem(itemStack);
        }

        if (guardianData.hasActiveCharacter()) {
            filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            itemMeta = filler.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GRAY + "Equipped passive items");
            filler.setItemMeta(itemMeta);
            this.addItem(filler);

            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

            RPGInventory rpgInventory = activeCharacter.getRpgInventory();

            if (!rpgInventory.getRingSlot().isEmpty()) {
                this.addItem(rpgInventory.getRingSlot().getItemOnSlot());
            }

            if (!rpgInventory.getEarringSlot().isEmpty()) {
                this.addItem(rpgInventory.getEarringSlot().getItemOnSlot());
            }

            if (!rpgInventory.getNecklaceSlot().isEmpty()) {
                this.addItem(rpgInventory.getNecklaceSlot().getItemOnSlot());
            }

            if (!rpgInventory.getGloveSlot().isEmpty()) {
                this.addItem(rpgInventory.getGloveSlot().getItemOnSlot());
            }

            if (!rpgInventory.getParrotSlot().isEmpty()) {
                this.addItem(rpgInventory.getParrotSlot().getItemOnSlot());
            }

            if (!rpgInventory.getEggSlot().isEmpty()) {
                this.addItem(rpgInventory.getEggSlot().getItemOnSlot());
            }
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) return;

        TextComponent message = new TextComponent(ChatPalette.GOLD + player.getName() + " shows an item to chat: ");

        ItemStack current = event.getCurrentItem();
        ItemMeta itemMeta = current.getItemMeta();
        String currentName = itemMeta.getDisplayName();

        TextComponent itemMessage = new TextComponent(currentName);
        StringBuilder stringBuilder = new StringBuilder(currentName);
        if (itemMeta.hasLore()) {
            List<String> lore = itemMeta.getLore();
            for (String line : lore) {
                stringBuilder.append("\n");
                stringBuilder.append(line);
            }
        }
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(stringBuilder.toString())));

        message.addExtra(itemMessage);

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(message);
        }

        player.closeInventory();
    }
}
