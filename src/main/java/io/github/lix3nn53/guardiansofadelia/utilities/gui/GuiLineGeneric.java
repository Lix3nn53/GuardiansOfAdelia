package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiLineGeneric implements GuiLine {

    private final List<ItemStack> words = new ArrayList<>();

    @Override
    public void addWord(ItemStack itemStack) {
        if (isEmpty()) {
            words.add(itemStack);
        }
    }

    @Override
    public List<ItemStack> getLine() {
        return words;
    }

    @Override
    public boolean isEmpty() {
        return words.size() < 10;
    }
}
