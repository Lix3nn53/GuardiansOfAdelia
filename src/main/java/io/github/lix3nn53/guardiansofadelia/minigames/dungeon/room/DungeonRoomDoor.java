package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.List;

public class DungeonRoomDoor {
    private final World world;
    private final List<BoundingBox> boxes;
    private final Material material;

    public DungeonRoomDoor(World world, List<BoundingBox> boxes, Material material) {
        this.world = world;
        this.boxes = boxes;
        this.material = material;
    }

    public void close() {
        for (BoundingBox box : boxes) {
            Vector min = box.getMin();
            Vector max = box.getMax();
            for (int i = min.getBlockX(); i <= max.getBlockX(); i++) {
                for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
                    for (int k = min.getBlockZ(); k <= max.getBlockZ(); k++) {
                        Block block = world.getBlockAt(i, j, k);

                        block.setType(this.material);
                    }
                }
            }
        }
    }

    public void open() {
        for (BoundingBox box : boxes) {
            Vector min = box.getMin();
            Vector max = box.getMax();
            for (int i = min.getBlockX(); i <= max.getBlockX(); i++) {
                for (int j = min.getBlockY(); j <= max.getBlockY(); j++) {
                    for (int k = min.getBlockZ(); k <= max.getBlockZ(); k++) {
                        Block block = world.getBlockAt(i, j, k);

                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
