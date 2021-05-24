package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Dungeon extends Minigame {

    private final DungeonTheme theme;
    private final String bossName;

    public Dungeon(DungeonTheme theme, int roomNo, List<Location> startLocation, List<Checkpoint> checkpoints) {
        super("Dungeon " + theme.getName(), ChatColor.AQUA, theme.getName(), roomNo, theme.getLevelReq()
                , 4, 1, startLocation, theme.getTimeLimitInMinutes(),
                5, MiniGameManager.getPortalLocationOfDungeonTheme(theme.getCode()), 4,
                0, 12, 1, checkpoints);
        this.theme = theme;
        this.bossName = theme.getBossName();
        reformParties();
    }

    @Override
    public void endGame() {
        super.endGame();
        List<Integer> winnerTeam = getWinnerTeams();
        if (!winnerTeam.isEmpty()) {
            ItemStack prizeItem = theme.getPrizeChest().getChest();

            Party party = getTeams().get(winnerTeam.get(0));
            for (Player member : party.getMembers()) {
                MessageUtils.sendCenteredMessage(member, ChatColor.GRAY + "------------------------");
                MessageUtils.sendCenteredMessage(member, getGameColor() + "Dungeon Prize");
                MessageUtils.sendCenteredMessage(member, prizeItem.getItemMeta().getDisplayName());
                MessageUtils.sendCenteredMessage(member, ChatColor.GRAY + "------------------------");
                InventoryUtils.giveItemToPlayer(member, prizeItem);
            }
        }
    }

    public void onBossKill(String internalName) {
        if (isInGame() && theme.getBossInternalName().equals(internalName)) {
            addScore(1, 1);
            endGame();
        }
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + getTimeLimitInMinutes() * 60);
        topLines.add(getTeamTextColor(1) + "Team" + 1 + " lives: " + getMaxLives());
        topLines.add("Boss: " + bossName);
        return topLines;
    }

    @Override
    public List<Integer> getWinnerTeams() {
        List<Integer> teamsAtBestScore = new ArrayList<>();
        for (int team : getTeams().keySet()) {
            int teamScore = getScoreOfTeam(team);
            if (teamScore > 0) {
                List<Integer> winner = new ArrayList<>();
                winner.add(team);
                return winner;
            }
        }
        return teamsAtBestScore;
    }
}
