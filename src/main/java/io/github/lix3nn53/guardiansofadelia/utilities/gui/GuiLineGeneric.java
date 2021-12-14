package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiLineGeneric implements GuiLine {

    private final List<ItemStack> words = new ArrayList<>();
    private boolean isDisabled = false;

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
        if (isDisabled) return false;

        return words.size() < 9;
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.isDisabled = disabled;
    }
}
