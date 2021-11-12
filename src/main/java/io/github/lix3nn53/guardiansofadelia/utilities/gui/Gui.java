package io.github.lix3nn53.guardiansofadelia.utilities.gui;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import org.bukkit.entity.Player;

import java.util.List;

public interface Gui {

    List<Integer> getFillableSlots();

    List<Integer> getItemSlots();

    List<Integer> getEmptySlots();

    void openInventory(Player player);

    boolean isLocked();

    int getResourceNPC();

    void onClick(Player player, GuardianData guardianData, String title, int slot);
}
