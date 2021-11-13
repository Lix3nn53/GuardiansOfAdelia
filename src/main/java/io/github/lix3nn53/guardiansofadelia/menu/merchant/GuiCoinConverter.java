package io.github.lix3nn53.guardiansofadelia.menu.merchant;

import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.locale.Translation;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCoinConverter extends GuiGeneric {

    public GuiCoinConverter(GuardianData guardianData, int shopNpc) {
        super(27, ChatPalette.GOLD + Translation.t(guardianData, "economy.coin.convert"), shopNpc);

        ItemStack silverToBronze = new ItemStack(Material.IRON_INGOT, 64);
        ItemMeta itemMeta = silverToBronze.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BROWN + Translation.t(guardianData, "economy.coin.bronze") + " " + Translation.t(guardianData, "economy.coin.name"));
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.BROWN + "64 " + Translation.t(guardianData, "economy.coin.bronze") + " = " + ChatPalette.GRAY + "1 " + Translation.t(guardianData, "economy.coin.silver"));
        lore.add(ChatPalette.GRAY + "64 " + Translation.t(guardianData, "economy.coin.silver") + " = " + ChatPalette.GOLD + "1 " + Translation.t(guardianData, "economy.coin.gold"));
        itemMeta.setLore(lore);
        silverToBronze.setItemMeta(itemMeta);
        this.setItem(0, silverToBronze);

        ItemStack bronzeToSilver = new ItemStack(Material.GOLD_INGOT, 1);
        itemMeta.setDisplayName(ChatPalette.GRAY + Translation.t(guardianData, "economy.coin.silver") + " " + Translation.t(guardianData, "economy.coin.name"));
        bronzeToSilver.setItemMeta(itemMeta);
        this.setItem(9, bronzeToSilver);

        ItemStack goldToSilver = new ItemStack(Material.GOLD_INGOT, 64);
        itemMeta.setDisplayName(ChatPalette.GRAY + Translation.t(guardianData, "economy.coin.silver") + " " + Translation.t(guardianData, "economy.coin.name"));
        goldToSilver.setItemMeta(itemMeta);
        this.setItem(10, goldToSilver);

        ItemStack silverToGold = new ItemStack(Material.DIAMOND, 1);
        itemMeta.setDisplayName(ChatPalette.GOLD + Translation.t(guardianData, "economy.coin.gold") + " " + Translation.t(guardianData, "economy.coin.name"));
        silverToGold.setItemMeta(itemMeta);
        this.setItem(18, silverToGold);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack current = this.getItem(event.getSlot());
        Material currentType = current.getType();

        PlayerInventory playerInventory = player.getInventory();
        if (currentType.equals(Material.IRON_INGOT)) {
            if (InventoryUtils.inventoryContains(playerInventory, Material.GOLD_INGOT, 1)) {
                InventoryUtils.removeMaterialFromInventory(playerInventory, Material.GOLD_INGOT, 1);
                InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.COPPER, 64).getCoin());
            }
        } else if (currentType.equals(Material.GOLD_INGOT)) {
            if (current.getAmount() == 1) {
                if (InventoryUtils.inventoryContains(playerInventory, Material.IRON_INGOT, 64)) {
                    InventoryUtils.removeMaterialFromInventory(playerInventory, Material.IRON_INGOT, 64);
                    InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.SILVER, 1).getCoin());
                }
            } else if (current.getAmount() == 64) {
                if (InventoryUtils.inventoryContains(playerInventory, Material.DIAMOND, 1)) {
                    InventoryUtils.removeMaterialFromInventory(playerInventory, Material.DIAMOND, 1);
                    InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.SILVER, 64).getCoin());
                }
            }
        } else if (currentType.equals(Material.DIAMOND)) {
            if (InventoryUtils.inventoryContains(playerInventory, Material.GOLD_INGOT, 64)) {
                InventoryUtils.removeMaterialFromInventory(playerInventory, Material.GOLD_INGOT, 64);
                InventoryUtils.giveItemToPlayer(player, new Coin(CoinType.GOLD, 1).getCoin());
            }
        }
    }
}
