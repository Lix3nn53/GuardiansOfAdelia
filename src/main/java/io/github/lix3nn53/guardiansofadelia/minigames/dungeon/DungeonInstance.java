package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomDoor;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomSpawner;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomState;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.HologramManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DungeonInstance extends Minigame {

    private final DungeonTheme theme;

    // State
    private final HashMap<Integer, DungeonRoomState> dungeonRoomStates = new HashMap<>();
    private List<Integer> activeRooms = new ArrayList<>();
    private int darkness = 0;
    private BukkitTask darknessRunnable;
    private final HashMap<Player, Integer> playerToLootedChestCount = new HashMap<>();
    private int unlockedChests;

    // Debug
    private final List<Hologram> debugHolograms = new ArrayList<>();

    public DungeonInstance(DungeonTheme theme, int instanceNo, List<Location> startLocation, List<Checkpoint> checkpoints) {
        super("Dungeon " + theme.getName(), ChatPalette.BLUE_LIGHT, theme.getName(), instanceNo, theme.getLevelReq()
                , 4, 1, startLocation, theme.getTimeLimitInMinutes(),
                5, MiniGameManager.getPortalLocationOfDungeonTheme(theme.getCode()), 999,
                0, 12, 1, checkpoints);
        this.theme = theme;

        Set<Integer> dungeonRoomKeys = theme.getDungeonRoomKeys();

        for (int i : dungeonRoomKeys) {
            dungeonRoomStates.put(i, new DungeonRoomState());
        }

        reformParties();
        remakeDebugHolograms();
        setGameModeOnWin(GameMode.ADVENTURE);
        setCountDownIn5SecondsOnWin(12);
    }

    @Override
    public void startGame() {
        super.startGame();

        // Reset starting rooms
        List<Integer> startingRooms = this.theme.getStartingRooms();
        activeRooms = startingRooms;

        // Reset room states
        for (int i : dungeonRoomStates.keySet()) {
            DungeonRoomState dungeonRoomState = dungeonRoomStates.get(i);
            dungeonRoomState.reset();
        }

        // All rooms #onDungeonStart
        Set<Integer> dungeonRoomKeys = this.theme.getDungeonRoomKeys();
        for (int roomNo : dungeonRoomKeys) {
            DungeonRoom dungeonRoom = this.theme.getDungeonRoom(roomNo);

            dungeonRoom.onDungeonStart(this.getStartLocation(1));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                // Starting rooms #onRoomStart
                for (int roomNo : startingRooms) {
                    DungeonRoom dungeonRoom = theme.getDungeonRoom(roomNo);
                    DungeonRoomState state = dungeonRoomStates.get(roomNo);
                    dungeonRoom.onRoomStart(state, getStartLocation(1));
                }

                startDarknessRunnable();
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 4);
    }

    @Override
    public void endGame() {
        super.endGame();

        HashMap<Integer, Party> teams = getTeams();

        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            for (Player player : party.getMembers()) {
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                player.removePotionEffect(PotionEffectType.WITHER);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 4));
            }
        }

        List<Integer> winnerTeam = getWinnerTeams();
        if (!winnerTeam.isEmpty()) {
            // Prize Chests
            Location startLocation = getStartLocation(1);
            Vector prizeChestCenterOffset = theme.getPrizeChestCenterOffset();

            Location center = startLocation.clone().add(prizeChestCenterOffset);

            DungeonPrizeChestManager.spawnPrizeChests(theme, center, 4);

            Party party = teams.get(winnerTeam.get(0));

            // Update unlockedChests
            this.unlockedChests = 1;
            this.playerToLootedChestCount.clear();

            boolean darknessCondition = darkness <= 50;
            String darknessConditionMessage = ChatPalette.YELLOW + "Darkness is less than 50: " + ChatPalette.RED + "FAIL";
            if (darknessCondition) {
                this.unlockedChests++;
                darknessConditionMessage = ChatPalette.YELLOW + "Darkness is less than 50: " + ChatPalette.GREEN_DARK + "SUCCESS";
            }

            boolean roomCondition = isAllRoomsCleared();
            String roomConditionMessage = ChatPalette.YELLOW + "All rooms cleared: " + ChatPalette.RED + "FAIL";
            if (roomCondition) {
                this.unlockedChests++;
                roomConditionMessage = ChatPalette.YELLOW + "All rooms cleared: " + ChatPalette.GREEN_DARK + "SUCCESS";
            }

            for (Player member : party.getMembers()) {
                MessageUtils.sendCenteredMessage(member, getGameColor() + "Dungeon Prize Chests Spawned!");
                MessageUtils.sendCenteredMessage(member, ChatPalette.YELLOW + "Dungeon Boss Defeated: " + ChatPalette.GREEN_DARK + "SUCCESS");
                MessageUtils.sendCenteredMessage(member, darknessConditionMessage);
                MessageUtils.sendCenteredMessage(member, roomConditionMessage);
                MessageUtils.sendCenteredMessage(member, ChatPalette.GOLD + "You got " + this.unlockedChests + "/3 keys! Use them to loot prize chests!");
            }
        }

        endDarknessRunnable();
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
    public void onPlayerDeath(Player player) {
        super.onPlayerDeath(player);

        this.darkness += 5;
    }

    @Override
    public List<String> getScoreboardTopLines() {
        List<String> topLines = new ArrayList<>();
        topLines.add("Time remaining: " + getTimeLimitInMinutes() * 60);
        topLines.add("Darkness: " + darkness);
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
        if (!CommandAdmin.DEBUG_MODE) return;

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

                Hologram hologram = new Hologram(add, ChatPalette.YELLOW + "Room-" + roomKey + " Door-" + i + "(" + state + ")");
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

                    Hologram hologram = new Hologram(add, ChatPalette.RED + "Room-" + roomKey + " Wave-" + i + " Spawner-" + y);
                    HologramManager.addHologram(hologram);
                    debugHolograms.add(hologram);

                    String mobCode = spawner.getMobCode();
                    int mobLevel = spawner.getMobLevel();
                    int amount = spawner.getAmount();
                    hologram = new Hologram(add.clone().add(0, 0.5, 0), ChatPalette.RED + mobCode + " lv" + mobLevel + " x" + amount);
                    HologramManager.addHologram(hologram);
                    debugHolograms.add(hologram);
                }
            }
        }
    }

    public DungeonTheme getTheme() {
        return theme;
    }

    private void startDarknessRunnable() {
        this.darkness = 0;

        int timeLimitInMinutes = this.getTimeLimitInMinutes();

        float secondsToReachMaxDarkness = timeLimitInMinutes * 60f * 0.9f;

        float periodInSeconds = secondsToReachMaxDarkness / 100;

        long periodInTicks = (long) (periodInSeconds * 20);

        this.darknessRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (darkness < 100) {
                    darkness++;
                    updateDarknessBoards();
                    applyDarknessEffects(periodInTicks);
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20, periodInTicks);
    }

    private void endDarknessRunnable() {
        this.darknessRunnable.cancel();
    }

    private void updateDarknessBoards() {
        HashMap<Integer, Party> teams = this.getTeams();

        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            if (!party.getMembers().isEmpty()) {
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Darkness: ")) {
                        board.setLine("Darkness: " + darkness, k);
                        break;
                    }
                }
            }
        }
    }

    private void applyDarknessEffects(long duration) {
        for (Player player : this.getPlayersInGame()) {
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                    RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                    if (darkness == 0) return;

                    float multiplier = darkness / 200f;

                    PotionEffect potionEffect = new PotionEffect(BuffType.ELEMENT_DAMAGE.getPotionEffectType(), (int) duration, 0);
                    rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_DAMAGE, -multiplier, potionEffect);

                    PotionEffect potionEffect2 = new PotionEffect(BuffType.ELEMENT_DEFENSE.getPotionEffectType(), (int) duration, 0);
                    rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_DEFENSE, -multiplier, potionEffect2);

                    new BukkitRunnable() { // remove buffs from buffed players after timeout

                        @Override
                        public void run() {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();

                            rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_DAMAGE, multiplier, potionEffect);
                            rpgCharacterStats.addToBuffMultiplier(BuffType.ELEMENT_DEFENSE, multiplier, potionEffect2);

                            cancel();
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), duration);
                }
            }
        }
    }


    private boolean isAllRoomsCleared() {
        int notClear = 0;
        for (int i : dungeonRoomStates.keySet()) {
            DungeonRoomState dungeonRoomState = dungeonRoomStates.get(i);
            if (!dungeonRoomState.isClear()) {
                notClear++;
                if (notClear > 1) { // Boss room is never cleared
                    return false;
                }
            }
        }

        return true;
    }

    public boolean canLootPrizeChest(Player player) {
        int alreadyGot = 0;
        if (playerToLootedChestCount.containsKey(player)) {
            alreadyGot = playerToLootedChestCount.get(player);
        }

        return alreadyGot < this.unlockedChests;
    }

    public void onLootPrizeChest(Player player) {
        int alreadyGot = 0;
        if (playerToLootedChestCount.containsKey(player)) {
            alreadyGot = playerToLootedChestCount.get(player);
        }
        alreadyGot++;

        playerToLootedChestCount.put(player, alreadyGot);
        player.sendMessage(ChatPalette.YELLOW + "Remaining keys: " + ChatPalette.GOLD + (this.unlockedChests - alreadyGot));
    }

    public void addDarkness(int add) {
        this.darkness += add;
    }
}
