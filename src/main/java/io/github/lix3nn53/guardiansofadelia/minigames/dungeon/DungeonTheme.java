package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.*;

public class DungeonTheme {
    private final String code;
    private final String name;
    private final String gearTag;
    private final GearLevel gearLevel;
    private final PortalColor portalColor;

    private final int levelReq;
    private final int timeLimitInMinutes;
    private final String bossInternalName;

    // Rooms
    private final HashMap<Integer, DungeonRoom> dungeonRooms;
    private final List<Integer> startingRooms;

    private final List<Vector> checkpointOffsets;

    public DungeonTheme(String code, String name, String gearTag, GearLevel gearLevel, PortalColor portalColor, int levelReq,
                        int timeLimitInMinutes, String bossInternalName, HashMap<Integer, DungeonRoom> dungeonRooms, List<Integer> startingRooms, List<Vector> checkpoints) {
        this.code = code;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.gearTag = gearTag;
        this.gearLevel = gearLevel;
        this.portalColor = portalColor;
        this.levelReq = levelReq;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.bossInternalName = bossInternalName;
        this.dungeonRooms = dungeonRooms;
        this.startingRooms = startingRooms;
        this.checkpointOffsets = checkpoints;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getGearTag() {
        return gearTag;
    }

    public GearLevel getGearLevel() {
        return gearLevel;
    }

    public int getLevelReq() {
        return levelReq;
    }

    public int getTimeLimitInMinutes() {
        return timeLimitInMinutes;
    }

    public String getBossInternalName() {
        return bossInternalName;
    }

    public List<Vector> getCheckpointOffsets() {
        return checkpointOffsets;
    }

    public void addCheckpointOffset(Vector offset) {
        checkpointOffsets.add(offset);
    }

    public String getBossName() {
        MobManager mobManager = MythicMobs.inst().getMobManager();
        MythicMob mythicMob = mobManager.getMythicMob(bossInternalName);

        return mythicMob.getDisplayName().get();
    }

    public PrizeChest getPrizeChest() {
        PrizeChestType[] values = PrizeChestType.values();
        int random = new Random().nextInt(values.length);

        PrizeChestType chestType = values[random];

        return new PrizeChest(this, chestType);
    }

    /**
     * @param type 0 for Weapon, 1 for Jewelry, 2 for Armor
     */
    public List<ItemStack> generateChestItems(PrizeChestType type) {
        ArrayList<ItemStack> chestItems = new ArrayList<>();
        switch (type) {
            case WEAPON_MELEE:
                chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.MYSTIC, gearLevel, gearTag, true, false));
                chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.LEGENDARY, gearLevel, gearTag, true, false));
                break;
            case WEAPON_RANGED:
                chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.MYSTIC, gearLevel, gearTag, false, false));
                chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.LEGENDARY, gearLevel, gearTag, false, false));
                break;
            case ARMOR_HEAVY:
                chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.MYSTIC, gearLevel, gearTag, true, false));
                chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.LEGENDARY, gearLevel, gearTag, true, false));
                break;
            case ARMOR_LIGHT:
                chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.MYSTIC, gearLevel, gearTag, false, false));
                chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.LEGENDARY, gearLevel, gearTag, false, false));
                break;
            case JEWELRY:
                chestItems.addAll(ItemPoolGenerator.generatePassives(ItemTier.MYSTIC, gearLevel, gearTag, false));
                chestItems.addAll(ItemPoolGenerator.generatePassives(ItemTier.LEGENDARY, gearLevel, gearTag, false));
                break;
            case PET:
                chestItems.addAll(ItemPoolGenerator.generateEggs(gearLevel));
                break;
        }

        return chestItems;
    }

    public GuiGeneric getJoinQueueGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, "Join dungeon: " + name + " #" + code, 0);

        int slotNo = 9;
        for (int i = 1; i < 100; i++) {
            DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(code, i);
            if (dungeonInstance == null) {
                break;
            }
            ItemStack itemStack = generateInstanceItem(dungeonInstance);
            guiGeneric.setItem(slotNo, itemStack);

            slotNo = slotNo + 2;
        }

        return guiGeneric;
    }


    private ItemStack generateInstanceItem(DungeonInstance dungeonInstance) {

        ItemStack room = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = room.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + getName() + " #" + dungeonInstance.getInstanceNo() + " (" + dungeonInstance.getPlayersInGameSize() + "/" + dungeonInstance.getMaxPlayerSize() + ")");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Level req: " + ChatColor.WHITE + dungeonInstance.getLevelReq());
        lore.add(ChatColor.RED + "Boss: " + ChatColor.WHITE + getBossName());
        lore.add(ChatColor.LIGHT_PURPLE + "Game time: " + ChatColor.WHITE + dungeonInstance.getTimeLimitInMinutes() + " minute(s)");
        lore.add("");
        lore.add(ChatColor.GOLD + "Players in dungeon");
        for (Player player : dungeonInstance.getPlayersInGame()) {
            lore.add(player.getDisplayName());
        }
        lore.add("");
        lore.add(ChatColor.GRAY + "Click to join this dungeon room!");
        itemMeta.setLore(lore);
        room.setItemMeta(itemMeta);

        if (dungeonInstance.isInGame()) {
            room.setType(Material.RED_WOOL);
        }
        room.setItemMeta(itemMeta);

        return room;
    }

    public PortalColor getPortalColor() {
        return portalColor;
    }

    public Set<Integer> getDungeonRoomKeys() {
        return dungeonRooms.keySet();
    }

    public DungeonRoom getDungeonRoom(int key) {
        return dungeonRooms.get(key);
    }

    public void addDungeonRoom(int key, DungeonRoom room) {
        dungeonRooms.put(key, room);
    }

    public List<Integer> getStartingRooms() {
        return startingRooms;
    }

    public boolean addStartingRooms(int roomNo) {
        if (!dungeonRooms.containsKey(roomNo)) return false;
        startingRooms.add(roomNo);

        return true;
    }
}
