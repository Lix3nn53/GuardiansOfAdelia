package io.github.lix3nn53.guardiansofadelia.minigames.arenas;

import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LastOneStanding extends Minigame {

    public LastOneStanding(int levelReq, int timeLimitInMinutes, int roomNo, List<Location> startLocations, int teamSize, int teamAmount, int maxLives, int requiredPlayerAmountToStart) {
        super(ChatColor.RED + "LastOneStanding", roomNo, levelReq, teamSize, teamAmount, startLocations,
                timeLimitInMinutes, 1, TownManager.getTown(1), maxLives, 20, requiredPlayerAmountToStart);
    }

    @Override
    public void endGame() {
        super.endGame();
        int winnerTeam = getWinnerTeam();
        if (winnerTeam >= 0) {
            StringBuilder msg = new StringBuilder(ChatColor.GOLD + "Winner team: #" + (winnerTeam + 1) + " (");
            Party winnerParty = getTeams().get(winnerTeam);
            for (Player player : winnerParty.getMembers()) {
                msg.append(player.getName());
            }
            msg.append(")");

            for (Party party : getTeams()) {
                for (Player player : party.getMembers()) {
                    player.sendMessage(msg.toString());
                }
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
            topLines.add("Team" + (i + 1) + " lives: " + getMaxLivesOfTeam());
        }
        return topLines;
    }

    @Override
    public void onPlayerDeath(Player player, Location deathLocation) {
        super.onPlayerDeath(player, deathLocation);
        int teamOfPlayer = getTeamOfPlayer(player);
        updateTeamLives(teamOfPlayer, getLivesOfTeam(teamOfPlayer));
        //check game end
        List<Party> aliveTeams = getAliveTeams();
        if (aliveTeams.size() == 1) {
            endGame();
        }
    }

    public void updateTeamLives(int teamIndex, int teamLives) {
        for (Party party : getTeams()) {
            BoardWithPlayers board = party.getBoard();
            for (int k : board.getRowLines().keySet()) {
                String s = board.getRowLines().get(k);
                if (s.contains("Team" + (teamIndex + 1) + " lives: ")) {
                    board.setLine("Team" + (teamIndex + 1) + " lives: " + teamLives, k);
                    break;
                }
            }
        }
    }

    @Override
    public int getWinnerTeam() {
        int bestTeamIndex = -1;
        for (int i = 0; i < getTeams().size(); i++) {
            int livesOfTeam = getLivesOfTeam(i);
            if (livesOfTeam > 0) {
                bestTeamIndex = i;
                break;
            }
        }
        return bestTeamIndex;
    }
}
