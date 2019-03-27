package io.github.lix3nn53.guardiansofadelia.economy.shop;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiLine;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopLine implements GuiLine {

    private final List<ItemStack> itemList = new ArrayList<>();

    public void addWord(ItemStack itemStack) {
        if (isEmpty()) {
            itemList.add(itemStack);
        }
    }

    public void addWord(ItemStack itemStack, int price) {
        if (itemList.size() < 9) {
            itemStack = EconomyUtils.setShopPrice(itemStack, price);
            itemList.add(itemStack);
        }
    }

    public List<ItemStack> getLine() {
        return itemList;
    }

    @Override
    public boolean isEmpty() {
        return itemList.size() < 9;
    }
}
