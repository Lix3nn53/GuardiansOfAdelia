package io.github.lix3nn53.guardiansofadelia.minigames.arenas;

import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.party.PartyManager;
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
            StringBuilder msg = new StringBuilder(ChatColor.GOLD + "Winner team: #" + winnerTeam + " ( ");
            List<Player> winnerParty = getTeams().get(winnerTeam);
            for (Player player : winnerParty) {
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
        List<Integer> aliveTeams = getAliveTeams();
        if (aliveTeams.size() == 1) {
            endGame();
        }
    }

    public void updateTeamLives(int teamIndex, int teamLives) {
        for (Integer teamNo : getTeams().keySet()) {
            List<Player> players = getTeams().get(teamNo);
            if (!players.isEmpty()) {
                if (PartyManager.inParty(players.get(0))) {
                    Party party = PartyManager.getParty(players.get(0));
                    BoardWithPlayers board = party.getBoard();
                    for (int k : board.getRowLines().keySet()) {
                        String s = board.getRowLines().get(k);
                        if (s.contains("Team" + teamIndex + " lives: ")) {
                            board.setLine("Team" + teamIndex + " lives: " + teamLives, k);
                            break;
                        }
                    }
                }
            }
        }
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

    @Override
    public void leave(Player player) {
        super.leave(player);
        setLivesOfPlayer(player, 0);
        if (getPlayersInGame().size() <= 1) {
            endGame();
        }
    }
}
