package io.github.lix3nn53.guardiansofadelia.economy;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Coin {

    private CoinType type;
    private int amount;

    public Coin(CoinType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public static List<Integer> convert(int total) {
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
        List<Integer> moneyBag = new ArrayList<Integer>();
        moneyBag.add(copper);
        moneyBag.add(silver);
        moneyBag.add(gold);
        return moneyBag;
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
        ItemStack money = new ItemStack(Material.IRON_INGOT, amount);
        ItemMeta itemMeta = money.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Bronze Coin");
        if (type.equals(CoinType.SILVER)) {
            money = new ItemStack(Material.GOLD_INGOT, amount);
            itemMeta.setDisplayName(ChatColor.WHITE + "Silver Coin");
        } else if (type.equals(CoinType.GOLD)) {
            money = new ItemStack(Material.DIAMOND, amount);
            itemMeta.setDisplayName(ChatColor.YELLOW + "Gold Coin");
        }
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.GREEN + "64 Bronze = " + ChatColor.WHITE + "1 Silver");
            add(ChatColor.WHITE + "64 Silver = " + ChatColor.YELLOW + "1 Gold");
        }});
        money.setItemMeta(itemMeta);

        return money;
    }
}
