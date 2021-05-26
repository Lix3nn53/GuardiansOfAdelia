package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.List;

public class DungeonRoomDoor {
    private final Material material;
    private final List<BoundingBox> boxes;

    public DungeonRoomDoor(Material material, List<BoundingBox> boxes) {
        this.material = material;
        this.boxes = boxes;
    }

    public void close(Location dungeonStart) {
        for (int boxIndex = 0; boxIndex < boxes.size(); boxIndex++) {
            BoundingBox boundingBox = boxes.get(boxIndex);

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
        }
    }

    public void open(Location dungeonStart) {
        for (int boxIndex = 0; boxIndex < boxes.size(); boxIndex++) {
            BoundingBox boundingBox = boxes.get(boxIndex);

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
        }
    }
}
