package io.github.lix3nn53.guardiansofadelia.economy;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
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

    public int getAmount() {
        return amount;
    }

    public ItemStack getCoin() {
        if (amount > 0) {
            ItemStack money = new ItemStack(Material.IRON_INGOT, amount);
            ItemMeta itemMeta = money.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.BROWN + "Bronze Coin");
            if (type.equals(CoinType.SILVER)) {
                money = new ItemStack(Material.GOLD_INGOT, amount);
                itemMeta.setDisplayName(ChatPalette.GRAY + "Silver Coin");
            } else if (type.equals(CoinType.GOLD)) {
                money = new ItemStack(Material.DIAMOND, amount);
                itemMeta.setDisplayName(ChatPalette.GOLD + "Gold Coin");
            }
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.BROWN + "64 Bronze = " + ChatPalette.GRAY + "1 Silver");
            lore.add(ChatPalette.GRAY + "64 Silver = " + ChatPalette.GOLD + "1 Gold");
            itemMeta.setLore(lore);
            money.setItemMeta(itemMeta);

            return money;
        }
        return new ItemStack(Material.AIR);
    }
}
