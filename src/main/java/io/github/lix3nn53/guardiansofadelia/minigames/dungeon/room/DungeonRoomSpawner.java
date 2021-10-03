package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.events.MyChunkEvents;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DungeonRoomSpawner {
    private final String mobCode;
    private final int mobLevel;
    private final int amount;
    private final Vector offset;

    public DungeonRoomSpawner(String mobCode, int mobLevel, int amount, Vector offset) {
        this.mobCode = mobCode;
        this.mobLevel = mobLevel;
        this.amount = amount;
        this.offset = offset;
    }

    public List<Entity> firstSpawn(Location dungeonStart, int roomNo, int spawnerIndex, DungeonRoomSpawnerState spawnerState) {
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
        spawnerState.startSecureSpawnerRunner(dungeonStart, this, roomNo, spawnerIndex);

        return spawned;
    }

    /**
     * Check around the spawner and
     *
     * @param dungeonStart
     * @return
     */
    public List<Entity> secureSpawn(Location dungeonStart, int roomNo, int spawnerIndex, int mobsLeftToKill) {
        String spawnerKey = roomNo + "-" + spawnerIndex;

        List<Entity> spawned = new ArrayList<>();

        World world = dungeonStart.getWorld();

        Location add = dungeonStart.clone().add(offset);

        Collection<Entity> nearbyEntities = world.getNearbyEntities(add, 32, 32, 32);

        int count = 0;
        for (Entity entity : nearbyEntities) {
            if (PersistentDataContainerUtil.hasString(entity, "spawnerKey")) {
                String mobSpawnerKey = PersistentDataContainerUtil.getString(entity, "spawnerKey");
                if (spawnerKey.equals(mobSpawnerKey)) {
                    count++;
                }
            }
        }

        int amountToSpawn = mobsLeftToKill - count;

        if (amountToSpawn <= 0) return spawned;

        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();

        try {
            for (int i = 0; i < amountToSpawn; i++) {
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

    public String getMobCode() {
        return mobCode;
    }

    public int getMobLevel() {
        return mobLevel;
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
}
