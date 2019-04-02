package io.github.lix3nn53.guardiansofadelia.party;

import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.ScoreboardGeneral;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PartyBoard extends ScoreboardGeneral {

    public PartyBoard(Party party) {
        super(ChatColor.AQUA + "Party");
        this.setLine("", 1);
        this.setLine(ChatColor.YELLOW + "» Leader", 2);
        this.setLine(party.getLeader().getName() + ChatColor.RED + " ❤", 3);
        this.setLine(ChatColor.GREEN + "» Members", 4);
        int lineCounter = 5;
        for (Player p : party.getMembers()) {
            if (!p.getUniqueId().equals(party.getLeader().getUniqueId())) {
                this.setLine(p.getName() + ChatColor.RED + " ❤", lineCounter);
                lineCounter++;
            }
        }
        for (Player p : party.getMembers()) {
            this.show(p);
        }
    }

    public void updateHP(String playerName, int hp) {
        if (playerName.length() > 6) {
            playerName = playerName.substring(0, 6);
        }
        for (int k : getRowLines().keySet()) {
            String s = getRowLines().get(k);
            if (s.contains(playerName)) {
                this.setLine(playerName + ChatColor.RED + " ❤" + hp, k);
                break;
            }
        }
    }

    public void remake(Party party) {
        this.setLine("", 1);
        this.setLine(ChatColor.YELLOW + "» Leader", 2);
        this.setLine(party.getLeader().getName() + ChatColor.RED + " ❤", 3);
        this.setLine(ChatColor.GREEN + "» Members", 4);
        int lineCounter = 5;
        for (Player p : party.getMembers()) {
            if (!p.getUniqueId().equals(party.getLeader().getUniqueId())) {
                this.setLine(p.getName() + ChatColor.RED + " ❤", lineCounter);
                lineCounter++;
            }
        }
        //clear extra rows
        for (int i = lineCounter; i <= 15; i++) {
            removeLine(i);
        }
        for (Player p : party.getMembers()) {
            this.show(p);
        }
    }
}
