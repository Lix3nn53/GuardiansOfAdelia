package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class Party {

    private final int size;
    private List<Player> members;
    private Player leader;
    private BoardWithPlayers board;

    public Party(List<Player> members, int size) {
        this.members = members;
        this.leader = members.get(0);
        this.board = new BoardWithPlayers(members, ChatColor.AQUA + "Party", new ArrayList<>());
        this.size = size;
        PartyManager.addMembers(members, this);
    }

    public Party(List<Player> members, int size, BoardWithPlayers boardWithPlayers) {
        this.members = members;
        this.leader = members.get(0);
        this.board = boardWithPlayers;
        this.size = size;
        PartyManager.addMembers(members, this);
    }

    public List<Player> getMembers() {
        return members;
    }

    public Player getLeader() {
        return leader;
    }

    public boolean addMember(Player player) {
        if (members.size() < size) {
            if (!members.contains(player)) {
                members.add(player);
                board.remake(this.members);
                PartyManager.addMember(player, this);
                return true;
            }
        }
        return false;
    }

    public void leave(Player playerLeft) {
        if (members.contains(playerLeft)) {
            members.remove(playerLeft);

            playerLeft.sendMessage(ChatColor.RED + "You left the party");

            for (Player member : members) {
                member.sendMessage(playerLeft.getName() + ChatColor.RED + " left your party");
            }

            onMemberRemove(playerLeft);
        }
    }

    public void kickMember(Player kicker, Player playerToKick) {
        if (getLeader().getUniqueId().equals(kicker.getUniqueId())) {
            if (members.contains(playerToKick)) {
                members.remove(playerToKick);

                playerToKick.sendMessage(kicker.getName() + ChatColor.RED + " kicked you from the party");

                for (Player member : members) {
                    member.sendMessage(kicker.getName() + ChatColor.RED + " kicked " + playerToKick.getName() + " from the party");
                }

                onMemberRemove(playerToKick);
            }
        }
    }

    private void onMemberRemove(Player player) {
        if (members.size() < 2) {
            for (Player member : members) {
                board.hide(member);
                PartyManager.removeMember(member);
            }
            members.clear();
        } else {
            if (player.getUniqueId().equals(leader.getUniqueId())) {
                leader = members.get(0);
            }
            board.remake(this.members);
        }
        board.hide(player);
        PartyManager.removeMember(player);
    }

    public void setNewLeader(Player setter, Player player) {
        if (setter.getUniqueId().equals(leader.getUniqueId())) {
            if (members.contains(player)) {
                if (!player.getUniqueId().equals(leader.getUniqueId())) {
                    leader = player;
                    board.remake(this.members);
                }
            }
        }
    }

    public BoardWithPlayers getBoard() {
        return board;
    }

    public boolean isEmpty() {
        return members.size() < size;
    }

    public void destroy() {
        for (Player member : members) {
            board.hide(member);
            PartyManager.removeMember(member);
        }
        members.clear();
    }
}
