package io.github.lix3nn53.guardiansofadelia.Items.list.passiveItems;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearPassive;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.rpginventory.slots.RPGSlotType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class PassiveItemList {

    public static ItemStack get(int placementNumber, RPGSlotType rpgSlotType, ItemTier tier, String itemTag, int minStatValue,
                                int maxStatValue, int minNumberofStats) {
        String name = "Stone Ring";
        Material material = Material.SHEARS;
        int customModelDataId = 10000001;
        int level = 2;
        RPGClass rpgClass = RPGClass.NO_CLASS;
        int itemID = 6001;
        int passiveTypeNum = 1;

        if (rpgSlotType.equals(RPGSlotType.RING)) {
            if (placementNumber == 2) {
                name = "Leaf Ring";
                customModelDataId = 10000002;
                level = 12;
                itemID = 6002;
            } else if (placementNumber == 3) {
                name = "Gold Ring";
                customModelDataId = 10000003;
                level = 22;
                itemID = 6003;
            } else if (placementNumber == 4) {
                name = "Fire Ring";
                customModelDataId = 10000004;
                level = 32;
                itemID = 6004;
            } else if (placementNumber == 5) {
                name = "Ocean Ring";
                customModelDataId = 10000005;
                level = 42;
                itemID = 6005;
            } else if (placementNumber == 6) {
                name = "Lapis Ring";
                customModelDataId = 10000006;
                level = 52;
                itemID = 6006;
            } else if (placementNumber == 7) {
                name = "Lightning Ring";
                customModelDataId = 10000007;
                level = 62;
                itemID = 6007;
            } else if (placementNumber == 8) {
                name = "Amethyst Ring";
                customModelDataId = 10000008;
                level = 72;
                itemID = 6008;
            } else if (placementNumber == 9) {
                name = "Ruby Ring";
                customModelDataId = 10000009;
                level = 82;
                itemID = 6009;
            } else if (placementNumber == 10) {
                name = "Moon Stone Ring";
                customModelDataId = 10000010;
                level = 90;
                itemID = 6010;
            }
        } else if (rpgSlotType.equals(RPGSlotType.GLOVE)) {
            passiveTypeNum = 2;
            if (placementNumber == 1) {
                name = "Short Gloves";
                customModelDataId = 10000011;
                level = 4;
                itemID = 6101;
            } else if (placementNumber == 2) {
                name = "Cloth Gloves";
                customModelDataId = 10000012;
                level = 14;
                itemID = 6102;
            } else if (placementNumber == 3) {
                name = "Leather Gloves";
                customModelDataId = 10000013;
                level = 24;
                itemID = 6103;
            } else if (placementNumber == 4) {
                name = "Chainmail Gloves";
                customModelDataId = 10000014;
                level = 34;
                itemID = 6104;
            } else if (placementNumber == 5) {
                name = "Iron Gloves";
                customModelDataId = 10000015;
                level = 44;
                itemID = 6105;
            } else if (placementNumber == 6) {
                name = "Amethyst Gloves";
                customModelDataId = 10000016;
                level = 54;
                itemID = 6106;
            } else if (placementNumber == 7) {
                name = "Gold Gloves";
                customModelDataId = 10000017;
                level = 64;
                itemID = 6107;
            } else if (placementNumber == 8) {
                name = "Quartz Gloves";
                customModelDataId = 10000018;
                level = 74;
                itemID = 6108;
            } else if (placementNumber == 9) {
                name = "Titanium Gloves";
                customModelDataId = 10000019;
                level = 84;
                itemID = 6109;
            } else if (placementNumber == 10) {
                name = "Moon Stone Gloves";
                customModelDataId = 10000020;
                level = 90;
                itemID = 6110;
            }
        } else if (rpgSlotType.equals(RPGSlotType.NECKLACE)) {
            passiveTypeNum = 3;
            if (placementNumber == 1) {
                name = "Stone Necklace";
                customModelDataId = 10000021;
                level = 6;
                itemID = 6201;
            } else if (placementNumber == 2) {
                name = "Leaf Necklace";
                customModelDataId = 10000022;
                level = 16;
                itemID = 6202;
            } else if (placementNumber == 3) {
                name = "Gold Necklace";
                customModelDataId = 10000023;
                level = 26;
                itemID = 6203;
            } else if (placementNumber == 4) {
                name = "Fire Necklace";
                customModelDataId = 10000024;
                level = 36;
                itemID = 6204;
            } else if (placementNumber == 5) {
                name = "Ocean Necklace";
                customModelDataId = 10000025;
                level = 46;
                itemID = 6205;
            } else if (placementNumber == 6) {
                name = "Lapis Necklace";
                customModelDataId = 10000026;
                level = 56;
                itemID = 6206;
            } else if (placementNumber == 7) {
                name = "Lightning Necklace";
                customModelDataId = 10000027;
                level = 66;
                itemID = 6207;
            } else if (placementNumber == 8) {
                name = "Amethyst Necklace";
                customModelDataId = 10000028;
                level = 76;
                itemID = 6208;
            } else if (placementNumber == 9) {
                name = "Ruby Necklace";
                customModelDataId = 10000029;
                level = 86;
                itemID = 6209;
            } else if (placementNumber == 10) {
                name = "Moon Stone Necklace";
                customModelDataId = 10000030;
                level = 90;
                itemID = 6210;
            }
        } else if (rpgSlotType.equals(RPGSlotType.EARRING)) {
            passiveTypeNum = 4;
            if (placementNumber == 1) {
                name = "Stone Earrings";
                customModelDataId = 10000031;
                level = 8;
                itemID = 6301;
            } else if (placementNumber == 2) {
                name = "Leaf Earrings";
                customModelDataId = 10000032;
                level = 18;
                itemID = 6302;
            } else if (placementNumber == 3) {
                name = "Gold Earrings";
                customModelDataId = 10000033;
                level = 28;
                itemID = 6303;
            } else if (placementNumber == 4) {
                name = "Fire Earrings";
                customModelDataId = 10000034;
                level = 38;
                itemID = 6304;
            } else if (placementNumber == 5) {
                name = "Ocean Earrings";
                customModelDataId = 10000035;
                level = 48;
                itemID = 6305;
            } else if (placementNumber == 6) {
                name = "Lapis Earrings";
                customModelDataId = 10000036;
                level = 58;
                itemID = 6306;
            } else if (placementNumber == 7) {
                name = "Lightning Earrings";
                customModelDataId = 10000037;
                level = 68;
                itemID = 6307;
            } else if (placementNumber == 8) {
                name = "Amethyst Earrings";
                customModelDataId = 10000038;
                level = 78;
                itemID = 6308;
            } else if (placementNumber == 9) {
                name = "Ruby Earrings";
                customModelDataId = 10000039;
                level = 88;
                itemID = 6309;
            } else if (placementNumber == 10) {
                name = "Moon Stone Earrings";
                customModelDataId = 10000040;
                level = 90;
                itemID = 6310;
            }
        } else if (rpgSlotType.equals(RPGSlotType.PARROT)) {
            passiveTypeNum = 5;
            customModelDataId = 10000041;
            if (placementNumber == 1) {
                name = "Gray Parrot";
                level = 20;
                itemID = 6401;
            } else if (placementNumber == 2) {
                name = "Green Parrot";
                level = 30;
                itemID = 6402;
            } else if (placementNumber == 3) {
                name = "Blue Parrot";
                level = 40;
                itemID = 6403;
            } else if (placementNumber == 4) {
                name = "Red Parrot";
                level = 60;
                itemID = 6404;
            } else if (placementNumber == 5) {
                name = "Cyan Parrot";
                level = 80;
                itemID = 6405;
            }
        }

        final GearPassive passive = new GearPassive(name, tier, itemTag, material, customModelDataId, passiveTypeNum, level, rpgClass, minStatValue,
                maxStatValue, minNumberofStats, tier.getBonusMultiplier(), itemID);

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
