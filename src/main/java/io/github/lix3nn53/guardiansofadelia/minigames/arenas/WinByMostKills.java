package io.github.lix3nn53.guardiansofadelia.minigames.arenas;

import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WinByMostKills extends Minigame {

    public WinByMostKills(String mapName, int levelReq, int timeLimitInMinutes, int roomNo, List<Location> startLocations, int teamSize, int teamAmount, int requiredPlayerAmountToStart) {
        super(ChatColor.RED + "WinByMostKills", mapName, roomNo, levelReq, teamSize, teamAmount, startLocations,
                timeLimitInMinutes, 1, TownManager.getTown(1), 999, 0, 20, requiredPlayerAmountToStart);
    }

    @Override
    public void endGame() {
        super.endGame();
        List<Integer> winnerTeams = getWinnerTeams();
        if (!winnerTeams.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            if (winnerTeams.size() == 1) {
                msg.append(ChatColor.GOLD + "Winner team: #").append(winnerTeams).append(" ( ");
                Party winnerParty = getTeams().get(winnerTeams.get(0));
                for (Player player : winnerParty.getMembers()) {
                    msg.append(player.getName());
                    msg.append(" ");
                }
                msg.append(")");
            } else {
                msg.append(ChatColor.GOLD + "Tie! Teams sharing the first place:");
                for (int team : winnerTeams) {
                    msg.append(" #").append(team).append(" ( ");
                    Party winnerParty = getTeams().get(team);
                    for (Player player : winnerParty.getMembers()) {
                        msg.append(player.getName());
                        msg.append(" ");
                    }
                    msg.append(")");
                }
            }
            for (Player player : getPlayersInGame()) {
                player.sendMessage(msg.toString());
            }
        }
    }

    @Override
    public String getMinigameName() {
        return "Win By Most Kills #" + getRoomNo();
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + getTimeLimitInMinutes() * 60);
        int teamAmount = getTeamAmount();
        for (int i = 0; i < teamAmount; i++) {
            topLines.add("Team" + (i + 1) + " score: " + 0);
        }
        return topLines;
    }
}
