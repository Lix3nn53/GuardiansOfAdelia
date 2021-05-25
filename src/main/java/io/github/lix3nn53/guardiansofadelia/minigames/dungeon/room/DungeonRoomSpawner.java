package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DungeonRoomSpawner {
    private final List<Location> locationList = new ArrayList<>();
    private final String petCode;
    private final int petLevel;
    private final int amount;

    public DungeonRoomSpawner(String petCode, int petLevel, int amount, Location dungeonStart, List<Vector> offsets) {
        this.petCode = petCode;
        this.petLevel = petLevel;
        this.amount = amount;

        for (int i = 0; i < offsets.size(); i++) {
            Vector offset = offsets.get(i);

            Location add = dungeonStart.clone().add(offset);

            locationList.add(add);
        }
    }

    public List<Entity> spawn() {
        List<Entity> entity = new ArrayList<>();
        BukkitAPIHelper apiHelper = MythicMobs.inst().getAPIHelper();
        try {
            for (Location location : locationList) {
                for (int i = 0; i < amount; i++) {
                    entity.add(apiHelper.spawnMythicMob(petCode, location, petLevel));
                }
            }
        } catch (InvalidMobTypeException e) {
            GuardiansOfAdelia.getInstance().getLogger().info("DungeonRoomSpawner mythicmob code error: " + petCode);
            e.printStackTrace();
        }

        return entity;
    }
}
