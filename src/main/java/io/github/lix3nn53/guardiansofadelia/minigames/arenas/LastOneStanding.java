package io.github.lix3nn53.guardiansofadelia.minigames.arenas;

import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LastOneStanding extends Minigame {

    public LastOneStanding(String mapName, int levelReq, int timeLimitInMinutes, int roomNo, List<Location> startLocations, int teamSize, int teamAmount, int maxLives, int requiredPlayerAmountToStart) {
        super("LastOneStanding", ChatColor.GOLD, mapName, roomNo, levelReq, teamSize, teamAmount, startLocations,
                timeLimitInMinutes, 1, TownManager.getTown(1), maxLives, 1, 20, requiredPlayerAmountToStart);
    }

    @Override
    public void endGame() {
        super.endGame();
        List<Integer> winnerTeam = getWinnerTeams();
        if (!winnerTeam.isEmpty()) {
            StringBuilder msg = new StringBuilder(getGameColor() + "Winner team: #" + winnerTeam + " ( ");
            Party winnerParty = getTeams().get(winnerTeam.get(0));
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
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + getTimeLimitInMinutes() * 60);
        int teamAmount = getTeamAmount();
        for (int i = 0; i < teamAmount; i++) {
            topLines.add(getTeamTextColor(i + 1) + "Team" + (i + 1) + " lives: " + getMaxLives());
        }
        return topLines;
    }

    @Override
    public List<Integer> getWinnerTeams() {
        for (int teamNo : getTeams().keySet()) {
            int livesOfTeam = getLivesOfTeam(teamNo);
            if (livesOfTeam > 0) {
                //return last alive team
                List<Integer> winner = new ArrayList<>();
                winner.add(teamNo);
                return winner;
            }
        }
        //return empty
        return new ArrayList<>();
    }
}
