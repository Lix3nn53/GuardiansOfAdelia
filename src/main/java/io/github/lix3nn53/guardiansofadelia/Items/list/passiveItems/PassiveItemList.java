package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PassiveItemList {

    public static ItemStack get(int placementNumber, RPGSlotType rpgSlotType, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberofStats, double bonusPercent) {
        String name = "Stone Ring";
        Material material = Material.SHEARS;
        int durability = 1;
        int level = 2;
        RPGClass rpgClass = RPGClass.NO_CLASS;
        int itemID = 6001;
        int passiveTypeNum = 1;

        if (rpgSlotType.equals(RPGSlotType.RING)) {
            if (placementNumber == 2) {
                name = "Leaf Ring";
                durability = 2;
                level = 12;
                itemID = 6002;
            } else if (placementNumber == 3) {
                name = "Gold Ring";
                durability = 3;
                level = 22;
                itemID = 6003;
            } else if (placementNumber == 4) {
                name = "Fire Ring";
                durability = 4;
                level = 32;
                itemID = 6004;
            } else if (placementNumber == 5) {
                name = "Ocean Ring";
                durability = 5;
                level = 42;
                itemID = 6005;
            } else if (placementNumber == 6) {
                name = "Lapis Ring";
                durability = 6;
                level = 52;
                itemID = 6006;
            } else if (placementNumber == 7) {
                name = "Lightning Ring";
                durability = 7;
                level = 62;
                itemID = 6007;
            } else if (placementNumber == 8) {
                name = "Amethyst Ring";
                durability = 8;
                level = 72;
                itemID = 6008;
            } else if (placementNumber == 9) {
                name = "Ruby Ring";
                durability = 9;
                level = 82;
                itemID = 6009;
            } else if (placementNumber == 10) {
                name = "Moon Stone Ring";
                durability = 10;
                level = 90;
                itemID = 6010;
            }
        } else if (rpgSlotType.equals(RPGSlotType.GLOVE)) {
            passiveTypeNum = 2;
            if (placementNumber == 1) {
                name = "Short Gloves";
                durability = 11;
                level = 4;
                itemID = 6101;
            } else if (placementNumber == 2) {
                name = "Cloth Gloves";
                durability = 12;
                level = 14;
                itemID = 6102;
            } else if (placementNumber == 3) {
                name = "Leather Gloves";
                durability = 13;
                level = 24;
                itemID = 6103;
            } else if (placementNumber == 4) {
                name = "Chainmail Gloves";
                durability = 14;
                level = 34;
                itemID = 6104;
            } else if (placementNumber == 5) {
                name = "Iron Gloves";
                durability = 15;
                level = 44;
                itemID = 6105;
            } else if (placementNumber == 6) {
                name = "Amethyst Gloves";
                durability = 16;
                level = 54;
                itemID = 6106;
            } else if (placementNumber == 7) {
                name = "Gold Gloves";
                durability = 17;
                level = 64;
                itemID = 6107;
            } else if (placementNumber == 8) {
                name = "Quartz Gloves";
                durability = 18;
                level = 74;
                itemID = 6108;
            } else if (placementNumber == 9) {
                name = "Titanium Gloves";
                durability = 19;
                level = 84;
                itemID = 6109;
            } else if (placementNumber == 10) {
                name = "Moon Stone Gloves";
                durability = 20;
                level = 90;
                itemID = 6110;
            }
        } else if (rpgSlotType.equals(RPGSlotType.NECKLACE)) {
            passiveTypeNum = 3;
            if (placementNumber == 1) {
                name = "Stone Necklace";
                durability = 21;
                level = 6;
                itemID = 6201;
            } else if (placementNumber == 2) {
                name = "Leaf Necklace";
                durability = 22;
                level = 16;
                itemID = 6202;
            } else if (placementNumber == 3) {
                name = "Gold Necklace";
                durability = 23;
                level = 26;
                itemID = 6203;
            } else if (placementNumber == 4) {
                name = "Fire Necklace";
                durability = 24;
                level = 36;
                itemID = 6204;
            } else if (placementNumber == 5) {
                name = "Ocean Necklace";
                durability = 25;
                level = 46;
                itemID = 6205;
            } else if (placementNumber == 6) {
                name = "Lapis Necklace";
                durability = 26;
                level = 56;
                itemID = 6206;
            } else if (placementNumber == 7) {
                name = "Lightning Necklace";
                durability = 27;
                level = 66;
                itemID = 6207;
            } else if (placementNumber == 8) {
                name = "Amethyst Necklace";
                durability = 28;
                level = 76;
                itemID = 6208;
            } else if (placementNumber == 9) {
                name = "Ruby Necklace";
                durability = 29;
                level = 86;
                itemID = 6209;
            } else if (placementNumber == 10) {
                name = "Moon Stone Necklace";
                durability = 30;
                level = 90;
                itemID = 6210;
            }
        } else if (rpgSlotType.equals(RPGSlotType.EARRING)) {
            passiveTypeNum = 4;
            if (placementNumber == 1) {
                name = "Stone Earrings";
                durability = 31;
                level = 8;
                itemID = 6301;
            } else if (placementNumber == 2) {
                name = "Leaf Earrings";
                durability = 32;
                level = 18;
                itemID = 6302;
            } else if (placementNumber == 3) {
                name = "Gold Earrings";
                durability = 33;
                level = 28;
                itemID = 6303;
            } else if (placementNumber == 4) {
                name = "Fire Earrings";
                durability = 34;
                level = 38;
                itemID = 6304;
            } else if (placementNumber == 5) {
                name = "Ocean Earrings";
                durability = 35;
                level = 48;
                itemID = 6305;
            } else if (placementNumber == 6) {
                name = "Lapis Earrings";
                durability = 36;
                level = 58;
                itemID = 6306;
            } else if (placementNumber == 7) {
                name = "Lightning Earrings";
                durability = 37;
                level = 68;
                itemID = 6307;
            } else if (placementNumber == 8) {
                name = "Amethyst Earrings";
                durability = 38;
                level = 78;
                itemID = 6308;
            } else if (placementNumber == 9) {
                name = "Ruby Earrings";
                durability = 39;
                level = 88;
                itemID = 6309;
            } else if (placementNumber == 10) {
                name = "Moon Stone Earrings";
                durability = 40;
                level = 90;
                itemID = 6310;
            }
        } else if (rpgSlotType.equals(RPGSlotType.PARROT)) {
            passiveTypeNum = 5;
            material = Material.STONE_HOE;
            durability = 7;
            if (placementNumber == 1) {
                name = "Gray Parrot";
                level = 20;
                itemID = 6401;
            } else if (placementNumber == 2) {
                name = "Green Parrot";
                level = 30;
                itemID = 6402;
            } else if (placementNumber == 3) {
                name = "Blue  Parrot";
                level = 40;
                itemID = 6403;
            } else if (placementNumber == 4) {
                name = "Red Parrot";
                level = 60;
                itemID = 6404;
            } else if (placementNumber == 5) {
                name = "LightBlue Parrot";
                level = 80;
                itemID = 6405;
            }
        }

        final GearPassive passive = new GearPassive(name, tier, itemTag, material, durability, passiveTypeNum, level, rpgClass, minStatValue,
                maxStatValue, minNumberofStats, bonusPercent, itemID);

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
