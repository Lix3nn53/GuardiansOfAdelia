package io.github.lix3nn53.guardiansofadelia.commands.admin;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.SelfTarget;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.RandomSkillOnGroundWithOffset;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillListForGround;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.SkillOnGround;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.checkpoint.Checkpoint;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomDoor;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoomSpawner;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.config.DungeonConfiguration;
import io.github.lix3nn53.guardiansofadelia.utilities.config.SkillOnGroundConfigurations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.*;

public class CommandAdminDungeon implements CommandExecutor {

    private static final Map<Player, String> selectedDungeon = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equals("admindungeon")) {
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length < 1) {
                player.sendMessage(ChatPalette.YELLOW + "/admindungeon select [theme]");
                player.sendMessage(ChatPalette.YELLOW + "/admindungeon tp <instanceNo>");
                player.sendMessage(ChatPalette.YELLOW + "/admindungeon holo - remakes holograms");
                player.sendMessage(ChatPalette.YELLOW + "/admindungeon door - close/open doors");
                player.sendMessage(ChatPalette.YELLOW + "/admindungeon reload - DELETES NEW CHANGES");
                player.sendMessage(ChatPalette.YELLOW + "/admindungeon save - SAVES NEW CHANGES");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon add room <roomNo>");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon add door <roomNo> <material>" + ChatPalette.GOLD + " !!select WorldEdit region first!!");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon add spawner <roomNo> <waveNo> <amount>" + ChatPalette.GOLD + " !!look at spawner location block!!");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon add skill <roomNo>" + ChatPalette.GOLD + " !!look at location block!!");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon add skill global" + ChatPalette.GOLD + " !!look at location block!!");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon add checkpoint" + ChatPalette.GOLD + " !!look at location block!!");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon set bossRoom " + ChatPalette.GOLD + " !!select WorldEdit region first!!");
                player.sendMessage(ChatPalette.GOLD + "/admindungeon set prizeloc" + ChatPalette.GOLD + " !!look at block!!");
            } else if (args[0].equals("reload")) {
                SkillListForGround.clear();
                MiniGameManager.clearDungeonData();

                SkillOnGroundConfigurations.createConfigs();
                SkillOnGroundConfigurations.loadConfigs();
                DungeonConfiguration.createConfigs();
                DungeonConfiguration.loadConfigs();
            } else if (args[0].equals("save")) {
                DungeonConfiguration.writeConfigs();
            } else if (args[0].equals("select")) {
                String key = args[1].toUpperCase();

                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(key, 1);

                if (dungeonInstance != null) {
                    selectedDungeon.put(player, key);
                    player.sendMessage(ChatPalette.GREEN_DARK + "SELECTED DUNGEON: " + key);
                } else {
                    player.sendMessage(ChatPalette.RED + "NO DUNGEON: " + key);
                }
            } else if (args[0].equals("tp")) {
                if (!selectedDungeon.containsKey(player)) {
                    player.sendMessage(ChatPalette.RED + "Select dungeon first");
                }
                String key = selectedDungeon.get(player);

                int instanceNo = Integer.parseInt(args[1]);

                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(key, instanceNo);

                Location startLocation = dungeonInstance.getStartLocation(1);

                player.teleport(startLocation);
            } else if (args[0].equals("holo")) {
                if (!selectedDungeon.containsKey(player)) {
                    player.sendMessage(ChatPalette.RED + "Select dungeon first");
                }
                String key = selectedDungeon.get(player);

                remakeHolograms(key);
            } else if (args[0].equals("door")) {
                if (!selectedDungeon.containsKey(player)) {
                    player.sendMessage(ChatPalette.RED + "Select dungeon first");
                }
                String key = selectedDungeon.get(player);

                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(key, 1);

                Location startLocation = dungeonInstance.getStartLocation(1);
                DungeonTheme theme = dungeonInstance.getTheme();

                Set<Integer> dungeonRoomKeys = theme.getDungeonRoomKeys();
                for (int roomKey : dungeonRoomKeys) {
                    DungeonRoom room = theme.getDungeonRoom(roomKey);

                    List<DungeonRoomDoor> doors = room.getDoors();
                    for (DungeonRoomDoor door : doors) {
                        if (door.isOpen()) {
                            door.close(startLocation);
                        } else {
                            door.open(startLocation);
                        }
                    }
                }

                dungeonInstance.remakeDebugHolograms();
            } else if (args[0].equals("add")) {
                if (!selectedDungeon.containsKey(player)) {
                    player.sendMessage(ChatPalette.RED + "Select dungeon first");
                }
                String key = selectedDungeon.get(player);

                switch (args[1]) {
                    case "room": {
                        int roomNo = Integer.parseInt(args[2]);

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        List<DungeonRoomDoor> doors = new ArrayList<>();
                        HashMap<Integer, List<DungeonRoomSpawner>> waves = new HashMap<>();
                        List<Integer> nextRooms = new ArrayList<>();
                        List<RandomSkillOnGroundWithOffset> skillsOnGround = new ArrayList<>();

                        DungeonRoom dungeonRoom = new DungeonRoom(doors, waves, skillsOnGround, nextRooms);

                        dungeonTheme.addDungeonRoom(roomNo, dungeonRoom);
                        player.sendMessage(ChatPalette.GREEN_DARK + "Added new room");
                        break;
                    }
                    case "door": {
                        int roomNo = Integer.parseInt(args[2]);
                        Material material = Material.valueOf(args[3].toUpperCase());

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        DungeonRoom room = dungeonTheme.getDungeonRoom(roomNo);

                        BukkitPlayer bPlayer = BukkitAdapter.adapt(player);
                        try {
                            Region region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection(bPlayer.getWorld());

                            BlockVector3 min = region.getMinimumPoint();
                            BlockVector3 max = region.getMaximumPoint();

                            BoundingBox boundingBox = new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

                            Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                            Location multiply = start.multiply(-1);

                            BoundingBox shift = boundingBox.shift(multiply);

                            DungeonRoomDoor dungeonRoomDoor = new DungeonRoomDoor(material, shift);
                            room.addDoor(dungeonRoomDoor);
                            player.sendMessage(ChatPalette.GREEN_DARK + "Added new door");

                            remakeHolograms(key);
                        } catch (IncompleteRegionException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "spawner": {
                        int roomNo = Integer.parseInt(args[2]);
                        int waveNo = Integer.parseInt(args[3]);
                        int amount = Integer.parseInt(args[4]);

                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        Block targetBlock = player.getTargetBlock(null, 12);
                        Location add = targetBlock.getLocation().add(0.5, 1, 0.5);

                        Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                        Vector offset = start.toVector().subtract(add.toVector()).multiply(-1);

                        DungeonRoomSpawner spawner = new DungeonRoomSpawner(amount, offset, false);

                        DungeonRoom dungeonRoom = dungeonTheme.getDungeonRoom(roomNo);
                        dungeonRoom.addSpawner(waveNo, spawner);
                        player.sendMessage(ChatPalette.GREEN_DARK + "Added new spawner");
                        remakeHolograms(key);
                        break;
                    }
                    case "skill": {
                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        Block targetBlock = player.getTargetBlock(null, 12);
                        Location add = targetBlock.getLocation().add(0.5, 1, 0.5);

                        Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                        Vector offset = start.toVector().subtract(add.toVector()).multiply(-1);

                        SelfTarget selfTarget = new SelfTarget();
                        SkillOnGround skillOnGround = new SkillOnGround("skillOnGroundDungeon", 400, 1, selfTarget);
                        ArrayList<SkillOnGround> skillList = new ArrayList<>();
                        skillList.add(skillOnGround);
                        RandomSkillOnGroundWithOffset skillOnGroundWithOffset = new RandomSkillOnGroundWithOffset(skillList, offset);

                        if (args[3].equals("global")) {
                            dungeonTheme.addSkillOnGround(skillOnGroundWithOffset);

                            player.sendMessage(ChatPalette.GREEN_DARK + "Added new skillOnGround to GLOBAL");
                        } else {
                            int roomNo = Integer.parseInt(args[2]);

                            DungeonRoom dungeonRoom = dungeonTheme.getDungeonRoom(roomNo);
                            dungeonRoom.addSkillOnGround(skillOnGroundWithOffset);

                            player.sendMessage(ChatPalette.GREEN_DARK + "Added new skillOnGround to ROOM-" + roomNo);
                        }

                        remakeHolograms(key);
                        break;
                    }
                    case "checkpoint": {
                        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                        DungeonTheme dungeonTheme = dungeonThemes.get(key);

                        Block targetBlock = player.getTargetBlock(null, 12);
                        Location add = targetBlock.getLocation().add(0.5, 1.5, 0.5);

                        Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                        Vector offset = start.toVector().subtract(add.toVector()).multiply(-1);

                        dungeonTheme.addCheckpointOffset(offset);

                        Checkpoint checkpoint = new Checkpoint(start.clone().add(offset));

                        for (int i = 0; i < 999; i++) {
                            if (MiniGameManager.dungeonInstanceExists(key, i)) {
                                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(key, i);

                                dungeonInstance.addCheckpoint(checkpoint);
                            } else {
                                break;
                            }
                        }

                        player.sendMessage(ChatPalette.GREEN_DARK + "Added new checkpoint");
                        break;
                    }
                }
            } else if (args[0].equals("set")) {
                if (!selectedDungeon.containsKey(player)) {
                    player.sendMessage(ChatPalette.RED + "Select dungeon first");
                }
                String key = selectedDungeon.get(player);

                if (args[1].equals("prizeloc")) {
                    HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                    DungeonTheme dungeonTheme = dungeonThemes.get(key);

                    Block targetBlock = player.getTargetBlock(null, 12);
                    Location add = targetBlock.getLocation().add(0.5, 1, 0.5);

                    Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                    Vector offset = start.toVector().subtract(add.toVector()).multiply(-1);

                    dungeonTheme.setPrizeChestCenterOffset(offset);

                    player.sendMessage(ChatPalette.GREEN_DARK + "Set chest prize center location");
                } else if (args[1].equals("bossRoom")) {
                    HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
                    DungeonTheme dungeonTheme = dungeonThemes.get(key);

                    BukkitPlayer bPlayer = BukkitAdapter.adapt(player);
                    try {
                        Region region = WorldEdit.getInstance().getSessionManager().get(bPlayer).getSelection(bPlayer.getWorld());

                        BlockVector3 min = region.getMinimumPoint();
                        BlockVector3 max = region.getMaximumPoint();

                        BoundingBox boundingBox = new BoundingBox(min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());

                        Location start = MiniGameManager.getDungeonInstance(key, 1).getStartLocation(1);
                        Location multiply = start.multiply(-1);

                        BoundingBox shift = boundingBox.shift(multiply);

                        dungeonTheme.setBossRoomBox(shift);

                        player.sendMessage(ChatPalette.GREEN_DARK + "Set boss room bounds");

                        remakeHolograms(key);
                    } catch (IncompleteRegionException e) {
                        e.printStackTrace();
                    }
                }
            }

            // If the player (or console) uses our command correct, we can return true
            return true;
        }

        return false;
    }

    private void remakeHolograms(String themeKey) {
        for (int i = 1; i < 999; i++) {
            if (MiniGameManager.dungeonInstanceExists(themeKey, i)) {
                DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(themeKey, i);

                dungeonInstance.remakeDebugHolograms();
            } else {
                break;
            }
        }
    }
}
