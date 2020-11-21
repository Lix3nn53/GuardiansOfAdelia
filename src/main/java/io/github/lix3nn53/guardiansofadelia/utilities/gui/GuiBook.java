package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import org.bukkit.inventory.ItemStack;

public interface GuiBook extends Gui {

    GuiGeneric getPageInventory(int pageNo);

    void addPage(GuiPage page);

    GuiPage getFirstAvailablePage();

    void addToFirstAvailableWord(ItemStack itemStack);
}
