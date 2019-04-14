package io.github.lix3nn53.guardiansofadelia.minigames.arenas;

import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LastOneStanding extends Minigame {

    public LastOneStanding(String mapName, int levelReq, int timeLimitInMinutes, int roomNo, List<Location> startLocations, int teamSize, int teamAmount, int maxLives, int requiredPlayerAmountToStart) {
        super(ChatColor.RED + "LastOneStanding", mapName, roomNo, levelReq, teamSize, teamAmount, startLocations,
                timeLimitInMinutes, 1, TownManager.getTown(1), maxLives, 1, 20, requiredPlayerAmountToStart);
    }

    @Override
    public void endGame() {
        super.endGame();
        int winnerTeam = getWinnerTeam();
        if (winnerTeam >= 0) {
            StringBuilder msg = new StringBuilder(ChatColor.GOLD + "Winner team: #" + winnerTeam + " ( ");
            Party winnerParty = getTeams().get(winnerTeam);
            for (Player player : winnerParty.getMembers()) {
                msg.append(player.getName());
                msg.append(" ");
            }
            msg.append(")");

            for (Player player : getPlayersInGame()) {
                player.sendMessage(msg.toString());
            }
        }
    }

    @Override
    public String getMinigameName() {
        return "Last one standing #" + getRoomNo();
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + getTimeLimitInMinutes() * 60);
        int teamAmount = getTeamAmount();
        for (int i = 0; i < teamAmount; i++) {
            topLines.add("Team" + (i + 1) + " lives: " + getMaxLives());
        }
        return topLines;
    }

    @Override
    public int getWinnerTeam() {
        int bestTeamIndex = -1;
        for (int teamNo : getTeams().keySet()) {
            int livesOfTeam = getLivesOfTeam(teamNo);
            if (livesOfTeam > 0) {
                bestTeamIndex = teamNo;
                break;
            }
        }
        return bestTeamIndex;
    }
}
