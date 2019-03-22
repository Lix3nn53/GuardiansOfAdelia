package io.github.lix3nn53.guardiansofadelia.party;

import org.bukkit.entity.Player;

import java.util.List;

public interface Group {

    List<Player> getMembers();

    boolean addMember(Player p);

    void removeMember(Player p);
}
