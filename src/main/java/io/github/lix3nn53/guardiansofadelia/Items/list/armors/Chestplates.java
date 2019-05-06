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
                health = 370;
                defense = 53;
                magicDefense = 38;
                itemID = 2002;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Chestplate";
                level = 38;
                health = 580;
                defense = 90;
                magicDefense = 60;
                itemID = 2003;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Chestplate";
                level = 48;
                health = 950;
                defense = 141;
                magicDefense = 94;
                itemID = 2004;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Speedy Chestplate";
                level = 58;
                health = 1300;
                defense = 196;
                magicDefense = 130;
                itemID = 2005;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Wolf Chestplate";
                level = 68;
                health = 1740;
                defense = 260;
                magicDefense = 174;
                itemID = 2006;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Moottalis Chestplate";
                level = 78;
                health = 2200;
                defense = 325;
                magicDefense = 218;
                itemID = 2007;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 2900;
                defense = 435;
                magicDefense = 290;
                itemID = 2008;
                material = Material.CHAINMAIL_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Chestplate";
                level = 28;
                health = 714;
                defense = 71;
                magicDefense = 28;
                itemID = 2102;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Chestplate";
                level = 38;
                health = 1150;
                defense = 114;
                magicDefense = 45;
                itemID = 2103;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Chestplate";
                level = 48;
                health = 1850;
                defense = 185;
                magicDefense = 74;
                itemID = 2104;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Chestplate";
                level = 58;
                health = 2570;
                defense = 260;
                magicDefense = 100;
                itemID = 2105;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Elisa Chestplate";
                level = 68;
                health = 3400;
                defense = 342;
                magicDefense = 135;
                itemID = 2106;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Gloria Chestplate";
                level = 78;
                health = 4250;
                defense = 424;
                magicDefense = 171;
                itemID = 2107;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 5700;
                defense = 560;
                magicDefense = 230;
                itemID = 2108;
                material = Material.DIAMOND_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Chestplate";
                level = 28;
                health = 360;
                defense = 38;
                magicDefense = 38;
                itemID = 2202;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Chestplate";
                level = 38;
                health = 580;
                defense = 60;
                magicDefense = 60;
                itemID = 2203;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Chestplate";
                level = 48;
                health = 950;
                defense = 100;
                magicDefense = 100;
                itemID = 2204;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Wizardy Chestplate";
                level = 58;
                health = 1350;
                defense = 128;
                magicDefense = 128;
                itemID = 2205;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Xhar Chestplate";
                level = 68;
                health = 1700;
                defense = 174;
                magicDefense = 174;
                itemID = 2206;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Chamor Chestplate";
                level = 78;
                health = 2200;
                defense = 216;
                magicDefense = 216;
                itemID = 2207;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Neferti Chestplate";
                level = 88;
                health = 2900;
                defense = 288;
                magicDefense = 288;
                itemID = 2208;
                material = Material.GOLDEN_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            if (placementNumber == 1) {
                name = "Sunset Chestplate";
                level = 28;
                health = 550;
                defense = 53;
                magicDefense = 38;
                itemID = 2302;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Chestplate";
                level = 38;
                health = 872;
                defense = 90;
                magicDefense = 58;
                itemID = 2303;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Chestplate";
                level = 48;
                health = 1420;
                defense = 141;
                magicDefense = 94;
                itemID = 2304;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Lupy Chestplate";
                level = 58;
                health = 1960;
                defense = 196;
                magicDefense = 130;
                itemID = 2305;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Fox Chestplate";
                level = 68;
                health = 2620;
                defense = 260;
                magicDefense = 174;
                itemID = 2306;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Luna Chestplate";
                level = 78;
                health = 3270;
                defense = 325;
                magicDefense = 218;
                itemID = 2307;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Shadow Chestplate";
                level = 88;
                health = 4380;
                defense = 435;
                magicDefense = 288;
                itemID = 2308;
                material = Material.CHAINMAIL_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Chestplate";
                level = 28;
                health = 570;
                defense = 42;
                magicDefense = 57;
                itemID = 2402;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Chestplate";
                level = 38;
                health = 915;
                defense = 68;
                magicDefense = 91;
                itemID = 2403;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Chestplate";
                level = 48;
                health = 1480;
                defense = 112;
                magicDefense = 148;
                itemID = 2404;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Eleaza Chestplate";
                level = 58;
                health = 2050;
                defense = 154;
                magicDefense = 205;
                itemID = 2405;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Gaia Chestplate";
                level = 68;
                health = 2750;
                defense = 205;
                magicDefense = 274;
                itemID = 2406;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Holyshion Chestplate";
                level = 78;
                health = 3400;
                defense = 258;
                magicDefense = 342;
                itemID = 2407;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Khraje Chestplate";
                level = 88;
                health = 4500;
                defense = 340;
                magicDefense = 450;
                itemID = 2408;
                material = Material.DIAMOND_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Chestplate";
                level = 28;
                health = 720;
                defense = 53;
                magicDefense = 35;
                itemID = 2502;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Chestplate";
                level = 38;
                health = 1150;
                defense = 88;
                magicDefense = 58;
                itemID = 2503;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Chestplate";
                level = 48;
                health = 1900;
                defense = 142;
                magicDefense = 96;
                itemID = 2504;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Fury Plate Chestplate";
                level = 58;
                health = 2620;
                defense = 196;
                magicDefense = 130;
                itemID = 2505;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Ceres Chestplate";
                level = 68;
                health = 3500;
                defense = 260;
                magicDefense = 174;
                itemID = 2506;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Zakar Chestplate";
                level = 78;
                health = 4350;
                defense = 328;
                magicDefense = 218;
                itemID = 2507;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Raxes Chestplate";
                level = 88;
                health = 5800;
                defense = 432;
                magicDefense = 288;
                itemID = 2508;
                material = Material.IRON_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Chestplate";
                level = 28;
                health = 720;
                defense = 53;
                magicDefense = 35;
                itemID = 2602;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Chestplate";
                level = 38;
                health = 1150;
                defense = 88;
                magicDefense = 58;
                itemID = 2603;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Chestplate";
                level = 48;
                health = 1900;
                defense = 142;
                magicDefense = 96;
                itemID = 2604;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Aqua Chestplate";
                level = 58;
                health = 2620;
                defense = 196;
                magicDefense = 130;
                itemID = 2605;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Water Spirit Chestplate";
                level = 68;
                health = 3500;
                defense = 260;
                magicDefense = 174;
                itemID = 2606;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Mera Chestplate";
                level = 78;
                health = 4350;
                defense = 328;
                magicDefense = 218;
                itemID = 2607;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Atlantean Chestplate";
                level = 88;
                health = 5800;
                defense = 432;
                magicDefense = 288;
                itemID = 2608;
                material = Material.IRON_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Chestplate";
                level = 28;
                health = 550;
                defense = 53;
                magicDefense = 38;
                itemID = 2702;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Chestplate";
                level = 38;
                health = 872;
                defense = 90;
                magicDefense = 60;
                itemID = 2703;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Chestplate";
                level = 48;
                health = 1420;
                defense = 141;
                magicDefense = 94;
                itemID = 2704;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Speedy Chestplate";
                level = 58;
                health = 1960;
                defense = 196;
                magicDefense = 130;
                itemID = 2705;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Wolf Chestplate";
                level = 68;
                health = 2620;
                defense = 260;
                magicDefense = 174;
                itemID = 2706;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Moottalis Chestplate";
                level = 78;
                health = 3270;
                defense = 325;
                magicDefense = 218;
                itemID = 2707;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 4380;
                defense = 435;
                magicDefense = 290;
                itemID = 2708;
                material = Material.CHAINMAIL_CHESTPLATE;
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
            } else if (rpgClass.equals(RPGClass.ROGUE)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 0, 26));
            } else if (rpgClass.equals(RPGClass.PALADIN)) {
                leatherArmorMeta.setColor(Color.fromRGB(230, 230, 0));
            } else if (rpgClass.equals(RPGClass.WARRIOR)) {
                leatherArmorMeta.setColor(Color.fromRGB(180, 0, 0));
            } else if (rpgClass.equals(RPGClass.MONK)) {
                leatherArmorMeta.setColor(Color.fromRGB(230, 140, 0));
            } else if (rpgClass.equals(RPGClass.HUNTER)) {
                leatherArmorMeta.setColor(Color.fromRGB(35, 140, 35));
            }
            chestplate.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return chestplate.getItemStack();
    }
}
