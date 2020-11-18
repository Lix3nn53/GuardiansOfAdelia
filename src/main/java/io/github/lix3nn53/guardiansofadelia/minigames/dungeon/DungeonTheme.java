package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MobManager;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonTheme {
    private final String code;
    private final String name;
    private final String gearTag;
    private final int gearLevel;
    private final PortalColor portalColor;

    private final int levelReq;
    private final int timeLimitInMinutes;
    private final String bossInternalName;

    public DungeonTheme(String code, String name, String gearTag, int gearLevel, PortalColor portalColor, int levelReq, int timeLimitInMinutes, String bossInternalName) {
        this.code = code;
        this.name = name;
        this.gearTag = gearTag;
        this.gearLevel = gearLevel;
        this.portalColor = portalColor;
        this.levelReq = levelReq;
        this.timeLimitInMinutes = timeLimitInMinutes;
        this.bossInternalName = bossInternalName;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
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

    public String getBossName() {
        MobManager mobManager = MythicMobs.inst().getMobManager();
        MythicMob mythicMob = mobManager.getMythicMob(bossInternalName);

        return mythicMob.getDisplayName().get();
    }

    public ItemStack getPrizeChest() {
        int random = new Random().nextInt(4);

        PrizeChestType chestType = PrizeChestType.values()[random];

        ItemStack chest = new PrizeChest(this, chestType).getChest();

        PersistentDataContainerUtil.putString("prizeDungeon", this.toString(), chest);
        PersistentDataContainerUtil.putInteger("prizeType", random, chest);

        return chest;
    }

    /**
     * @param type 0 for Weapon, 1 for Jewelry, 2 for Armor
     */
    public List<ItemStack> generateChestItems(PrizeChestType type) {
        ArrayList<ItemStack> chestItems = new ArrayList<>();
        if (type.equals(PrizeChestType.WEAPON)) {
            chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.MYSTIC, gearTag, gearLevel, 0));
            chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.LEGENDARY, gearTag, gearLevel, 0));
        } else if (type.equals(PrizeChestType.ARMOR)) {
            chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.MYSTIC, gearTag, gearLevel, 0));
            chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.LEGENDARY, gearTag, gearLevel, 0));
        } else if (type.equals(PrizeChestType.JEWELRY)) {
            chestItems.addAll(ItemPoolGenerator.generatePassives(ItemTier.MYSTIC, gearTag, gearLevel, 0));
            chestItems.addAll(ItemPoolGenerator.generatePassives(ItemTier.LEGENDARY, gearTag, gearLevel, 0));
        } else if (type.equals(PrizeChestType.PET)) {
            chestItems.addAll(ItemPoolGenerator.generateEggs(gearLevel));
        }

        return chestItems;
    }

    public GuiGeneric getJoinQueueGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, "Join dungeon: " + name + " #" + code, 0);

        DungeonRoom dungeonRoom = MiniGameManager.getDungeonRoom(code, 1);
        ItemStack room = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = room.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + getName() + " #1 (" + dungeonRoom.getPlayersInGameSize() + "/" + dungeonRoom.getMaxPlayerSize() + ")");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Level req: " + ChatColor.WHITE + dungeonRoom.getLevelReq());
        lore.add(ChatColor.RED + "Boss: " + ChatColor.WHITE + getBossName());
        lore.add(ChatColor.LIGHT_PURPLE + "Game time: " + ChatColor.WHITE + dungeonRoom.getTimeLimitInMinutes() + " minute(s)");
        lore.add("");
        lore.add(ChatColor.GOLD + "Players in dungeon");
        for (Player player : dungeonRoom.getPlayersInGame()) {
            lore.add(player.getDisplayName());
        }
        lore.add("");
        lore.add(ChatColor.GRAY + "Click to join this dungeon room!");
        itemMeta.setLore(lore);
        room.setItemMeta(itemMeta);

        if (dungeonRoom.isInGame()) {
            room.setType(Material.RED_WOOL);
        }
        room.setItemMeta(itemMeta);
        guiGeneric.setItem(9, room);

        dungeonRoom = MiniGameManager.getDungeonRoom(code, 2);
        itemMeta.setDisplayName(ChatColor.AQUA + getName() + " #2 (" + dungeonRoom.getPlayersInGameSize() + "/" + dungeonRoom.getMaxPlayerSize() + ")");
        room.setItemMeta(itemMeta);

        room.setType(Material.LIME_WOOL);
        if (dungeonRoom.isInGame()) {
            room.setType(Material.RED_WOOL);
        }
        room.setItemMeta(itemMeta);
        guiGeneric.setItem(11, room);

        /*TODO disabled room 3 and 4 of dungeons for maintainability, stay this way?
        dungeon = MiniGameManager.getDungeon(this, 3);
        itemMeta.setDisplayName(ChatColor.AQUA + getName() + " #3 (" + dungeon.getPlayersInGameSize() + "/" + dungeon.getMaxPlayerSize() + ")");
        room.setItemMeta(itemMeta);

        room.setType(Material.LIME_WOOL);
        if (dungeon.isInGame()) {
            room.setType(Material.RED_WOOL);
        }
        room.setItemMeta(itemMeta);
        guiGeneric.setItem(13, room);

        dungeon = MiniGameManager.getDungeon(this, 4);
        itemMeta.setDisplayName(ChatColor.AQUA + getName() + " #4 (" + dungeon.getPlayersInGameSize() + "/" + dungeon.getMaxPlayerSize() + ")");
        room.setItemMeta(itemMeta);

        room.setType(Material.LIME_WOOL);
        if (dungeon.isInGame()) {
            room.setType(Material.RED_WOOL);
        }
        room.setItemMeta(itemMeta);
        guiGeneric.setItem(15, room);*/

        return guiGeneric;
    }

    public PortalColor getPortalColor() {
        return portalColor;
    }
}
