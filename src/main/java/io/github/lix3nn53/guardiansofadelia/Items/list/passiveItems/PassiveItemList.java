package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.inventory.ItemStack;

public class PassiveItemList {

    public static ItemStack get(int placementNumber, RPGSlotType rpgSlotType, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Stone Ring";
        int customModelDataId = 1;
        int level = 2;
        RPGClass rpgClass = RPGClass.NO_CLASS;
        int passiveTypeNum = 1;

        if (rpgSlotType.equals(RPGSlotType.RING)) {
            if (placementNumber == 2) {
                name = "Leaf Ring";
                customModelDataId = 2;
                level = 12;
            } else if (placementNumber == 3) {
                name = "Gold Ring";
                customModelDataId = 3;
                level = 22;
            } else if (placementNumber == 4) {
                name = "Fire Ring";
                customModelDataId = 4;
                level = 32;
            } else if (placementNumber == 5) {
                name = "Ocean Ring";
                customModelDataId = 5;
                level = 42;
            } else if (placementNumber == 6) {
                name = "Lapis Ring";
                customModelDataId = 6;
                level = 52;
            } else if (placementNumber == 7) {
                name = "Lightning Ring";
                customModelDataId = 7;
                level = 62;
            } else if (placementNumber == 8) {
                name = "Amethyst Ring";
                customModelDataId = 8;
                level = 72;
            } else if (placementNumber == 9) {
                name = "Ruby Ring";
                customModelDataId = 9;
                level = 82;
            } else if (placementNumber == 10) {
                name = "Moon Stone Ring";
                customModelDataId = 10;
                level = 90;
            }
        } else if (rpgSlotType.equals(RPGSlotType.GLOVE)) {
            passiveTypeNum = 2;
            if (placementNumber == 1) {
                name = "Short Gloves";
                customModelDataId = 11;
                level = 4;
            } else if (placementNumber == 2) {
                name = "Cloth Gloves";
                customModelDataId = 12;
                level = 14;
            } else if (placementNumber == 3) {
                name = "Leather Gloves";
                customModelDataId = 13;
                level = 24;
            } else if (placementNumber == 4) {
                name = "Chainmail Gloves";
                customModelDataId = 14;
                level = 34;
            } else if (placementNumber == 5) {
                name = "Iron Gloves";
                customModelDataId = 15;
                level = 44;
            } else if (placementNumber == 6) {
                name = "Amethyst Gloves";
                customModelDataId = 16;
                level = 54;
            } else if (placementNumber == 7) {
                name = "Gold Gloves";
                customModelDataId = 17;
                level = 64;
            } else if (placementNumber == 8) {
                name = "Quartz Gloves";
                customModelDataId = 18;
                level = 74;
            } else if (placementNumber == 9) {
                name = "Titanium Gloves";
                customModelDataId = 19;
                level = 84;
            } else if (placementNumber == 10) {
                name = "Moon Stone Gloves";
                customModelDataId = 20;
                level = 90;
            }
        } else if (rpgSlotType.equals(RPGSlotType.NECKLACE)) {
            passiveTypeNum = 3;
            if (placementNumber == 1) {
                name = "Stone Necklace";
                customModelDataId = 21;
                level = 6;
            } else if (placementNumber == 2) {
                name = "Leaf Necklace";
                customModelDataId = 22;
                level = 16;
            } else if (placementNumber == 3) {
                name = "Gold Necklace";
                customModelDataId = 23;
                level = 26;
            } else if (placementNumber == 4) {
                name = "Fire Necklace";
                customModelDataId = 24;
                level = 36;
            } else if (placementNumber == 5) {
                name = "Ocean Necklace";
                customModelDataId = 25;
                level = 46;
            } else if (placementNumber == 6) {
                name = "Lapis Necklace";
                customModelDataId = 26;
                level = 56;
            } else if (placementNumber == 7) {
                name = "Lightning Necklace";
                customModelDataId = 27;
                level = 66;
            } else if (placementNumber == 8) {
                name = "Amethyst Necklace";
                customModelDataId = 28;
                level = 76;
            } else if (placementNumber == 9) {
                name = "Ruby Necklace";
                customModelDataId = 29;
                level = 86;
            } else if (placementNumber == 10) {
                name = "Moon Stone Necklace";
                customModelDataId = 30;
                level = 90;
            }
        } else if (rpgSlotType.equals(RPGSlotType.EARRING)) {
            passiveTypeNum = 4;
            if (placementNumber == 1) {
                name = "Stone Earrings";
                customModelDataId = 31;
                level = 8;
            } else if (placementNumber == 2) {
                name = "Leaf Earrings";
                customModelDataId = 32;
                level = 18;
            } else if (placementNumber == 3) {
                name = "Gold Earrings";
                customModelDataId = 33;
                level = 28;
            } else if (placementNumber == 4) {
                name = "Fire Earrings";
                customModelDataId = 34;
                level = 38;
            } else if (placementNumber == 5) {
                name = "Ocean Earrings";
                customModelDataId = 35;
                level = 48;
            } else if (placementNumber == 6) {
                name = "Lapis Earrings";
                customModelDataId = 36;
                level = 58;
            } else if (placementNumber == 7) {
                name = "Lightning Earrings";
                customModelDataId = 37;
                level = 68;
            } else if (placementNumber == 8) {
                name = "Amethyst Earrings";
                customModelDataId = 38;
                level = 78;
            } else if (placementNumber == 9) {
                name = "Ruby Earrings";
                customModelDataId = 39;
                level = 88;
            } else if (placementNumber == 10) {
                name = "Moon Stone Earrings";
                customModelDataId = 40;
                level = 90;
            }
        } else if (rpgSlotType.equals(RPGSlotType.PARROT)) {
            passiveTypeNum = 5;
            customModelDataId = 41;
            if (placementNumber == 1) {
                name = "Gray Parrot";
                level = 20;
            } else if (placementNumber == 2) {
                name = "Green Parrot";
                level = 30;
            } else if (placementNumber == 3) {
                name = "Blue Parrot";
                level = 40;
            } else if (placementNumber == 4) {
                name = "Red Parrot";
                level = 60;
            } else if (placementNumber == 5) {
                name = "Cyan Parrot";
                level = 80;
            }
        }

        final GearPassive passive = new GearPassive(name, tier, itemTag, customModelDataId, passiveTypeNum, level, rpgClass, minStatValue,
                maxStatValue, minNumberofStats, tier.getBonusMultiplier());

        return passive.getItemStack();
    }

    public static RPGSlotType getSlotTypeFromNo(int passiveTypeNum) {
        if (passiveTypeNum == 2) {
            return RPGSlotType.GLOVE;
        } else if (passiveTypeNum == 3) {
            return RPGSlotType.NECKLACE;
        } else if (passiveTypeNum == 4) {
            return RPGSlotType.EARRING;
        } else if (passiveTypeNum == 5) {
            return RPGSlotType.PARROT;
        }
        return RPGSlotType.RING;
    }
}
