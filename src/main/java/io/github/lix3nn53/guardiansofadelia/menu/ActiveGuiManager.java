package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.utilities.gui.Gui;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ActiveGuiManager {

    private static final HashMap<Player, Gui> playerToActiveGui = new HashMap<>();

    public static boolean hasActiveGui(Player player) {
        return playerToActiveGui.containsKey(player);
    }

    public static void clearActiveGui(Player player) {
        playerToActiveGui.remove(player);
    }

    public static Gui getActiveGui(Player player) {
        return playerToActiveGui.get(player);
    }

    public static void setActiveGui(Player player, Gui gui) {
        playerToActiveGui.put(player, gui);
    }
}
