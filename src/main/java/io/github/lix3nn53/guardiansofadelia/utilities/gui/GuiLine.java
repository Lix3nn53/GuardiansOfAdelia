package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface GuiLine {

    void addWord(ItemStack itemStack);

    List<ItemStack> getLine();

    boolean isEmpty();

    void setDisabled(boolean disabled);
}
