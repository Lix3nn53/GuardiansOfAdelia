package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChestType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public enum DungeonTheme {
    SLIME, ZOMBIE, MAGIC_FOREST, ICE_CREAM, PIRATE, ICE, DESERT, SWAMP, LAVA, DARKNESS;

    public String getName() {
        if (this.equals(DungeonTheme.DARKNESS)) {
            return "Aleesia's Castle";
        } else if (this.equals(DungeonTheme.DESERT)) {
            return "Desert's Heart";
        } else if (this.equals(DungeonTheme.SWAMP)) {
            return "Caverns of Woz's Spirit";
        } else if (this.equals(DungeonTheme.ICE_CREAM)) {
            return "Whole Cake Town";
        } else if (this.equals(DungeonTheme.ICE)) {
            return "Frozen Cave";
        } else if (this.equals(DungeonTheme.MAGIC_FOREST)) {
            return "Forest of Mist";
        } else if (this.equals(DungeonTheme.PIRATE)) {
            return "Shipwreck Cove";
        } else if (this.equals(DungeonTheme.LAVA)) {
            return "Dwarven Kingdom Ruins";
        } else if (this.equals(DungeonTheme.ZOMBIE)) {
            return "Lair of the Zombie Subjects";
        }
        return "Sticky Sky Island";
    }

    public Town getDefaultTown() {
        final int townNo = getTownNo();
        Optional<Town> townOptional = TownManager.getTowns().stream()
                .filter(item -> item.getNo() == townNo)
                .findAny();
        return townOptional.orElse(null);
    }

    private int getTownNo() {
        if (this.equals(DungeonTheme.DARKNESS)) {
            return 5;
        } else if (this.equals(DungeonTheme.DESERT)) {
            return 4;
        } else if (this.equals(DungeonTheme.SWAMP)) {
            return 4;
        } else if (this.equals(DungeonTheme.ICE_CREAM)) {
            return 2;
        } else if (this.equals(DungeonTheme.ICE)) {
            return 3;
        } else if (this.equals(DungeonTheme.MAGIC_FOREST)) {
            return 2;
        } else if (this.equals(DungeonTheme.PIRATE)) {
            return 3;
        } else if (this.equals(DungeonTheme.LAVA)) {
            return 5;
        } else if (this.equals(DungeonTheme.ZOMBIE)) {
            return 1;
        }
        return 1;
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
        String itemTag = "Sticky";
        int gearLevel = 0;

        if (this.equals(DungeonTheme.DARKNESS)) {
            itemTag = "Aleesia's";
            gearLevel = 9;
        } else if (this.equals(DungeonTheme.DESERT)) {
            itemTag = "Sand ";
            gearLevel = 6;
        } else if (this.equals(DungeonTheme.SWAMP)) {
            itemTag = "Woz's";
            gearLevel = 7;
        } else if (this.equals(DungeonTheme.ICE_CREAM)) {
            itemTag = "Sugar";
            gearLevel = 3;
        } else if (this.equals(DungeonTheme.ICE)) {
            itemTag = "Frozen";
            gearLevel = 5;
        } else if (this.equals(DungeonTheme.MAGIC_FOREST)) {
            itemTag = "Magical";
            gearLevel = 2;
        } else if (this.equals(DungeonTheme.PIRATE)) {
            itemTag = "Haunted";
            gearLevel = 4;
        } else if (this.equals(DungeonTheme.LAVA)) {
            itemTag = "Burning";
            gearLevel = 8;
        } else if (this.equals(DungeonTheme.ZOMBIE)) {
            itemTag = "Rotten";
            gearLevel = 1;
        }

        ArrayList<ItemStack> chestItems = new ArrayList<>();
        if (type.equals(PrizeChestType.WEAPON)) {
            chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.MYSTIC, itemTag, gearLevel, 0));
            chestItems.addAll(ItemPoolGenerator.generateWeapons(ItemTier.LEGENDARY, itemTag, gearLevel, 0));
        } else if (type.equals(PrizeChestType.ARMOR)) {
            chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.MYSTIC, itemTag, gearLevel, 0));
            chestItems.addAll(ItemPoolGenerator.generateArmors(ItemTier.LEGENDARY, itemTag, gearLevel, 0));
        } else if (type.equals(PrizeChestType.JEWELRY)) {
            chestItems.addAll(ItemPoolGenerator.generatePassives(ItemTier.MYSTIC, itemTag, gearLevel, 0));
            chestItems.addAll(ItemPoolGenerator.generatePassives(ItemTier.LEGENDARY, itemTag, gearLevel, 0));
        } else if (type.equals(PrizeChestType.PET)) {
            chestItems.addAll(ItemPoolGenerator.generateEggs(gearLevel));
        }

        return chestItems;
    }

    public GuiGeneric getJoinQueueGui() {
        GuiGeneric guiGeneric = new GuiGeneric(27, "Join dungeon: " + toString(), 0);

        Dungeon dungeon = MiniGameManager.getDungeon(this, 1);
        ItemStack room = new ItemStack(Material.LIME_WOOL);
        ItemMeta itemMeta = room.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA + getName() + " #1 (" + dungeon.getPlayersInGameSize() + "/" + dungeon.getMaxPlayerSize() + ")");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Level req: " + ChatColor.WHITE + dungeon.getLevelReq());
        lore.add(ChatColor.RED + "Boss: " + ChatColor.WHITE + dungeon.getBossMobName());
        lore.add(ChatColor.LIGHT_PURPLE + "Game time: " + ChatColor.WHITE + dungeon.getTimeLimitInMinutes() + " minute(s)");
        lore.add("");
        lore.add(ChatColor.GOLD + "Players in dungeon");
        for (Player player : dungeon.getPlayersInGame()) {
            lore.add(player.getDisplayName());
        }
        lore.add("");
        lore.add(ChatColor.GRAY + "Click to join this dungeon room!");
        itemMeta.setLore(lore);
        room.setItemMeta(itemMeta);

        if (dungeon.isInGame()) {
            room.setType(Material.RED_WOOL);
        }
        room.setItemMeta(itemMeta);
        guiGeneric.setItem(9, room);

        dungeon = MiniGameManager.getDungeon(this, 2);
        itemMeta.setDisplayName(ChatColor.AQUA + getName() + " #2 (" + dungeon.getPlayersInGameSize() + "/" + dungeon.getMaxPlayerSize() + ")");
        room.setItemMeta(itemMeta);

        room.setType(Material.LIME_WOOL);
        if (dungeon.isInGame()) {
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
        if (this.equals(DungeonTheme.DARKNESS)) {
            return PortalColor.PURPLE;
        } else if (this.equals(DungeonTheme.DESERT)) {
            return PortalColor.ORANGE;
        } else if (this.equals(DungeonTheme.SWAMP)) {
            return PortalColor.ORANGE;
        } else if (this.equals(DungeonTheme.ICE_CREAM)) {
            return PortalColor.PURPLE;
        } else if (this.equals(DungeonTheme.ICE)) {
            return PortalColor.BLUE;
        } else if (this.equals(DungeonTheme.MAGIC_FOREST)) {
            return PortalColor.GREEN;
        } else if (this.equals(DungeonTheme.PIRATE)) {
            return PortalColor.BLUE;
        } else if (this.equals(DungeonTheme.LAVA)) {
            return PortalColor.RED;
        } else if (this.equals(DungeonTheme.ZOMBIE)) {
            return PortalColor.GREEN;
        }
        return PortalColor.GREEN;
    }
}
