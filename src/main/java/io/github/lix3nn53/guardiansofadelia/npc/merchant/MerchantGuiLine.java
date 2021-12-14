package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiLine;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MerchantGuiLine implements GuiLine {

    private final List<ItemStack> words = new ArrayList<>();

    public void addWord(ItemStack itemStack, int price) {
        if (isEmpty()) {
            itemStack = EconomyUtils.setShopPrice(itemStack, price);
            words.add(itemStack);
        }
    }

    @Override
    public void addWord(ItemStack itemStack) {
        addWord(itemStack, 1);
    }

    @Override
    public List<ItemStack> getLine() {
        return words;
    }

    @Override
    public boolean isEmpty() {
        return words.size() < 9;
    }

    @Override
    public void setDisabled(boolean disabled) {
        throw new NotImplementedException("MerchantGuiLine#setDisabled not implemented");
    }
}
