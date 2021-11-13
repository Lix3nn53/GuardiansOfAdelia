package io.github.lix3nn53.guardiansofadelia.menu.main;

import io.github.lix3nn53.guardiansofadelia.menu.main.minigames.GuiMinigamesJoinLOS;
import io.github.lix3nn53.guardiansofadelia.menu.main.minigames.GuiMinigamesJoinWBMK;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiMinigames extends GuiGeneric {

    public GuiMinigames() {
        super(27, ChatPalette.GRAY_DARK + "MiniGames", 0);

        ItemStack pvp = new ItemStack(Material.RED_WOOL);
        ItemMeta itemMeta = pvp.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.RED + "Last One Standing");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + "Click to join this minigame");
        itemMeta.setLore(lore);
        pvp.setItemMeta(itemMeta);
        this.setItem(0, pvp);

        itemMeta.setDisplayName(ChatPalette.RED + "Win By Most Kills");
        pvp.setItemMeta(itemMeta);
        this.setItem(2, pvp);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            int slot = event.getSlot();
            if (slot == 0) {
                GuiMinigamesJoinLOS gui = new GuiMinigamesJoinLOS();
                gui.openInventory(player);
            } else if (slot == 2) {
                GuiMinigamesJoinWBMK gui = new GuiMinigamesJoinWBMK();
                gui.openInventory(player);
            }
        }
    }
}
