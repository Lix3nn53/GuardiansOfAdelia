package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.revive.TombManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiRevive extends GuiGeneric {

    public GuiRevive(Player player) {
        super(9, ChatPalette.BLUE_LIGHT + "Revive Gui", 0);

        ItemStack respawn = new ItemStack(Material.IRON_HOE);
        ItemMeta itemMeta = respawn.getItemMeta();
        itemMeta.setCustomModelData(18);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + "Respawn");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "You have respawn in nearest town");
        lore.add(ChatPalette.GRAY + "to your death location.");
        lore.add(ChatPalette.GRAY + "Close your inventory to continue.");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        respawn.setItemMeta(itemMeta);

        this.setItem(3, respawn);

        ItemStack soul = new ItemStack(Material.IRON_HOE);
        itemMeta = soul.getItemMeta();
        itemMeta.setCustomModelData(10);
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + "Search your tomb in soul mode");
        lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Find your tomb and left");
        lore.add(ChatPalette.GRAY + "click near it to respawn");
        lore.add(ChatPalette.RED + "Time limit is 2 minutes after your death");
        lore.add(ChatPalette.GRAY + "You will respawn here if you cant");
        lore.add(ChatPalette.GRAY + "find your tomb in time");
        itemMeta.setLore(lore);
        soul.setItemMeta(itemMeta);

        this.setItem(5, soul);

        this.openInventory(player);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            int slot = event.getSlot();
            if (slot == 5) {
                TombManager.startSearch(player);
                player.closeInventory(); //calling inventory close event after startSearch doesn't cancel search
            } else if (slot == 3) {
                //inventory close event cancels tomb search so no need to call it twice
                player.closeInventory();
            }
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        TombManager.closeTombGui((Player) player);
    }
}
