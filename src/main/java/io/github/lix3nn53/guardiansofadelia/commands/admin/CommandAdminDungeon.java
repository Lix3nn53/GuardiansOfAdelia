package io.github.lix3nn53.guardiansofadelia.commands.admin;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomDoor;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomSpawner;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandAdminDungeon implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("admindungeon")) {
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add room [theme] <roomNo>");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add door [theme] <roomNo> <material>");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add wave [theme] <instance> <room> [material] - select WorldEdit region first!");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add spawner [theme] <roomNo>");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add checkpoint [theme] <roomNo>");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon add startRoom [theme] <roomNo>");

            } else if (args[0].equals("add")) {
                if (args[1].equals("room")) {
                    String key = args[2];
                    int roomNo = Integer.parseInt(args[3]);

                    HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                    DungeonTheme dungeonTheme = dungeonThemes.get(key);

                    List<DungeonRoomDoor> doors = new ArrayList<>();
                    HashMap<Integer, List<DungeonRoomSpawner>> waves = new HashMap<>();
                    List<Integer> nextRooms = new ArrayList<>();

                    DungeonRoom dungeonRoom = new DungeonRoom(doors, waves, nextRooms);

                    dungeonTheme.addDungeonRoom(roomNo, dungeonRoom);
                    player.sendMessage("Added new room");
                } else if (args[1].equals("door")) {
                    String key = args[2];
                    int roomNo = Integer.parseInt(args[3]);
                    Material material = Material.valueOf(args[4]);

                    HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                    DungeonTheme dungeonTheme = dungeonThemes.get(key);

                    DungeonRoom room = dungeonTheme.getDungeonRoom(roomNo);

                    BukkitPlayer bPlayer = BukkitAdapter.adapt(player);
                    try {
                        Region region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection(bPlayer.getWorld());

                        BlockVector3 min = region.getMinimumPoint();
                        BlockVector3 max = region.getMaximumPoint();

                        BoundingBox boundingBox = new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

                        Location clone = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1).clone();
                        Location multiply = clone.multiply(-1);

                        BoundingBox shift = boundingBox.shift(multiply);

                        DungeonRoomDoor dungeonRoomDoor = new DungeonRoomDoor(material, shift);
                        room.addDoor(dungeonRoomDoor);
                        player.sendMessage("Added new door");

                        remakeHolograms(key);
                    } catch (IncompleteRegionException e) {
                        e.printStackTrace();
                    }
                }
            } else if (args[0].equals("addRoomSpawner")) {
                String dungeonThemeStr = args[1];
                int instance = Integer.parseInt(args[2]);
                int room = Integer.parseInt(args[3]);
                int wave = Integer.parseInt(args[4]);
                String mobCode = args[5];
                int mobLevel = Integer.parseInt(args[6]);
                int amount = Integer.parseInt(args[7]);

                HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                DungeonTheme dungeonTheme = dungeonThemes.get(dungeonThemeStr);
                DungeonRoom dungeonRoom = dungeonTheme.getDungeonRoom(room);

                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(dungeonThemeStr, instance);
                Location startLocation = dungeonInstance.getStartLocation(1);
                Block targetBlock = player.getTargetBlock(null, 12);
                Location add = targetBlock.getLocation().add(0.5, 1, 0.5);

                Vector subtract = startLocation.toVector().subtract(add.toVector());

                DungeonRoomSpawner spawner = new DungeonRoomSpawner(mobCode, mobLevel, amount, subtract);
                dungeonRoom.addSpawner(wave, spawner);
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }

    private void remakeHolograms(String themeKey) {
        for (int i = 0; i < 999; i++) {
            if (MiniGameManager.dungeonInstanceExists(themeKey, i)) {
                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(themeKey, i);

                dungeonInstance.remakeDebugHolograms();
            } else {
                break;
            }
        }
    }
}
