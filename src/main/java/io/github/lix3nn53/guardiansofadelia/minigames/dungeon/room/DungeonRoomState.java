package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.entity.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public class DungeonRoomState {
    private int currentWave = 1;
    private boolean isClear = false;

    List<ArmorStand> skillsOnGroundArmorStands = new ArrayList<>();

    public int getCurrentWave() {
        return currentWave;
    }

    public void onNextWaveStart() {
        currentWave++;
    }

    public static boolean isWaveCompleted(List<DungeonRoomSpawnerState> spawnerStates) {
        for (DungeonRoomSpawnerState spawnerState : spawnerStates) {
            if (!spawnerState.isClear()) {
                return false;
            }
        }

        return true;
    }

    public void reset() {
        currentWave = 1;
        isClear = false;
    }

    public boolean isClear() {
        return isClear;
    }

    public void onComplete() {
        isClear = true;
    }

    public void addSkillsOnGroundArmorStand(ArmorStand add) {
        skillsOnGroundArmorStands.add(add);
    }

    public void clearSkillsOnGroundArmorStands() {
        for (ArmorStand armorStand : skillsOnGroundArmorStands) {
            armorStand.remove();
        }

        skillsOnGroundArmorStands.clear();
    }
}
