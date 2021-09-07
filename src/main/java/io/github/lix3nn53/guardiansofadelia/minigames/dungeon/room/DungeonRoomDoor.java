package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class DungeonRoomDoor {
    private final Material material;
    private final BoundingBox boundingBox;
    // State
    private boolean isOpen = false;

    public DungeonRoomDoor(Material material, BoundingBox boundingBox) {
        this.material = material;
        this.boundingBox = boundingBox;
    }

    public Material getMaterial() {
        return material;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void close(Location dungeonStart) {
        BoundingBox shift = boundingBox.clone().shift(dungeonStart);

        Vector min = shift.getMin();
        Vector max = shift.getMax();
        for (int i = min.getBlockX(); i <= max.getBlockX(); i++) {
            for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
                for (int k = min.getBlockZ(); k <= max.getBlockZ(); k++) {
                    Block block = dungeonStart.getWorld().getBlockAt(i, j, k);

                    block.setType(this.material);
                }
            }
        }

        isOpen = false;
    }

    public void open(Location dungeonStart) {
        BoundingBox shift = boundingBox.clone().shift(dungeonStart);

        Vector min = shift.getMin();
        Vector max = shift.getMax();
        for (int i = min.getBlockX(); i <= max.getBlockX(); i++) {
            for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
                for (int k = min.getBlockZ(); k <= max.getBlockZ(); k++) {
                    Block block = dungeonStart.getWorld().getBlockAt(i, j, k);

                    block.setType(Material.AIR);
                }
            }
        }

        isOpen = true;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
