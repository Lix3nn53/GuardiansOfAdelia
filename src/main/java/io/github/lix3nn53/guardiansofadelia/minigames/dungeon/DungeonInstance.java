package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.events.MyChunkEvents;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.buff.BuffType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.RandomSkillOnGroundWithOffset;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.Minigame;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.*;
import io.github.lix3nn53.guardiansofadelia.party.Party;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.Scoreboard.BoardWithPlayers;
import io.github.lix3nn53.guardiansofadelia.utilities.centermessage.MessageUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.hologram.Hologram;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.HologramManager;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
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
    private final HashMap<Integer, DungeonRoomState> roomNoToRoomState = new HashMap<>();
    private final HashMap<Integer, HashMap<Integer, List<DungeonRoomSpawnerState>>> roomToWavesToSpawnerStates = new HashMap<>();
    private final List<Integer> activeRooms = new ArrayList<>();
    private int darkness = 0;
    private BukkitTask darknessRunnable;
    private final HashMap<Player, Integer> playerToLootedChestCount = new HashMap<>();
    private int unlockedChests;
    private final List<ArmorStand> skillsOnGroundArmorStands = new ArrayList<>();

    // Debug
    private final List<Hologram> debugHolograms = new ArrayList<>();

    public DungeonInstance(DungeonTheme theme, int instanceNo, List<Location> startLocation, List<Checkpoint> checkpoints) {
        super("Dungeon " + theme.getName(), ChatPalette.BLUE_LIGHT, theme.getName(), instanceNo, theme.getLevelReq()
                , 4, 1, startLocation, theme.getTimeLimitInMinutes(),
                5, MiniGameManager.getPortalLocationOfDungeonTheme(theme.getCode()), 999,
                0, 12, 1, checkpoints);
        this.theme = theme;

        reformParties();
        remakeDebugHolograms();
        setGameModeOnWin(GameMode.ADVENTURE);
        setCountDownIn5SecondsOnWin(12);
    }

    @Override
    public void startGame() {
        super.startGame();

        resetAll();

        Location startLocation = this.getStartLocation(1);

        // All rooms #onDungeonStart
        Set<Integer> dungeonRoomKeys = this.theme.getDungeonRoomKeys();
        for (int roomNo : dungeonRoomKeys) {
            DungeonRoom dungeonRoom = this.theme.getDungeonRoom(roomNo);

            dungeonRoom.onDungeonStart(startLocation);
        }

        // Start dungeon skillsOnGround that does not belong to rooms
        List<RandomSkillOnGroundWithOffset> skillsOnGround = this.theme.getSkillsOnGround();
        for (RandomSkillOnGroundWithOffset skillOnGround : skillsOnGround) {
            ArmorStand activate = skillOnGround.activate(startLocation, 40L);
            skillsOnGroundArmorStands.add(activate);
            MyChunkEvents.DO_NOT_DELETE.add(activate);
        }

        final List<Integer> startingRooms = this.theme.getStartingRooms();
        new BukkitRunnable() {
            @Override
            public void run() {
                // Starting rooms #onRoomStart
                for (int roomNo : startingRooms) {
                    DungeonRoom dungeonRoom = theme.getDungeonRoom(roomNo);
                    DungeonRoomState dungeonRoomState = roomNoToRoomState.get(roomNo);

                    HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates = roomToWavesToSpawnerStates.get(roomNo);
                    dungeonRoom.onRoomStart(dungeonRoomState, roomNo, wavesToSpawnerStates, getStartLocation(1), theme, darkness);
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
                player.removePotionEffect(PotionEffectType.POISON);
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, 4));

                List<Entity> nearbyEntities = player.getNearbyEntities(16, 16, 16);
                for (Entity entity : nearbyEntities) {
                    if (entity.getType().equals(EntityType.PLAYER)) continue;

                    entity.remove();
                }
            }
        }

        for (int roomNo : roomToWavesToSpawnerStates.keySet()) {
            HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates = roomToWavesToSpawnerStates.get(roomNo);

            for (int waveNo : wavesToSpawnerStates.keySet()) {
                List<DungeonRoomSpawnerState> spawnerStates = wavesToSpawnerStates.get(waveNo);
                for (DungeonRoomSpawnerState state : spawnerStates) {
                    state.stopSecureSpawnerRunner();
                    state.clearSpawned();
                }
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

            boolean darknessCondition = darkness < 70;
            String darknessConditionMessage = ChatPalette.YELLOW + "Darkness is less than 70: " + ChatPalette.RED + "FAIL";
            if (darknessCondition) {
                this.unlockedChests++;
                darknessConditionMessage = ChatPalette.YELLOW + "Darkness is less than 70: " + ChatPalette.GREEN_DARK + "SUCCESS";
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

        // Clear global skillsOnGround
        for (ArmorStand armorStand : skillsOnGroundArmorStands) {
            MyChunkEvents.DO_NOT_DELETE.remove(armorStand);
            armorStand.remove();
        }
        skillsOnGroundArmorStands.clear();
    }

    public void onMobKill(String internalName, Entity mob) {
        if (isInGame()) {
            if (theme.getBossInternalName().equals(internalName)) {
                for (int roomNo : activeRooms) {
                    DungeonRoom room = theme.getDungeonRoom(roomNo);
                    DungeonRoomState dungeonRoomState = roomNoToRoomState.get(roomNo);

                    HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates = roomToWavesToSpawnerStates.get(roomNo);
                    room.onRoomEnd(dungeonRoomState, this.getStartLocation(1), wavesToSpawnerStates);
                }

                updateRoomsLeftBoards();

                addScore(1, 1);
                endGame();
            } else {
                List<Integer> newActiveRooms = new ArrayList<>(this.activeRooms);

                for (int roomNo : activeRooms) {
                    DungeonRoom room = theme.getDungeonRoom(roomNo);
                    DungeonRoomState roomState = roomNoToRoomState.get(roomNo);

                    HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates = roomToWavesToSpawnerStates.get(roomNo);
                    boolean roomDone = room.onMobKill(roomState, wavesToSpawnerStates, getPlayersInGame(), roomNo, mob, getStartLocation(1), this.theme, this.darkness);

                    if (roomDone) {
                        List<Integer> nextRooms = room.onRoomEnd(roomState, this.getStartLocation(1), wavesToSpawnerStates);

                        for (int nextRoomNo : nextRooms) {
                            DungeonRoom nextRoom = theme.getDungeonRoom(nextRoomNo);
                            DungeonRoomState nextRoomState = roomNoToRoomState.get(nextRoomNo);

                            HashMap<Integer, List<DungeonRoomSpawnerState>> nextRoomWavesToSpawnerStates = roomToWavesToSpawnerStates.get(nextRoomNo);
                            nextRoom.onRoomStart(nextRoomState, nextRoomNo, nextRoomWavesToSpawnerStates, this.getStartLocation(1), this.theme, this.darkness);
                        }

                        newActiveRooms.remove(Integer.valueOf(roomNo));
                        newActiveRooms.addAll(nextRooms);

                        updateRoomsLeftBoards();
                    }
                }

                this.activeRooms.clear();
                this.activeRooms.addAll(newActiveRooms);
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

        String bossName = "NULL";
        if (theme != null) bossName = theme.getBossName();
        topLines.add(ChatColor.RED + "Boss: " + bossName);

        topLines.add(ChatColor.YELLOW + "Time remaining: " + ChatColor.RESET + getTimeLimitInMinutes() * 60);
        topLines.add(ChatColor.DARK_PURPLE + "Darkness: " + ChatColor.RESET + darkness);

        int rooms = 0;
        if (roomNoToRoomState != null) rooms = roomNoToRoomState.size();
        topLines.add(ChatColor.GOLD + "Rooms left: " + ChatColor.RESET + rooms);

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

                    int amount = spawner.getAmount();
                    hologram = new Hologram(add.clone().add(0, 0.5, 0), ChatPalette.RED + "SPAWNER x" + amount);
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
                    updateDarknessOnBoards();
                    applyDarknessEffects(periodInTicks);
                }
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 20, periodInTicks);
    }

    private void endDarknessRunnable() {
        this.darknessRunnable.cancel();
    }

    private void updateDarknessOnBoards() {
        HashMap<Integer, Party> teams = this.getTeams();

        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            if (!party.getMembers().isEmpty()) {
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Darkness: ")) {
                        board.setLine(ChatColor.DARK_PURPLE + "Darkness: " + ChatColor.RESET + darkness, k);
                        break;
                    }
                }
            }
        }
    }

    private void updateRoomsLeftBoards() {
        int left = roomNoToRoomState.size();

        for (int roomKey : roomNoToRoomState.keySet()) {
            DungeonRoomState state = roomNoToRoomState.get(roomKey);

            if (state.isClear()) {
                left--;
            }
        }

        HashMap<Integer, Party> teams = this.getTeams();

        for (Integer teamNo : teams.keySet()) {
            Party party = teams.get(teamNo);
            if (!party.getMembers().isEmpty()) {
                BoardWithPlayers board = party.getBoard();
                for (int k : board.getRowLines().keySet()) {
                    String s = board.getRowLines().get(k);
                    if (s.contains("Rooms left: ")) {
                        board.setLine(ChatColor.GOLD + "Rooms left: " + ChatColor.RESET + left, k);
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
        for (int roomKey : roomNoToRoomState.keySet()) {
            DungeonRoomState state = roomNoToRoomState.get(roomKey);

            if (!state.isClear()) {
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
        if (this.darkness < 0) this.darkness = 0;
    }

    private void resetAll() {
        roomNoToRoomState.clear();
        roomToWavesToSpawnerStates.clear();

        Set<Integer> dungeonRoomKeys = theme.getDungeonRoomKeys();

        for (int roomKey : dungeonRoomKeys) {
            roomNoToRoomState.put(roomKey, new DungeonRoomState());

            DungeonRoom dungeonRoom = theme.getDungeonRoom(roomKey);
            HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners = dungeonRoom.getWaveToSpawners();

            HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates = new HashMap<>();
            for (int waveNo : waveToSpawners.keySet()) {
                List<DungeonRoomSpawner> roomSpawners = waveToSpawners.get(waveNo);

                List<DungeonRoomSpawnerState> waveToSpawnerStates = new ArrayList<>();
                for (int spawnerIndex = 0; spawnerIndex < roomSpawners.size(); spawnerIndex++) {
                    waveToSpawnerStates.add(new DungeonRoomSpawnerState());
                }

                wavesToSpawnerStates.put(waveNo, waveToSpawnerStates);
            }
            roomToWavesToSpawnerStates.put(roomKey, wavesToSpawnerStates);
        }

        // Reset active rooms
        activeRooms.clear();
        activeRooms.addAll(this.theme.getStartingRooms());

        // Clear chest loot count
        playerToLootedChestCount.clear();

        // Clear global skillsOnGround
        for (ArmorStand armorStand : skillsOnGroundArmorStands) {
            MyChunkEvents.DO_NOT_DELETE.remove(armorStand);
            armorStand.remove();
        }
        skillsOnGroundArmorStands.clear();
    }

    @Override
    public boolean onPlayerDealDamageToEntity(Player attacker, LivingEntity defender) {
        ActiveMob mythicMobInstance = MythicMobs.inst().getMobManager().getMythicMobInstance(defender);

        if (mythicMobInstance != null) {
            String internalName = mythicMobInstance.getType().getInternalName();

            if (internalName.equals(this.theme.getBossInternalName())) { // BOSS
                return this.theme.canAttackBoss(this.getStartLocation(1), attacker);
            }
        }

        return true;
    }
}
