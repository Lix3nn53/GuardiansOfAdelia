package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public final class Party implements Group {

    private final int size;
    private List<Player> members;
    private Player leader;
    private PartyBoard board;

    public Party(List<Player> members, Player leader, int size) {
        this.members = members;
        this.leader = leader;
        this.board = new PartyBoard(this);
        this.size = size;
    }

    @Override
    public List<Player> getMembers() {
        return members;
    }

    public Player getLeader() {
        return leader;
    }

    @Override
    public boolean addMember(Player player) {
        if (members.size() < size) {
            if (!members.contains(player)) {
                members.add(player);
                board.remake();
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeMember(Player player) {
        members.remove(player);
        GuardianData guardianData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(player.getUniqueId());
        guardianData.clearParty();
        player.sendMessage(ChatColor.RED + "You left the party");
        for (Player member : members) {
            member.sendMessage(player.getName() + ChatColor.RED + " left your party");
        }
        if (members.size() < 2) {
            for (Player member : members) {
                GuardianData memberguardianData = GuardiansOfAdelia.getGuardianDataManager().getGuardianData(member.getUniqueId());
                memberguardianData.clearParty();
                board.hide(member);
            }
            members.clear();
        } else {
            if (player.getUniqueId().equals(leader.getUniqueId())) {
                leader = members.get(0);
            }
            board.remake();
        }
    }

    public void setNewLeader(Player player) {
        if (members.contains(player)) {
            leader = player;
            board.remake();
        }
    }

    public PartyBoard getBoard() {
        return board;
    }
}
