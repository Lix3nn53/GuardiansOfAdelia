package io.github.lix3nn53.guardiansofadelia.minigames.arenas;

import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LastOneStanding extends Minigame {

    public LastOneStanding(String mapName, int levelReq, int timeLimitInMinutes, int instanceNo, List<Location> startLocations, int teamSize, int teamAmount, int maxLives, int requiredPlayerAmountToStart) {
        super("LastOneStanding", ChatPalette.GOLD, mapName, instanceNo, levelReq, teamSize, teamAmount, startLocations,
                timeLimitInMinutes, 2, TownManager.getTown(1).getLocation(), maxLives, 1, 20, requiredPlayerAmountToStart, new ArrayList<>());
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
                MessageUtils.sendCenteredMessage(player, msg.toString());
            }
        }
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add(ChatColor.YELLOW + "Time remaining: " + ChatColor.RESET + getTimeLimitInMinutes() * 60);
        int teamAmount = getTeamAmount();
        for (int i = 0; i < teamAmount; i++) {
            topLines.add(getTeamTextColor(i + 1) + "Team" + (i + 1) + " lives: " + getMaxLives());
        }
        return topLines;
    }

    @Override
    public List<Integer> getWinnerTeams() {
        List<Integer> teamsAlive = new ArrayList<>();

        for (int teamNo : getTeams().keySet()) {
            int livesOfTeam = getLivesOfTeam(teamNo);
            if (livesOfTeam > 0) {
                teamsAlive.add(teamNo);
            }
        }

        return teamsAlive;
    }

    @Override
    public void onPlayerDealDamageToPlayer(Player attacker, Player defender) {
        //empty, kills does not give score in this mode
    }

    @Override
    public void onPlayerDeath(Player player) {
        addScore(player, -1);
        super.onPlayerDeath(player);
    }
}
