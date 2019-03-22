package io.github.lix3nn53.guardiansofadelia.utilities.gui;

public interface GuiBook extends Gui {

    GuiGeneric getPageInventory(int pageNo);

    void addPage(GuiPage page);

}
