package io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard;

import org.bukkit.entity.Player;

import java.util.HashMap;

public interface MyScoreboard {

    void setLine(String text, int row);

    void removeLine(int row);

    void show(Player p);

    void hide(Player p);

    HashMap<Integer, String> getRowLines();
}
