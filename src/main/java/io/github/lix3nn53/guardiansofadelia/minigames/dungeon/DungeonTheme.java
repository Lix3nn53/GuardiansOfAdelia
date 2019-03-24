package io.github.lix3nn53.guardiansofadelia.minigames.dungeon;

import io.github.lix3nn53.guardiansofadelia.Items.GearLevel;
import io.github.lix3nn53.guardiansofadelia.Items.PrizeChest;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ItemPoolGenerator;

import java.util.Optional;

public enum DungeonTheme {
    SLIME, ZOMBIE, MAGIC_FOREST, ICE_CREAM, PIRATE, ICE, DESERT, SWAMP, LAVA, DARKNESS;

    public String getCode() {
        if (this.equals(DungeonTheme.DARKNESS)) {
            return "dark";
        } else if (this.equals(DungeonTheme.DESERT)) {
            return "desert";
        } else if (this.equals(DungeonTheme.SWAMP)) {
            return "swamp";
        } else if (this.equals(DungeonTheme.ICE_CREAM)) {
            return "sugar";
        } else if (this.equals(DungeonTheme.ICE)) {
            return "ice";
        } else if (this.equals(DungeonTheme.MAGIC_FOREST)) {
            return "magic";
        } else if (this.equals(DungeonTheme.PIRATE)) {
            return "pirate";
        } else if (this.equals(DungeonTheme.LAVA)) {
            return "lava";
        } else if (this.equals(DungeonTheme.ZOMBIE)) {
            return "zombie";
        }
        return "slime";
    }

    public String getName() {
        if (this.equals(DungeonTheme.DARKNESS)) {
            return "Malephar's Castle";
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
        if (townOptional.isPresent()) {
            return townOptional.get();
        }
        return null;
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
            itemTag = "Malephar's";
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
        double random = Math.random();
        if (random < 0.3) {
            prizeChest.setName(prizeChest.getName() + " (Weapon)");
            prizeChest.addItemList(ItemPoolGenerator.generateWeapons(ItemTier.MYSTIC, itemTag, gearLevel));
            prizeChest.addItemList(ItemPoolGenerator.generateWeapons(ItemTier.LEGENDARY, itemTag, gearLevel));
        } else if (random < 0.6) {
            prizeChest.setName(prizeChest.getName() + " (Jewelry)");
            prizeChest.addItemList(ItemPoolGenerator.generatePassives(ItemTier.MYSTIC, itemTag, gearLevel));
            prizeChest.addItemList(ItemPoolGenerator.generatePassives(ItemTier.LEGENDARY, itemTag, gearLevel));
        } else {
            prizeChest.setName(prizeChest.getName() + " (Armor)");
            prizeChest.addItemList(ItemPoolGenerator.generateArmors(ItemTier.MYSTIC, itemTag, gearLevel));
            prizeChest.addItemList(ItemPoolGenerator.generateArmors(ItemTier.LEGENDARY, itemTag, gearLevel));
        }
    }
}
