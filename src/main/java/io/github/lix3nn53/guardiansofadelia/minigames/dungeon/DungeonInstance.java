package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomDoor;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomSpawner;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomState;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.HologramManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DungeonInstance extends Minigame {

    private final DungeonTheme theme;

    private final HashMap<Integer, DungeonRoomState> dungeonRoomStates = new HashMap<>();
    // State
    private List<Integer> activeRooms = new ArrayList<>();
    // Debug
    private final List<Hologram> debugHolograms = new ArrayList<>();

    public DungeonInstance(DungeonTheme theme, int instanceNo, List<Location> startLocation, List<Checkpoint> checkpoints) {
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
        remakeDebugHolograms();
    }

    @Override
    public void startGame() {
        super.startGame();

        List<Integer> startingRooms = this.theme.getStartingRooms();

        Set<Integer> dungeonRoomKeys = this.theme.getDungeonRoomKeys();
        for (int roomNo : dungeonRoomKeys) {
            DungeonRoom dungeonRoom = this.theme.getDungeonRoom(roomNo);

            dungeonRoom.onDungeonStart(this.getStartLocation(1));
        }

        for (int roomNo : startingRooms) {
            DungeonRoom dungeonRoom = this.theme.getDungeonRoom(roomNo);
            DungeonRoomState state = dungeonRoomStates.get(roomNo);
            dungeonRoom.onRoomStart(state, this.getStartLocation(1));
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
                    boolean roomDone = room.onMobKill(state, getPlayersInGame(), roomNo, internalName, getStartLocation(1));

                    if (roomDone) {
                        List<Integer> nextRooms = room.onRoomEnd(this.getStartLocation(1));

                        for (int nextRoomNo : nextRooms) {
                            DungeonRoom nextRoom = theme.getDungeonRoom(nextRoomNo);
                            DungeonRoomState nextRoomState = dungeonRoomStates.get(nextRoomNo);
                            nextRoom.onRoomStart(nextRoomState, this.getStartLocation(1));
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
        String bossName = "NULL";
        if (theme != null) {
            bossName = theme.getBossName();
        }
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

    public void clearDebugHolograms() {
        for (Hologram hologram : debugHolograms) {
            HologramManager.removeHologram(hologram);
        }
        debugHolograms.clear();
    }

    public void remakeDebugHolograms() {
        clearDebugHolograms();

        Set<Integer> dungeonRoomKeys = theme.getDungeonRoomKeys();

        Location startLocation = getStartLocation(1);

        for (int roomKey : dungeonRoomKeys) {
            DungeonRoom room = theme.getDungeonRoom(roomKey);

            List<DungeonRoomDoor> doors = room.getDoors();
            for (int i = 0; i < doors.size(); i++) {
                DungeonRoomDoor door = doors.get(i);
                Vector center = door.getBoundingBox().getCenter().clone();

                Location add = startLocation.clone().add(center);

                String state = "Closed";
                if (door.isOpen()) {
                    state = "Open";
                }

                Hologram hologram = new Hologram(add, ChatColor.YELLOW + "Room-" + roomKey + " Door-" + i + "(" + state + ")");
                HologramManager.addHologram(hologram);
                debugHolograms.add(hologram);
            }

            HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners = room.getWaveToSpawners();
            for (int i : waveToSpawners.keySet()) {
                List<DungeonRoomSpawner> spawners = waveToSpawners.get(i);

                for (int y = 0; y < spawners.size(); y++) {
                    DungeonRoomSpawner spawner = spawners.get(y);

                    Vector offset = spawner.getOffset();

                    Location add = startLocation.clone().add(offset);

                    Hologram hologram = new Hologram(add, ChatColor.RED + "Room-" + roomKey + " Wave-" + i + " Spawner-" + y);
                    HologramManager.addHologram(hologram);
                    debugHolograms.add(hologram);

                    String mobCode = spawner.getMobCode();
                    int mobLevel = spawner.getMobLevel();
                    int amount = spawner.getAmount();
                    hologram = new Hologram(add.clone().add(0, 0.5, 0), ChatColor.RED + mobCode + " lv" + mobLevel + " x" + amount);
                    HologramManager.addHologram(hologram);
                    debugHolograms.add(hologram);
                }
            }
        }
    }

    public DungeonTheme getTheme() {
        return theme;
    }
}
