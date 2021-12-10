package io.github.lix3nn53.guardiansofadelia.menu.bazaar;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.economy.bazaar.Bazaar;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.signmenu.SignMenuFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiBazaarEdit extends GuiGeneric {

    public GuiBazaarEdit(Player owner, List<ItemStack> itemsOnSale) {
        super(27, ChatPalette.GOLD + "Edit your bazaar", 0);

        ItemStack glassInfo = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta itemMeta = glassInfo.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.GOLD + "Click an item in your inventory to add");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatPalette.GOLD + "to your bazaar. Click an item in your bazaar");
        lore.add(ChatPalette.GOLD + "to remove from your bazaar.");
        lore.add("");
        lore.add(ChatPalette.GREEN_DARK + "Click green wool to set up your bazaar");
        lore.add("");
        lore.add(ChatPalette.RED + "Click red wool to destroy your bazaar");
        itemMeta.setLore(lore);
        glassInfo.setItemMeta(itemMeta);
        for (int i = 18; i <= 26; i++) {
            this.setItem(i, glassInfo);
        }

        ItemStack redWool = new ItemStack(Material.RED_WOOL);
        ItemMeta redMeta = redWool.getItemMeta();
        redMeta.setDisplayName(ChatPalette.RED + "Click to destroy your bazaar");
        redWool.setItemMeta(redMeta);
        this.setItem(18, redWool);

        ItemStack greenWool = new ItemStack(Material.LIME_WOOL);
        ItemMeta greenMeta = greenWool.getItemMeta();
        greenMeta.setDisplayName(ChatPalette.GREEN_DARK + "Click to set up your bazaar");
        greenWool.setItemMeta(greenMeta);
        this.setItem(26, greenWool);

        int i = 0;
        for (ItemStack itemStack : itemsOnSale) {
            this.setItem(i, itemStack);
            i++;
        }
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
        ItemStack current = event.getCurrentItem();
        Material currentType = current.getType();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            if (currentType.equals(Material.LIME_WOOL)) {
                if (guardianData != null) {
                    if (guardianData.hasBazaar()) {
                        Bazaar bazaar = guardianData.getBazaar();
                        bazaar.setUp();
                    }
                }
            } else if (currentType.equals(Material.RED_WOOL)) {
                if (guardianData != null) {
                    if (guardianData.hasBazaar()) {
                        Bazaar bazaar = guardianData.getBazaar();
                        bazaar.remove();
                        guardianData.setBazaar(null);
                        player.closeInventory();
                        player.sendMessage(ChatPalette.RED + "Removed your bazaar");
                    }
                }
            } else {
                if (guardianData != null) {
                    if (guardianData.hasBazaar()) {
                        Bazaar bazaar = guardianData.getBazaar();
                        boolean isRemoved = bazaar.removeItem(current, current.getAmount());
                        if (isRemoved) {
                            ItemStack itemStack = EconomyUtils.removeShopPrice(current);
                            InventoryUtils.giveItemToPlayer(player, itemStack);
                            bazaar.edit();
                        }
                    }
                }
            }
        } else if (clickedInventory.getType().equals(InventoryType.PLAYER)) {
            SignMenuFactory signMenuFactory = GuardiansOfAdelia.getSignMenuFactory();

            ItemMeta itemMeta = current.getItemMeta();
            String currentName = itemMeta.getDisplayName();

            SignMenuFactory.Menu menu = signMenuFactory.newMenu(Arrays.asList("Set price of:", currentName, "▼ Enter below ▼"))
                    .reopenIfFail(true)
                    .response((signPlayer, strings) -> {
                        if (GuardianDataManager.hasGuardianData(player)) {
                            GuardianData guardianDataBazaar = GuardianDataManager.getGuardianData(player);
                            if (guardianDataBazaar.hasBazaar()) {
                                Bazaar bazaar = guardianDataBazaar.getBazaar();

                                int price;
                                try {
                                    price = Integer.parseInt(strings[3]);
                                } catch (NumberFormatException e) {
                                    player.sendMessage(ChatPalette.RED + "Enter a number between 1-266239 to last line.");
                                    return false;
                                }

                                if (price > 266304) {
                                    player.sendMessage(ChatPalette.RED + "Max price is 266239.");
                                    return false;
                                }

                                bazaar.addItem(current, price);
                                InventoryUtils.removeItemFromInventory(player.getInventory(), current, current.getAmount());
                                bazaar.edit();
                            }
                        }

                        return true;
                    });

            menu.open(player);
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        GuardianData guardianData;
        if (GuardianDataManager.hasGuardianData(player)) {
            guardianData = GuardianDataManager.getGuardianData(player);
        } else {
            return;
        }

        if (guardianData.hasBazaar()) {
            Bazaar bazaar = guardianData.getBazaar();
            bazaar.setOpen(true);
        }
    }
}
