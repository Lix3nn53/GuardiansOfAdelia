package io.github.lix3nn53.guardiansofadelia.menu.merchant.storage;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GuiBazaarStorage extends GuiGeneric {

    public GuiBazaarStorage() {
        super(54, "Bazaar Storage", 0);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GuardianData guardianData;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);
        } else {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            ItemStack current = event.getCurrentItem();

            if (guardianData != null) {
                if (guardianData.hasBazaar()) {
                    Bazaar bazaar = guardianData.getBazaar();
                    List<ItemStack> itemsOnSale = bazaar.getItemsOnSale();
                    if (itemsOnSale.contains(current)) {
                        player.closeInventory();
                        player.sendMessage(ChatPalette.RED + Translation.t(guardianData, "economy.storage.bazaar.onsale_error"));
                        return;
                    }
                }
            }

            ItemStack removedShopPrice = EconomyUtils.removeShopPrice(current);
            InventoryUtils.giveItemToPlayer(player, removedShopPrice);
            int slot = event.getSlot();
            clickedInventory.setItem(slot, new ItemStack(Material.AIR));
        }
    }
}
