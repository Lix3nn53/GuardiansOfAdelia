package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.events.MyChunkEvents;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DungeonRoomSpawner {

    private final int amount;
    private final Vector offset;
    private final boolean isBoss;

    public DungeonRoomSpawner(int amount, Vector offset, boolean isBoss) {
        this.amount = amount;
        this.offset = offset;
        this.isBoss = isBoss;
    }

    public List<Entity> firstSpawn(String mobCode, int mobLevel, Location dungeonStart, int roomNo, int spawnerIndex, DungeonRoomSpawnerState spawnerState) {
        String spawnerKey = roomNo + "-" + spawnerIndex;

        Location add = dungeonStart.clone().add(offset);

        List<Entity> spawned = new ArrayList<>();
        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
        try {
            for (int i = 0; i < amount; i++) {
                Entity entity = apiHelper.spawnMythicMob(mobCode, add, mobLevel);
                PersistentDataContainerUtil.putString("spawnerKey", spawnerKey, entity);
                spawned.add(entity);
            }
        } catch (InvalidMobTypeException e) {
            GuardiansOfAdelia.getInstance().getLogger().info("DungeonRoomSpawner mythicmob code error: " + mobCode);
            e.printStackTrace();
        }

        MyChunkEvents.DO_NOT_DELETE.addAll(spawned);

        // Start secure spawner runner
        spawnerState.startSecureSpawnerRunner(mobCode, mobLevel, dungeonStart, this, roomNo, spawnerIndex);

        spawnerState.onFirstSpawn(spawned);

        return spawned;
    }

    /**
     * Check around the spawner and
     *
     * @param dungeonStart
     * @return
     */
    public List<Entity> secureSpawn(String mobCode, int mobLevel, Location dungeonStart, int roomNo,
                                    int spawnerIndex, int mobsLeftToKill) {
        String spawnerKey = roomNo + "-" + spawnerIndex;

        List<Entity> spawned = new ArrayList<>();

        if (mobsLeftToKill <= 0) return spawned;

        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();

        try {
            Location add = dungeonStart.clone().add(offset);

            for (int i = 0; i < mobsLeftToKill; i++) {
                Entity entity = apiHelper.spawnMythicMob(mobCode, add, mobLevel);
                PersistentDataContainerUtil.putString("spawnerKey", spawnerKey, entity);
                spawned.add(entity);
            }
        } catch (InvalidMobTypeException e) {
            GuardiansOfAdelia.getInstance().getLogger().info("DungeonRoomSpawner mythicmob code error: " + mobCode);
            e.printStackTrace();
        }

        return spawned;
    }

    public int getAmount() {
        return amount;
    }

    public Vector getOffset() {
        return offset;
    }

    public boolean thisSpawnersMob(Entity entity, int roomNo, int spawnerIndex) {
        if (!PersistentDataContainerUtil.hasString(entity, "spawnerKey")) {
            return false;
        }

        String spawnerKey = roomNo + "-" + spawnerIndex;
        return PersistentDataContainerUtil.getString(entity, "spawnerKey").equals(spawnerKey);
    }

    public boolean isBoss() {
        return isBoss;
    }
}
