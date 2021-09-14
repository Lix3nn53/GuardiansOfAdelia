package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.events.MyChunkEvents;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
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

    public List<Entity> spawn(Location dungeonStart) {
        List<Entity> spawned = new ArrayList<>();
        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
        try {
            Location add = dungeonStart.clone().add(offset);
            for (int i = 0; i < amount; i++) {
                spawned.add(apiHelper.spawnMythicMob(mobCode, add, mobLevel));
            }
        } catch (InvalidMobTypeException e) {
            GuardiansOfAdelia.getInstance().getLogger().info("DungeonRoomSpawner mythicmob code error: " + mobCode);
            e.printStackTrace();
        }

        MyChunkEvents.DO_NOT_DELETE.addAll(spawned);

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
}
