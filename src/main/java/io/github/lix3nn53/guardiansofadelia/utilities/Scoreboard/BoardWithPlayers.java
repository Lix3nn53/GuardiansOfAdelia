package io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class BoardWithPlayers extends ScoreboardGeneral {

    private final List<String> topLines;
    private final ChatColor teamColor;

    public BoardWithPlayers(List<Player> players, String name, List<String> topLines, ChatColor teamColor) {
        super(name);
        this.teamColor = teamColor;

        this.topLines = topLines;
        this.setLine("", 1);

        int lineCounter = 2;
        for (String s : topLines) {
            this.setLine(s, lineCounter);
            lineCounter++;

        }

        this.setLine(ChatPalette.GREEN_DARK + "» Party", lineCounter);
        lineCounter++;
        if (!players.isEmpty()) {
            this.setLine(players.get(0).getName() + ChatColor.RED + " ❤" + (int) (players.get(0).getHealth() + 0.5), lineCounter);
            lineCounter++;
        }

        if (players.size() > 1) {
            for (int i = 1; i < players.size(); i++) {
                Player member = players.get(i);
                this.setLine(member.getName() + ChatColor.RED + " ❤" + (int) (member.getHealth() + 0.5), lineCounter);
                lineCounter++;
            }
        }

        this.registerNewTeam("friendlyTeam");
        this.setTeamColor("friendlyTeam", teamColor);

        for (Player p : players) {
            this.show(p);
            this.joinTeam("friendlyTeam", p);
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

    public void remake(List<Player> players) {
        this.setLine("", 1);

        int lineCounter = 2;
        for (String s : topLines) {
            this.setLine(s, lineCounter);
            lineCounter++;

        }

        this.setLine(ChatPalette.GREEN_DARK + "» Party", lineCounter);
        lineCounter++;
        if (!players.isEmpty()) {
            this.setLine(players.get(0).getName() + ChatColor.RED + " ❤" + (int) (players.get(0).getHealth() + 0.5), lineCounter);
            lineCounter++;
        }

        if (players.size() > 1) {
            for (int i = 1; i < players.size(); i++) {
                Player member = players.get(i);
                this.setLine(member.getName() + ChatColor.RED + " ❤" + (int) (member.getHealth() + 0.5), lineCounter);
                lineCounter++;
            }
        }

        for (int i = lineCounter; i < 15; i++) {
            this.removeLine(i);
        }

        this.unregisterTeam("friendlyTeam");
        this.registerNewTeam("friendlyTeam");
        this.setTeamColor("friendlyTeam", this.teamColor);

        for (Player p : players) {
            this.show(p);
            this.joinTeam("friendlyTeam", p);
        }
    }
}
