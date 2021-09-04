package io.github.lix3nn53.guardiansofadelia.commands.admin;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomDoor;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomSpawner;
import org.bukkit.Bukkit;
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
                player.sendMessage(ChatColor.YELLOW + "/admindungeon addRoom [theme] <roomNo>");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon addRoomSpawner [theme] <instance> <room> <wave> [mobCode] <mobLevel> <amount>");
                player.sendMessage(ChatColor.YELLOW + "/admindungeon addRoomDoor [theme] <instance> <room> [material] - select WorldEdit region first!");

            } else if (args[0].equals("addRoom")) {
                String dungeonThemeStr = args[1];
                int roomNo = Integer.parseInt(args[2]);

                HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                DungeonTheme dungeonTheme = dungeonThemes.get(dungeonThemeStr);

                List<DungeonRoomDoor> doors = new ArrayList<>();
                HashMap<Integer, List<DungeonRoomSpawner>> waves = new HashMap<>();
                List<Integer> nextRooms = new ArrayList<>();

                DungeonRoom dungeonRoom = new DungeonRoom(doors, waves, nextRooms);

                dungeonTheme.addDungeonRoom(roomNo, dungeonRoom);
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
            } else if (args[0].equals("addRoomDoor")) {
                String dungeonThemeStr = args[1];
                int instance = Integer.parseInt(args[2]);
                int room = Integer.parseInt(args[3]);
                Material material = Material.valueOf(args[4]);

                HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                DungeonTheme dungeonTheme = dungeonThemes.get(dungeonThemeStr);
                DungeonRoom dungeonRoom = dungeonTheme.getDungeonRoom(room);

                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(dungeonThemeStr, instance);
                Location startLocation = dungeonInstance.getStartLocation(1);

                WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
                LocalSession session = worldEdit.getSession(player);
                try {
                    Region selection = session.getSelection(session.getSelectionWorld());

                    BlockVector3 min = selection.getMinimumPoint();
                    BlockVector3 max = selection.getMaximumPoint();

                    BoundingBox boundingBox = new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

                    Location clone = startLocation.clone();
                    Location multiply = clone.multiply(-1);

                    BoundingBox shift = boundingBox.shift(multiply);

                    DungeonRoomDoor dungeonRoomDoor = new DungeonRoomDoor(material, shift);
                    dungeonRoom.addDoor(dungeonRoomDoor);
                } catch (IncompleteRegionException e) {
                    e.printStackTrace();
                    player.sendMessage("ERROR WHILE ADD DUNGEON ROOM");
                }
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        return false;
    }
}
