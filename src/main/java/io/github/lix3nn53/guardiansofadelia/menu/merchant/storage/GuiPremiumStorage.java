package io.github.lix3nn53.guardiansofadelia.menu.merchant.storage;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiPremiumStorage extends GuiGeneric {

    public GuiPremiumStorage() {
        super(54, "Premium Storage", 0);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            ItemStack current = event.getCurrentItem();
            if (current == null) return;
            int slot = event.getSlot();

            InventoryUtils.giveItemToPlayer(player, current);
            clickedInventory.setItem(slot, new ItemStack(Material.AIR));
        }
    }
}
