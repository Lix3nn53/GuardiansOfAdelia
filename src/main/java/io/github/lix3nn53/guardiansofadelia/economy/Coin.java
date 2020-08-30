package io.github.lix3nn53.guardiansofadelia.economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Coin {

    private final CoinType type;
    private final int amount;

    public Coin(CoinType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public static String getStringValue(int total) {
        int copper;
        int silver = 0;
        int gold = 0;
        if (total > 63) {
            copper = total % 64;
            silver = total / 64;
            if (silver > 63) {
                silver = total % 64;
                gold = total / 64;
            }
        } else {
            copper = total;
        }
        return ChatColor.GREEN + Integer.toString(copper) + " " + ChatColor.WHITE + silver + " " +
                ChatColor.YELLOW + gold;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack getCoin() {
        if (amount > 0) {
            ItemStack money = new ItemStack(Material.IRON_INGOT, amount);
            ItemMeta itemMeta = money.getItemMeta();
            itemMeta.setDisplayName(ChatColor.GREEN + "Bronze Coin");
            if (type.equals(CoinType.SILVER)) {
                money = new ItemStack(Material.GOLD_INGOT, amount);
                itemMeta.setDisplayName(ChatColor.WHITE + "Silver Coin");
            } else if (type.equals(CoinType.GOLD)) {
                money = new ItemStack(Material.DIAMOND, amount);
                itemMeta.setDisplayName(ChatColor.GOLD + "Gold Coin");
            }
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatColor.GREEN + "64 Bronze = " + ChatColor.WHITE + "1 Silver");
            lore.add(ChatColor.WHITE + "64 Silver = " + ChatColor.YELLOW + "1 Gold");
            itemMeta.setLore(lore);
            money.setItemMeta(itemMeta);

            return money;
        }
        return new ItemStack(Material.AIR);
    }
}
