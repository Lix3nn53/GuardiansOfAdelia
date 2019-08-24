package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.portals.PortalColor;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;
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
            return "Lair of the Zombie Leader";
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

    public PrizeChest getPrizeChest() {
        PrizeChest prizeChest = new PrizeChest(getName() + " Prize Chest");
        String itemTag = "Sticky";
        GearLevel gearLevel = GearLevel.ZERO;

        if (this.equals(DungeonTheme.DARKNESS)) {
            itemTag = "Aleesia's";
            gearLevel = GearLevel.NINE;
        } else if (this.equals(DungeonTheme.DESERT)) {
            itemTag = "Sand ";
            gearLevel = GearLevel.SIX;
        } else if (this.equals(DungeonTheme.SWAMP)) {
            itemTag = "Woz's";
            gearLevel = GearLevel.SEVEN;
        } else if (this.equals(DungeonTheme.ICE_CREAM)) {
            itemTag = "Sugar";
            gearLevel = GearLevel.THREE;
        } else if (this.equals(DungeonTheme.ICE)) {
            itemTag = "Frozen";
            gearLevel = GearLevel.FIVE;
        } else if (this.equals(DungeonTheme.MAGIC_FOREST)) {
            itemTag = "Magical";
            gearLevel = GearLevel.TWO;
        } else if (this.equals(DungeonTheme.PIRATE)) {
            itemTag = "Haunted";
            gearLevel = GearLevel.FOUR;
        } else if (this.equals(DungeonTheme.LAVA)) {
            itemTag = "Burning";
            gearLevel = GearLevel.EIGHT;
        } else if (this.equals(DungeonTheme.ZOMBIE)) {
            itemTag = "Rotten";
            gearLevel = GearLevel.ONE;
        }

        generateChestItems(prizeChest, itemTag, gearLevel);

        return prizeChest;
    }

    private void generateChestItems(PrizeChest prizeChest, String itemTag, GearLevel gearLevel) {
        int random = new Random().nextInt(3);
        if (random == 0) {
            prizeChest.setName(prizeChest.getName() + " (Weapon)");
            prizeChest.addItemList(ItemPoolGenerator.generateWeapons(ItemTier.MYSTIC, itemTag, gearLevel));
            prizeChest.addItemList(ItemPoolGenerator.generateWeapons(ItemTier.LEGENDARY, itemTag, gearLevel));
        } else if (random == 1) {
            prizeChest.setName(prizeChest.getName() + " (Jewelry)");
            prizeChest.addItemList(ItemPoolGenerator.generatePassives(ItemTier.MYSTIC, itemTag, gearLevel));
            prizeChest.addItemList(ItemPoolGenerator.generatePassives(ItemTier.LEGENDARY, itemTag, gearLevel));
        } else {
            prizeChest.setName(prizeChest.getName() + " (Armor)");
            prizeChest.addItemList(ItemPoolGenerator.generateArmors(ItemTier.MYSTIC, itemTag, gearLevel));
            prizeChest.addItemList(ItemPoolGenerator.generateArmors(ItemTier.LEGENDARY, itemTag, gearLevel));
        }
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
        guiGeneric.setItem(15, room);

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
