package io.github.lix3nn53.guardiansofadelia.menu.merchant;

import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.CoinType;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiCoinConverter extends GuiGeneric {

    public GuiCoinConverter(int shopNpc) {
        super(27, ChatPalette.GOLD + "Coin Converter", shopNpc);

        ItemStack silverToBronze = new ItemStack(Material.IRON_INGOT, 64);
        ItemMeta itemMeta = silverToBronze.getItemMeta();
        itemMeta.setDisplayName(ChatPalette.BROWN + "Bronze Coin");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.BROWN + "64 Bronze = " + ChatPalette.GRAY + "1 Silver");
        lore.add(ChatPalette.GRAY + "64 Silver = " + ChatPalette.GOLD + "1 Gold");
        itemMeta.setLore(lore);
        silverToBronze.setItemMeta(itemMeta);
        this.setItem(0, silverToBronze);

        ItemStack bronzeToSilver = new ItemStack(Material.GOLD_INGOT, 1);
        itemMeta.setDisplayName(ChatPalette.GRAY + "Silver Coin");
        bronzeToSilver.setItemMeta(itemMeta);
        this.setItem(9, bronzeToSilver);

        ItemStack goldToSilver = new ItemStack(Material.GOLD_INGOT, 64);
        itemMeta.setDisplayName(ChatPalette.GRAY + "Silver Coin");
        goldToSilver.setItemMeta(itemMeta);
        this.setItem(10, goldToSilver);

        ItemStack silverToGold = new ItemStack(Material.DIAMOND, 1);
        itemMeta.setDisplayName(ChatPalette.GOLD + "Gold Coin");
        silverToGold.setItemMeta(itemMeta);
        this.setItem(18, silverToGold);
    }

    @Override
    public void onClick(Player player, GuardianData guardianData, String title, int slot) {
        ItemStack current = this.getItem(slot);
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
