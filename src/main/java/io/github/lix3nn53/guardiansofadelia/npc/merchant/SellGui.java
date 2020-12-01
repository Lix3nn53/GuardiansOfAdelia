package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.enchanting.EnchantManager;
import io.github.lix3nn53.guardiansofadelia.economy.Coin;
import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SellGui extends GuiGeneric {

    private final List<Integer> slotNoToRemoveFromPlayerInv = new ArrayList<>();
    private int totalValue = 0;

    public SellGui(int shopNpc) {
        super(54, ChatColor.GOLD + "Sell Items", shopNpc);

        ItemStack fillItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta itemMeta = fillItem.getItemMeta();
        itemMeta.setDisplayName(org.bukkit.ChatColor.DARK_GRAY + "-");
        fillItem.setItemMeta(itemMeta);

        setItem(52, fillItem);
        setItem(51, fillItem);
        setItem(47, fillItem);
        setItem(46, fillItem);
        setItem(45, fillItem);

        ItemStack greenWool = new ItemStack(Material.LIME_WOOL);
        itemMeta.setDisplayName(ChatColor.GREEN + "Click to CONFIRM");
        greenWool.setItemMeta(itemMeta);
        setItem(53, greenWool);

        ItemStack goldGlass = new ItemStack(Material.DIAMOND);
        itemMeta.setDisplayName(org.bukkit.ChatColor.GOLD + "Gold value: " + ChatColor.WHITE + "0");
        goldGlass.setItemMeta(itemMeta);
        setItem(50, goldGlass);

        ItemStack silverGlass = new ItemStack(Material.GOLD_INGOT);
        itemMeta.setDisplayName(org.bukkit.ChatColor.WHITE + "Silver value: " + ChatColor.WHITE + "0");
        silverGlass.setItemMeta(itemMeta);
        setItem(49, silverGlass);

        ItemStack bronzeGlass = new ItemStack(Material.IRON_INGOT);
        itemMeta.setDisplayName(org.bukkit.ChatColor.GREEN + "Bronze value: " + ChatColor.WHITE + "0");
        bronzeGlass.setItemMeta(itemMeta);
        setItem(48, bronzeGlass);
    }

    public boolean addItemToSell(ItemStack itemStack, int slotNoInPlayerInventory) {
        if (!InventoryUtils.canSellMaterial(itemStack.getType())) return false;

        if (!slotNoToRemoveFromPlayerInv.contains(slotNoInPlayerInventory)) {
            addItem(itemStack);
            slotNoToRemoveFromPlayerInv.add(slotNoInPlayerInventory);
            totalValue += getSellValue(itemStack);
            List<Coin> coins = EconomyUtils.priceToCoins(totalValue);

            ItemStack gold = new ItemStack(Material.DIAMOND);
            ItemMeta itemMeta = gold.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GOLD + "Gold value: " + ChatColor.WHITE + "0");
            setItem(50, gold);
            if (coins.get(2).getAmount() > 0) {
                gold.setAmount(coins.get(2).getAmount());
                itemMeta.setDisplayName(ChatColor.GOLD + "Gold value: " + ChatColor.WHITE + coins.get(2).getAmount());
            }
            gold.setItemMeta(itemMeta);
            setItem(50, gold);

            ItemStack silver = new ItemStack(Material.GOLD_INGOT);
            itemMeta.setDisplayName(ChatColor.WHITE + "Silver value: " + ChatColor.WHITE + "0");
            if (coins.get(1).getAmount() > 0) {
                silver.setAmount(coins.get(1).getAmount());
                itemMeta.setDisplayName(ChatColor.WHITE + "Silver value: " + ChatColor.WHITE + coins.get(1).getAmount());
            }
            silver.setItemMeta(itemMeta);
            setItem(49, silver);

            ItemStack bronze = new ItemStack(Material.IRON_INGOT);
            itemMeta.setDisplayName(org.bukkit.ChatColor.GREEN + "Bronze value:  " + ChatColor.WHITE + "0");
            if (coins.get(0).getAmount() > 0) {
                bronze.setAmount(coins.get(0).getAmount());
                itemMeta.setDisplayName(ChatColor.GREEN + "Bronze value: " + ChatColor.WHITE + coins.get(0).getAmount());
            }
            bronze.setItemMeta(itemMeta);
            setItem(48, bronze);
        }

        return true;
    }

    private int getSellValue(ItemStack itemStack) {
        int reqLevel = 1;
        if (PersistentDataContainerUtil.hasInteger(itemStack, "reqLevel")) {
            reqLevel = PersistentDataContainerUtil.getInteger(itemStack, "reqLevel");
        }
        double price = Math.max(1, Math.pow(reqLevel, 1.12) / 4);

        ItemTier itemTier = ItemTier.getItemTierOfItemStack(itemStack);
        price = price * itemTier.getBonusMultiplier() + 0.5;

        int enchantLevel = EnchantManager.getEnchantLevel(itemStack);
        price = price * EnchantManager.getSellGuiMultiplier(enchantLevel);

        return (int) (price * itemStack.getAmount() + 0.5);
    }

    public void finish(Player player) {
        for (int slotNo : this.slotNoToRemoveFromPlayerInv) {
            player.getInventory().setItem(slotNo, new ItemStack(Material.AIR));
        }
        List<Coin> coins = EconomyUtils.priceToCoins(this.totalValue);
        if (coins.get(0).getAmount() > 0) {
            InventoryUtils.giveItemToPlayer(player, coins.get(0).getCoin());
        }
        if (coins.get(1).getAmount() > 0) {
            InventoryUtils.giveItemToPlayer(player, coins.get(1).getCoin());
        }
        if (coins.get(2).getAmount() > 0) {
            InventoryUtils.giveItemToPlayer(player, coins.get(2).getCoin());
        }
        player.closeInventory();
        player.sendMessage(ChatColor.GOLD + "Sold items to NPC for: " + EconomyUtils.priceToString(this.totalValue));
    }

    public void confirm() {
        ItemStack yellowWool = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta itemMeta = yellowWool.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GOLD + "Click to SELL");
        yellowWool.setItemMeta(itemMeta);
        setItem(53, yellowWool);
    }
}
