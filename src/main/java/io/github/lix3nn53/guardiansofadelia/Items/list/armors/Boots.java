package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Boots {

    public static ItemStack get(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, double chanceToGetEachStat, int minNumberOfStats) {
        String name = "Leather Boots";
        Material material = Material.LEATHER_BOOTS;
        int level = 2;
        int health = 18;
        int defense = 10;
        int magicDefense = 7;
        int itemID = 1000;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Boots";
                level = 12;
                health = 36;
                defense = 14;
                magicDefense = 10;
                itemID = 1001;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Boots";
                level = 22;
                health = 46;
                defense = 14;
                magicDefense = 24;
                itemID = 1002;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Boots";
                level = 32;
                health = 68;
                defense = 20;
                magicDefense = 32;
                itemID = 1003;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Boots";
                level = 42;
                health = 92;
                defense = 24;
                magicDefense = 42;
                itemID = 1004;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 4) {
                name = "Speedy Boots";
                level = 52;
                health = 120;
                defense = 32;
                magicDefense = 53;
                itemID = 1005;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 5) {
                name = "White Wolf Boots";
                level = 62;
                health = 150;
                defense = 40;
                magicDefense = 68;
                itemID = 1006;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 6) {
                name = "Moottalis Boots";
                level = 72;
                health = 190;
                defense = 48;
                magicDefense = 82;
                itemID = 1007;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Boots";
                level = 82;
                health = 270;
                defense = 81;
                magicDefense = 142;
                itemID = 1008;
                material = Material.CHAINMAIL_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Boots";
                level = 22;
                health = 78;
                defense = 34;
                magicDefense = 12;
                itemID = 1102;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Boots";
                level = 32;
                health = 112;
                defense = 50;
                magicDefense = 18;
                itemID = 1103;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Boots";
                level = 42;
                health = 158;
                defense = 64;
                magicDefense = 21;
                itemID = 1104;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Boots";
                level = 52;
                health = 202;
                defense = 76;
                magicDefense = 26;
                itemID = 1105;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 5) {
                name = "Elisa Boots";
                level = 62;
                health = 246;
                defense = 100;
                magicDefense = 33;
                itemID = 1106;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 6) {
                name = "Gloria Boots";
                level = 72;
                health = 315;
                defense = 124;
                magicDefense = 42;
                itemID = 1107;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Boots";
                level = 82;
                health = 450;
                defense = 206;
                magicDefense = 70;
                itemID = 1108;
                material = Material.DIAMOND_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Boots";
                level = 22;
                health = 40;
                defense = 14;
                magicDefense = 24;
                itemID = 1202;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Boots";
                level = 32;
                health = 58;
                defense = 20;
                magicDefense = 32;
                itemID = 1203;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Boots";
                level = 42;
                health = 82;
                defense = 24;
                magicDefense = 42;
                itemID = 1204;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 4) {
                name = "Wizardy Boots";
                level = 52;
                health = 104;
                defense = 32;
                magicDefense = 53;
                itemID = 1205;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 5) {
                name = "Xhar Boots";
                level = 62;
                health = 124;
                defense = 40;
                magicDefense = 68;
                itemID = 1206;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 6) {
                name = "Chamor Boots";
                level = 72;
                health = 158;
                defense = 48;
                magicDefense = 82;
                itemID = 1207;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 7) {
                name = "Neferti Boots";
                level = 82;
                health = 226;
                defense = 81;
                magicDefense = 142;
                itemID = 1208;
                material = Material.GOLDEN_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            if (placementNumber == 1) {
                name = "Sunset Boots";
                level = 22;
                health = 46;
                defense = 20;
                magicDefense = 20;
                itemID = 1302;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Boots";
                level = 32;
                health = 68;
                defense = 28;
                magicDefense = 28;
                itemID = 1303;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Boots";
                level = 42;
                health = 92;
                defense = 36;
                magicDefense = 36;
                itemID = 1304;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 4) {
                name = "Lupy Boots";
                level = 52;
                health = 120;
                defense = 44;
                magicDefense = 44;
                itemID = 1305;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 5) {
                name = "White Fox Boots";
                level = 62;
                health = 150;
                defense = 54;
                magicDefense = 54;
                itemID = 1306;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 6) {
                name = "Luna Boots";
                level = 72;
                health = 190;
                defense = 70;
                magicDefense = 70;
                itemID = 1307;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 7) {
                name = "Shadow Boots";
                level = 82;
                health = 270;
                defense = 118;
                magicDefense = 118;
                itemID = 1308;
                material = Material.CHAINMAIL_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Boots";
                level = 22;
                health = 55;
                defense = 26;
                magicDefense = 26;
                itemID = 1402;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Boots";
                level = 32;
                health = 80;
                defense = 36;
                magicDefense = 36;
                itemID = 1403;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Boots";
                level = 42;
                health = 111;
                defense = 47;
                magicDefense = 47;
                itemID = 1404;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 4) {
                name = "Eleaza Boots";
                level = 52;
                health = 142;
                defense = 58;
                magicDefense = 58;
                itemID = 1405;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 5) {
                name = "Gaia Boots";
                level = 62;
                health = 174;
                defense = 71;
                magicDefense = 71;
                itemID = 1406;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 6) {
                name = "Holyshion Boots";
                level = 72;
                health = 220;
                defense = 91;
                magicDefense = 91;
                itemID = 1407;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 7) {
                name = "Khraje Boots";
                level = 82;
                health = 315;
                defense = 153;
                magicDefense = 153;
                itemID = 1408;
                material = Material.DIAMOND_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Boots";
                level = 22;
                health = 62;
                defense = 24;
                magicDefense = 5;
                itemID = 1502;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Boots";
                level = 32;
                health = 90;
                defense = 32;
                magicDefense = 8;
                itemID = 1503;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Boots";
                level = 42;
                health = 126;
                defense = 42;
                magicDefense = 10;
                itemID = 1504;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 4) {
                name = "Fury Plate Boots";
                level = 52;
                health = 162;
                defense = 52;
                magicDefense = 14;
                itemID = 1505;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 5) {
                name = "Ceres Boots";
                level = 62;
                health = 198;
                defense = 68;
                magicDefense = 17;
                itemID = 1506;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 6) {
                name = "Zakar Boots";
                level = 72;
                health = 253;
                defense = 82;
                magicDefense = 21;
                itemID = 1507;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 7) {
                name = "Raxes Boots";
                level = 82;
                health = 360;
                defense = 142;
                magicDefense = 30;
                itemID = 1508;
                material = Material.IRON_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Boots";
                level = 22;
                health = 56;
                defense = 21;
                magicDefense = 12;
                itemID = 1602;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Boots";
                level = 32;
                health = 78;
                defense = 30;
                magicDefense = 18;
                itemID = 1603;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Boots";
                level = 42;
                health = 110;
                defense = 38;
                magicDefense = 22;
                itemID = 1604;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 4) {
                name = "Aqua Boots";
                level = 52;
                health = 142;
                defense = 48;
                magicDefense = 28;
                itemID = 1605;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 5) {
                name = "Water Spirit Boots";
                level = 62;
                health = 174;
                defense = 61;
                magicDefense = 36;
                itemID = 1606;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 6) {
                name = "Mera Boots";
                level = 72;
                health = 220;
                defense = 75;
                magicDefense = 44;
                itemID = 1607;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 7) {
                name = "Atlantean Boots";
                level = 82;
                health = 315;
                defense = 128;
                magicDefense = 74;
                itemID = 1608;
                material = Material.IRON_BOOTS;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor boots = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, chanceToGetEachStat, minNumberOfStats, itemID);
        if (isColorful) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) boots.getItemStack().getItemMeta();
            if (rpgClass.equals(RPGClass.ARCHER)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 179, 0));
            } else if (rpgClass.equals(RPGClass.KNIGHT)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 184, 230));
            } else if (rpgClass.equals(RPGClass.MAGE)) {
                leatherArmorMeta.setColor(Color.fromRGB(153, 0, 115));
            } else if (rpgClass.equals(RPGClass.NINJA)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 0, 26));
            } else if (rpgClass.equals(RPGClass.PALADIN)) {
                leatherArmorMeta.setColor(Color.fromRGB(230, 230, 0));
            } else if (rpgClass.equals(RPGClass.WARRIOR)) {
                leatherArmorMeta.setColor(Color.fromRGB(180, 0, 0));
            } else if (rpgClass.equals(RPGClass.MONK)) {
                leatherArmorMeta.setColor(Color.fromRGB(230, 140, 0));
            }
            boots.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return boots.getItemStack();
    }
}
