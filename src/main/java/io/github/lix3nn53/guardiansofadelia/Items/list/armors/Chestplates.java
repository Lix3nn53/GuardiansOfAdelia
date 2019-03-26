package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

class Chestplates {

    public static ItemStack get(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        String name = "Leather Chestplate";
        Material material = Material.LEATHER_CHESTPLATE;
        int level = 8;
        int health = 4;
        int defense = 28;
        int magicDefense = 24;
        int itemID = 2100;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Chestplate";
                level = 18;
                health = 96;
                defense = 42;
                magicDefense = 33;
                itemID = 2101;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Chestplate";
                level = 28;
                health = 46;
                defense = 14;
                magicDefense = 24;
                itemID = 2002;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Chestplate";
                level = 38;
                health = 68;
                defense = 20;
                magicDefense = 32;
                itemID = 2003;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Chestplate";
                level = 48;
                health = 92;
                defense = 24;
                magicDefense = 42;
                itemID = 2004;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Speedy Chestplate";
                level = 58;
                health = 120;
                defense = 32;
                magicDefense = 53;
                itemID = 2005;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Wolf Chestplate";
                level = 68;
                health = 150;
                defense = 40;
                magicDefense = 68;
                itemID = 2006;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Moottalis Chestplate";
                level = 78;
                health = 190;
                defense = 48;
                magicDefense = 82;
                itemID = 2007;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 270;
                defense = 81;
                magicDefense = 142;
                itemID = 2008;
                material = Material.CHAINMAIL_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Chestplate";
                level = 28;
                health = 78;
                defense = 34;
                magicDefense = 12;
                itemID = 2102;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Chestplate";
                level = 38;
                health = 112;
                defense = 50;
                magicDefense = 18;
                itemID = 2103;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Chestplate";
                level = 48;
                health = 158;
                defense = 64;
                magicDefense = 21;
                itemID = 2104;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Chestplate";
                level = 58;
                health = 202;
                defense = 76;
                magicDefense = 26;
                itemID = 2105;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Elisa Chestplate";
                level = 68;
                health = 246;
                defense = 100;
                magicDefense = 33;
                itemID = 2106;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Gloria Chestplate";
                level = 78;
                health = 315;
                defense = 124;
                magicDefense = 42;
                itemID = 2107;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 450;
                defense = 206;
                magicDefense = 70;
                itemID = 2108;
                material = Material.DIAMOND_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Chestplate";
                level = 28;
                health = 40;
                defense = 14;
                magicDefense = 24;
                itemID = 2202;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Chestplate";
                level = 38;
                health = 58;
                defense = 20;
                magicDefense = 32;
                itemID = 2203;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Chestplate";
                level = 48;
                health = 82;
                defense = 24;
                magicDefense = 42;
                itemID = 2204;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Wizardy Chestplate";
                level = 58;
                health = 104;
                defense = 32;
                magicDefense = 53;
                itemID = 2205;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Xhar Chestplate";
                level = 68;
                health = 124;
                defense = 40;
                magicDefense = 68;
                itemID = 2206;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Chamor Chestplate";
                level = 78;
                health = 158;
                defense = 48;
                magicDefense = 82;
                itemID = 2207;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Neferti Chestplate";
                level = 88;
                health = 226;
                defense = 81;
                magicDefense = 142;
                itemID = 2208;
                material = Material.GOLDEN_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            if (placementNumber == 1) {
                name = "Sunset Chestplate";
                level = 28;
                health = 46;
                defense = 20;
                magicDefense = 20;
                itemID = 2302;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Chestplate";
                level = 38;
                health = 68;
                defense = 28;
                magicDefense = 28;
                itemID = 2303;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Chestplate";
                level = 48;
                health = 92;
                defense = 36;
                magicDefense = 36;
                itemID = 2304;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Lupy Chestplate";
                level = 58;
                health = 120;
                defense = 44;
                magicDefense = 44;
                itemID = 2305;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Fox Chestplate";
                level = 68;
                health = 150;
                defense = 54;
                magicDefense = 54;
                itemID = 2306;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Luna Chestplate";
                level = 78;
                health = 190;
                defense = 70;
                magicDefense = 70;
                itemID = 2307;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Shadow Chestplate";
                level = 88;
                health = 270;
                defense = 118;
                magicDefense = 118;
                itemID = 2308;
                material = Material.CHAINMAIL_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Chestplate";
                level = 28;
                health = 55;
                defense = 26;
                magicDefense = 26;
                itemID = 2402;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Chestplate";
                level = 38;
                health = 80;
                defense = 36;
                magicDefense = 36;
                itemID = 2403;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Chestplate";
                level = 48;
                health = 111;
                defense = 47;
                magicDefense = 47;
                itemID = 2404;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Eleaza Chestplate";
                level = 58;
                health = 142;
                defense = 58;
                magicDefense = 58;
                itemID = 2405;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Gaia Chestplate";
                level = 68;
                health = 174;
                defense = 71;
                magicDefense = 71;
                itemID = 2406;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Holyshion Chestplate";
                level = 78;
                health = 220;
                defense = 91;
                magicDefense = 91;
                itemID = 2407;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Khraje Chestplate";
                level = 88;
                health = 315;
                defense = 153;
                magicDefense = 153;
                itemID = 2408;
                material = Material.DIAMOND_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Chestplate";
                level = 28;
                health = 62;
                defense = 24;
                magicDefense = 5;
                itemID = 2502;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Chestplate";
                level = 38;
                health = 90;
                defense = 32;
                magicDefense = 8;
                itemID = 2503;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Chestplate";
                level = 48;
                health = 126;
                defense = 42;
                magicDefense = 10;
                itemID = 2504;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Fury Plate Chestplate";
                level = 58;
                health = 162;
                defense = 52;
                magicDefense = 14;
                itemID = 2505;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Ceres Chestplate";
                level = 68;
                health = 198;
                defense = 68;
                magicDefense = 17;
                itemID = 2506;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Zakar Chestplate";
                level = 78;
                health = 253;
                defense = 82;
                magicDefense = 21;
                itemID = 2507;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Raxes Chestplate";
                level = 88;
                health = 360;
                defense = 142;
                magicDefense = 30;
                itemID = 2508;
                material = Material.IRON_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Chestplate";
                level = 28;
                health = 148;
                defense = 64;
                magicDefense = 34;
                itemID = 2602;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Chestplate";
                level = 38;
                health = 214;
                defense = 92;
                magicDefense = 48;
                itemID = 2603;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Chestplate";
                level = 48;
                health = 294;
                defense = 116;
                magicDefense = 60;
                itemID = 2604;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Aqua Chestplate";
                level = 58;
                health = 380;
                defense = 142;
                magicDefense = 76;
                itemID = 2605;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Water Spirit Chestplate";
                level = 68;
                health = 460;
                defense = 182;
                magicDefense = 96;
                itemID = 2606;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Mera Chestplate";
                level = 78;
                health = 590;
                defense = 224;
                magicDefense = 118;
                itemID = 2607;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Atlantean Chestplate";
                level = 88;
                health = 840;
                defense = 380;
                magicDefense = 200;
                itemID = 2608;
                material = Material.IRON_CHESTPLATE;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor chestplate = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats, itemID);
        if (isColorful) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) chestplate.getItemStack().getItemMeta();
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
            chestplate.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return chestplate.getItemStack();
    }
}
