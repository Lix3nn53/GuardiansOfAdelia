package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomState;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Dungeon extends Minigame {

    private final DungeonTheme theme;

    private final HashMap<Integer, DungeonRoomState> dungeonRoomStates = new HashMap<>();
    // State
    private List<Integer> activeRooms = new ArrayList<>();

    public Dungeon(DungeonTheme theme, int instanceNo, List<Location> startLocation, List<Checkpoint> checkpoints) {
        super("Dungeon " + theme.getName(), ChatColor.AQUA, theme.getName(), instanceNo, theme.getLevelReq()
                , 4, 1, startLocation, theme.getTimeLimitInMinutes(),
                5, MiniGameManager.getPortalLocationOfDungeonTheme(theme.getCode()), 4,
                0, 12, 1, checkpoints);
        this.theme = theme;

        Set<Integer> dungeonRoomKeys = theme.getDungeonRoomKeys();

        for (int i : dungeonRoomKeys) {
            dungeonRoomStates.put(i, new DungeonRoomState());
        }

        reformParties();
    }

    @Override
    public void startGame() {
        super.startGame();

        List<Integer> startingRooms = this.theme.getStartingRooms();

        for (int roomNo : startingRooms) {
            DungeonRoom dungeonRoom = this.theme.getDungeonRoom(roomNo);
            DungeonRoomState state = dungeonRoomStates.get(roomNo);
            dungeonRoom.onRoomStart(state);
        }

        activeRooms = startingRooms;
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

    public void onMobKill(String internalName) {
        if (isInGame()) {
            if (theme.getBossInternalName().equals(internalName)) {
                addScore(1, 1);
                endGame();
            } else {
                for (int roomNo : activeRooms) {
                    DungeonRoom room = theme.getDungeonRoom(roomNo);
                    DungeonRoomState state = dungeonRoomStates.get(roomNo);
                    boolean roomDone = room.onMobKill(state, getPlayersInGame(), roomNo, internalName);

                    if (roomDone) {
                        List<Integer> nextRooms = room.onRoomEnd();

                        for (int nextRoomNo : nextRooms) {
                            DungeonRoom nextRoom = theme.getDungeonRoom(nextRoomNo);
                            DungeonRoomState nextRoomState = dungeonRoomStates.get(nextRoomNo);
                            nextRoom.onRoomStart(nextRoomState);
                        }

                        this.activeRooms = nextRooms;
                    }
                }
            }
        }
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + getTimeLimitInMinutes() * 60);
        topLines.add(getTeamTextColor(1) + "Team" + 1 + " lives: " + getMaxLives());
        topLines.add("Boss: " + theme.getBossName());
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
