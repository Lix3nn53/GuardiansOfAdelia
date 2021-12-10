package io.github.lix3nn53.guardiansofadelia.menu.bazaar;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiBazaar extends GuiGeneric {

    public GuiBazaar(GuardianData guardianData) {
        super(27, ChatPalette.GOLD + Translation.t(guardianData, "economy.bazaar.name"), 0);

        ItemStack info = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta itemMeta = info.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "economy.bazaar.info"));

        boolean hasOpenBazaar = false;
        if (guardianData.hasBazaar()) {
            Bazaar bazaar = guardianData.getBazaar();
            if (bazaar.isOpen()) {
                hasOpenBazaar = true;
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ChatPalette.GREEN_DARK + Translation.t(guardianData, "general.open"));
                lore.add("");
                lore.add(ChatPalette.GOLD + Translation.t(guardianData, "economy.bazaar.earned") + ": " + EconomyUtils.priceToString(bazaar.getMoneyEarned()));
                lore.add("");
                lore.add(ChatPalette.YELLOW + Translation.t(guardianData, "economy.bazaar.customers"));
                for (Player customer : bazaar.getCustomers()) {
                    lore.add(customer.getDisplayName());
                }
                itemMeta.setLore(lore);
            }
        }
        if (!hasOpenBazaar) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + Translation.t(guardianData, "general.closed"));
            itemMeta.setLore(lore);
        }
        info.setItemMeta(itemMeta);
        this.setItem(12, info);

        ItemStack open = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatPalette.GREEN_DARK + Translation.t(guardianData, "economy.bazaar.open.name"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "economy.bazaar.open.l1"));
        lore.add(ChatPalette.GRAY + Translation.t(guardianData, "economy.bazaar.open.l2"));
        itemMeta.setLore(lore);
        open.setItemMeta(itemMeta);
        this.setItem(14, open);
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
            Material currentType = current.getType();

            if (currentType.equals(Material.LIME_WOOL)) {
                if (guardianData != null) {
                    if (guardianData.hasBazaar()) {
                        Bazaar bazaar = guardianData.getBazaar();
                        bazaar.edit();
                    } else {
                        Bazaar bazaar = new Bazaar(player);
                        guardianData.setBazaar(bazaar);
                        bazaar.edit();
                    }
                }
            }
        }
    }
}
