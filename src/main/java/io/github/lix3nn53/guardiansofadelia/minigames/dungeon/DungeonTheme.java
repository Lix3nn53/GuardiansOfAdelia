package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.creatures.mythicmobs.MMManager;
import io.github.lix3nn53.guardiansofadelia.guardian.element.ElementType;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.onground.RandomSkillOnGroundWithOffset;
import io.github.lix3nn53.guardiansofadelia.items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.room.DungeonRoom;
import io.github.lix3nn53.guardiansofadelia.transportation.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import net.md_5.bungee.api.ChatColor;
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

    private final List<String> monsterPool;
    private final String bossInternalName;

    // Global skillOnGrounds
    private final List<RandomSkillOnGroundWithOffset> skillsOnGround;

    // Rooms
    private final HashMap<Integer, DungeonRoom> dungeonRooms;
    private final List<Integer> startingRooms;

    private final List<Vector> checkpointOffsets;
    private Vector prizeChestCenterOffset;

    public DungeonTheme(String code, String name, String gearTag, GearLevel gearLevel, PortalColor portalColor, int levelReq,
                        int timeLimitInMinutes, List<String> monsterPool, String bossInternalName, HashMap<Integer, DungeonRoom> dungeonRooms,
                        List<Integer> startingRooms, List<Vector> checkpoints, Vector prizeChestCenterOffset, List<RandomSkillOnGroundWithOffset> skillsOnGround) {
        this.code = code;
        this.name = ChatColor.translateAlternateColorCodes('&', name);
        this.gearTag = gearTag;
        this.gearLevel = gearLevel;
        this.portalColor = portalColor;
        this.levelReq = levelReq;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.monsterPool = monsterPool;
        this.bossInternalName = bossInternalName;
        this.dungeonRooms = dungeonRooms;
        this.startingRooms = Collections.unmodifiableList(startingRooms);
        this.checkpointOffsets = checkpoints;
        this.prizeChestCenterOffset = prizeChestCenterOffset;
        this.skillsOnGround = skillsOnGround;
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

    public List<String> getMonsterPool() {
        return monsterPool;
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
        itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + getName() + " #" + dungeonInstance.getInstanceNo() + " (" + dungeonInstance.getPlayersInGameSize() + "/" + dungeonInstance.getMaxPlayerSize() + ")");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatPalette.YELLOW + "Level req: " + ChatPalette.WHITE + dungeonInstance.getLevelReq());
        lore.add(ChatPalette.PURPLE_LIGHT + "Time limit: " + ChatPalette.WHITE + dungeonInstance.getTimeLimitInMinutes() + " minute(s)");
        lore.add("");
        lore.add(ChatPalette.RED + "BOSS: " + ChatPalette.WHITE + getBossName());

        // Resistance
        List<String> weakness = new ArrayList<>();
        for (ElementType type : ElementType.values()) {
            if (MMManager.hasElementResistance(bossInternalName, type)) {
                double resistance = MMManager.getElementResistance(bossInternalName, type);

                if (resistance < 1) {
                    int percent = (int) (100 * (-(1d - resistance)));

                    lore.add(ChatPalette.BLUE_LIGHT + "Resistance: " + type.getFullName() + " - " + -percent + "%");
                } else {
                    int percent = (int) (100 * (1d - resistance));

                    weakness.add(ChatPalette.RED + "Weakness: " + type.getFullName() + " - " + -percent + "%");
                }
            }
        }
        lore.addAll(weakness);

        if (dungeonInstance.isInGame()) {
            lore.add("");
            lore.add(ChatPalette.GOLD + "Players in dungeon: ");
            for (Player player : dungeonInstance.getPlayersInGame()) {
                lore.add(player.getDisplayName());
            }
        } else {
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to join this dungeon room!");
        }
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

    public Vector getPrizeChestCenterOffset() {
        return prizeChestCenterOffset;
    }

    public void setPrizeChestCenterOffset(Vector prizeChestCenterOffset) {
        this.prizeChestCenterOffset = prizeChestCenterOffset;
    }

    public List<RandomSkillOnGroundWithOffset> getSkillsOnGround() {
        return skillsOnGround;
    }

    public void addSkillOnGround(RandomSkillOnGroundWithOffset skill) {
        skillsOnGround.add(skill);
    }

    public String getRandomMonsterToSpawn(int darkness) {
        if (darkness < 0) {
            darkness = 1;
        } else if (darkness > 100) {
            darkness = 99;
        }

        int size = monsterPool.size();

        int bound = (int) ((darkness / 100d) * size);

        /*int i = 0;
        if (bound > 0) {
            i = new Random().nextInt(bound);
        }*/

        return monsterPool.get(bound);
    }

    public int getMonsterLevel(int darkness) {
        if (darkness < 0) {
            darkness = 1;
        } else if (darkness > 100) {
            darkness = 99;
        }

        int maxLevel = 10;

        return (int) ((darkness / 100d) * maxLevel) + 1;
    }
}
