package io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class DungeonRoomDoor {
    private final World world;
    private final List<BoundingBox> boxes = new ArrayList<>();
    private final Material material;

    public DungeonRoomDoor(World world, Material material, Location dungeonStart, List<Vector> offsetMin, List<Vector> offsetMax) {
        this.world = world;
        this.material = material;

        for (int i = 0; i < offsetMin.size(); i++) {
            Vector min = offsetMin.get(i).add(dungeonStart.toVector());
            Vector max = offsetMax.get(i).add(dungeonStart.toVector());

            BoundingBox box = new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
            boxes.add(box);
        }
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
