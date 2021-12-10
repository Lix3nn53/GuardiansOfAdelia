package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class Party {

    private final int size;
    private final int minSize;
    private final List<Player> members;
    private final BoardWithPlayers board;

    public Party(List<Player> members, int size, int minSize, ChatColor teamColor) {
        this.members = members;
        List<String> topLines = new ArrayList<>();
        this.board = new BoardWithPlayers(members, ChatColor.AQUA + "Party", topLines, teamColor);
        this.size = size;
        if (minSize < 1) {
            this.minSize = 1;
        } else {
            this.minSize = minSize;
        }
        PartyManager.addMembers(members, this);
    }

    public Party(List<Player> members, int size, int minSize, BoardWithPlayers boardWithPlayers) {
        this.members = members;
        this.board = boardWithPlayers;
        this.size = size;
        if (minSize < 1) {
            this.minSize = 1;
        } else {
            this.minSize = minSize;
        }
        PartyManager.addMembers(members, this);
    }

    public List<Player> getMembers() {
        return members;
    }

    public Player getLeader() {
        return members.get(0);
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
        members.remove(playerLeft);

        playerLeft.sendMessage(ChatPalette.RED + "You left the party");

        for (Player member : members) {
            member.sendMessage(playerLeft.getName() + ChatPalette.RED + " left your party");
        }

        onMemberRemove(playerLeft);
    }

    public void kickMember(Player kicker, Player playerToKick) {
        if (getLeader().getUniqueId().equals(kicker.getUniqueId())) {
            if (members.contains(playerToKick)) {
                members.remove(playerToKick);

                playerToKick.sendMessage(kicker.getName() + ChatPalette.RED + " kicked you from the party");

                for (Player member : members) {
                    member.sendMessage(kicker.getName() + ChatPalette.RED + " kicked " + playerToKick.getName() + " from the party");
                }

                onMemberRemove(playerToKick);
            }
        }
    }

    private void onMemberRemove(Player player) {
        if (members.size() < minSize) {
            for (Player member : members) {
                board.hide(member);
                PartyManager.removeMember(member);
            }
        } else {
            board.remake(this.members);
        }
        board.hide(player);
        PartyManager.removeMember(player);
    }

    /*public void setNewLeader(Player setter, Player player) {
        Player leader = getLeader();

        if (!setter.equals(leader)) {
            setter.sendMessage(ChatPalette.RED + "You must be party leader");
            return;
        }

        if (!members.contains(player)) {
            setter.sendMessage(ChatPalette.RED + player.getName() + " is not a member of your party");
            return;
        }

        if (player.equals(leader)) {
            setter.sendMessage(ChatPalette.RED + "You can't select yourself");
            return;
        }

        this.members.clear();
        this.members.add(player);
        this.members.add(leader);

        board.remake(this.members);
    }*/

    public BoardWithPlayers getBoard() {
        return board;
    }

    public boolean anyFreeSpace() {
        return members.size() < size;
    }

    public void destroy() {
        for (Player member : getMembers()) {
            getBoard().hide(member);
            PartyManager.removeMember(member);
        }
    }
}
