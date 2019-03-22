package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import org.bukkit.entity.Player;

import java.util.List;

public interface Gui {

    List<Integer> getFillableSlots();

    List<Integer> getItemSlots();

    List<Integer> getEmptySlots();

    void openInventory(Player player);

    boolean isLocked();

    int getResourceNPC();
}
