package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGroundWithOffset;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DungeonRoom {

    private final List<DungeonRoomDoor> doors;
    private final HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners;
    private final List<SkillOnGroundWithOffset> skillsOnGround;

    /**
     * Rooms to start after this room ends
     */
    private final List<Integer> nextRooms;

    public DungeonRoom(List<DungeonRoomDoor> doors, HashMap<Integer, List<DungeonRoomSpawner>> waveToSpawners,
                       List<SkillOnGroundWithOffset> skillsOnGround, List<Integer> nextRooms) {
        this.doors = doors;
        this.waveToSpawners = waveToSpawners;
        this.skillsOnGround = skillsOnGround;
        this.nextRooms = nextRooms;
    }

    public void onDungeonStart(Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.close(dungeonStart);
        }
    }

    public void onRoomStart(int roomNo, HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates, Location dungeonStart) {
        for (DungeonRoomDoor door : doors) {
            door.close(dungeonStart);
        }

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(1);
        List<DungeonRoomSpawnerState> spawnerStates = wavesToSpawnerStates.get(1);
        for (int spawnerIndex = 0; spawnerIndex < spawners.size(); spawnerIndex++) {
            DungeonRoomSpawner spawner = spawners.get(spawnerIndex);

            DungeonRoomSpawnerState spawnerState = spawnerStates.get(spawnerIndex);

            spawner.firstSpawn(dungeonStart, roomNo, spawnerIndex, spawnerState);
        }

        for (SkillOnGroundWithOffset skillOnGround : skillsOnGround) {
            skillOnGround.activate(dungeonStart, 40L);
        }
    }

    /**
     * @param players
     * @param roomNo
     * @return true if room completed, false otherwise
     */
    public boolean onMobKill(DungeonRoomState roomState, HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates, List<Player> players, int roomNo, Entity mob, Location dungeonStart) {
        boolean thisRoomsMob = false;

        int currentWave = roomState.getCurrentWave();

        List<DungeonRoomSpawner> spawners = waveToSpawners.get(currentWave);
        List<DungeonRoomSpawnerState> spawnerStates = wavesToSpawnerStates.get(currentWave); // get spawner states of current wave
        for (int spawnerIndex = 0; spawnerIndex < spawners.size(); spawnerIndex++) {
            DungeonRoomSpawner spawner = spawners.get(spawnerIndex);

            if (spawner.thisSpawnersMob(mob, roomNo, spawnerIndex)) {
                DungeonRoomSpawnerState spawnerState = spawnerStates.get(spawnerIndex);
                spawnerState.onMobKill(1);
                thisRoomsMob = true;
                break;
            }
        }

        if (!thisRoomsMob) {
            return false;
        }

        if (DungeonRoomState.isWaveCompleted(spawnerStates)) {
            currentWave++;

            if (waveToSpawners.containsKey(currentWave)) {
                roomState.onNextWaveStart();

                for (Player player : players) {
                    player.sendMessage(ChatPalette.PURPLE_LIGHT + "ROOM-" + roomNo + " WAVE-" + currentWave + " incoming!");
                    player.sendTitle(ChatColor.WHITE + "", ChatPalette.PURPLE_LIGHT + "ROOM-" + roomNo + " WAVE-" + currentWave + " incoming!", 15, 40, 15);
                }

                List<DungeonRoomSpawner> newSpawners = waveToSpawners.get(currentWave);
                List<DungeonRoomSpawnerState> newSpawnerStates = wavesToSpawnerStates.get(currentWave);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (int spawnerIndex = 0; spawnerIndex < newSpawners.size(); spawnerIndex++) {
                            DungeonRoomSpawner spawner = newSpawners.get(spawnerIndex);

                            DungeonRoomSpawnerState spawnerState = newSpawnerStates.get(spawnerIndex);
                            spawner.firstSpawn(dungeonStart, roomNo, spawnerIndex, spawnerState);
                        }
                    }
                }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 4L);


            } else {
                for (Player player : players) {
                    player.sendMessage(ChatPalette.PURPLE_LIGHT + "ROOM-" + roomNo + " completed!");
                    player.sendTitle(ChatColor.WHITE + "", ChatPalette.PURPLE_LIGHT + "ROOM-" + roomNo + " completed!", 15, 40, 15);
                }

                roomState.onComplete();

                return true;
            }
        }

        return false;
    }

    public List<Integer> onRoomEnd(Location dungeonStart, HashMap<Integer, List<DungeonRoomSpawnerState>> wavesToSpawnerStates) {
        for (DungeonRoomDoor door : doors) {
            door.open(dungeonStart);
        }
        for (SkillOnGroundWithOffset skillOnGround : skillsOnGround) {
            skillOnGround.deactivate();
        }

        for (List<DungeonRoomSpawnerState> spawnerStates : wavesToSpawnerStates.values()) {
            for (DungeonRoomSpawnerState spawnerState : spawnerStates) {
                spawnerState.stopSecureSpawnerRunner();
            }
        }

        return nextRooms;
    }

    public void addSpawner(int wave, DungeonRoomSpawner spawner) {
        List<DungeonRoomSpawner> spawners = new ArrayList<>();
        if (waveToSpawners.containsKey(wave)) {
            spawners = waveToSpawners.get(wave);
        }

        spawners.add(spawner);
        waveToSpawners.put(wave, spawners);
    }

    public void addDoor(DungeonRoomDoor door) {
        doors.add(door);
    }

    public List<DungeonRoomDoor> getDoors() {
        return doors;
    }

    public HashMap<Integer, List<DungeonRoomSpawner>> getWaveToSpawners() {
        return waveToSpawners;
    }

    public List<Integer> getNextRooms() {
        return nextRooms;
    }

    public void addSkillOnGround(SkillOnGroundWithOffset skill) {
        skillsOnGround.add(skill);
    }

    public List<SkillOnGroundWithOffset> getSkillsOnGround() {
        return skillsOnGround;
    }
}
