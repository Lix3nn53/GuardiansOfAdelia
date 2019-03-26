package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

class Helmets {

    public static ItemStack get(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        String name = "Leather Helmet";
        Material material = Material.LEATHER_HELMET;
        int level = 4;
        int health = 18;
        int defense = 10;
        int magicDefense = 7;
        int itemID = 3200;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Helmet";
                level = 14;
                health = 36;
                defense = 14;
                magicDefense = 10;
                itemID = 3201;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Helmet";
                level = 24;
                health = 46;
                defense = 14;
                magicDefense = 24;
                itemID = 3002;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Helmet";
                level = 34;
                health = 68;
                defense = 20;
                magicDefense = 32;
                itemID = 3003;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Helmet";
                level = 44;
                health = 92;
                defense = 24;
                magicDefense = 42;
                itemID = 3004;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Speedy Helmet";
                level = 54;
                health = 120;
                defense = 32;
                magicDefense = 53;
                itemID = 3005;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Wolf Helmet";
                level = 64;
                health = 150;
                defense = 40;
                magicDefense = 68;
                itemID = 3006;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Moottalis Helmet";
                level = 74;
                health = 190;
                defense = 48;
                magicDefense = 82;
                itemID = 3007;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 270;
                defense = 81;
                magicDefense = 142;
                itemID = 3008;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Helmet";
                level = 24;
                health = 78;
                defense = 34;
                magicDefense = 12;
                itemID = 3102;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Helmet";
                level = 34;
                health = 112;
                defense = 50;
                magicDefense = 18;
                itemID = 3103;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Helmet";
                level = 44;
                health = 158;
                defense = 64;
                magicDefense = 21;
                itemID = 3104;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Helmet";
                level = 54;
                health = 202;
                defense = 76;
                magicDefense = 26;
                itemID = 3105;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Elisa Helmet";
                level = 64;
                health = 246;
                defense = 100;
                magicDefense = 33;
                itemID = 3106;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Gloria Helmet";
                level = 74;
                health = 315;
                defense = 124;
                magicDefense = 42;
                itemID = 3107;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 450;
                defense = 206;
                magicDefense = 70;
                itemID = 3108;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Helmet";
                level = 24;
                health = 40;
                defense = 14;
                magicDefense = 24;
                itemID = 3202;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Helmet";
                level = 34;
                health = 58;
                defense = 20;
                magicDefense = 32;
                itemID = 3203;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Circlet";
                level = 44;
                health = 82;
                defense = 24;
                magicDefense = 42;
                itemID = 3204;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 4) {
                name = "Wizardy Circlet";
                level = 54;
                health = 104;
                defense = 32;
                magicDefense = 53;
                itemID = 3205;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 5) {
                name = "Xhar Circlet";
                level = 64;
                health = 124;
                defense = 40;
                magicDefense = 68;
                itemID = 3206;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 6) {
                name = "Chamor Circlet";
                level = 74;
                health = 158;
                defense = 48;
                magicDefense = 82;
                itemID = 3207;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 7) {
                name = "Neferti Circlet";
                level = 84;
                health = 226;
                defense = 81;
                magicDefense = 142;
                itemID = 3208;
                material = Material.GOLDEN_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            if (placementNumber == 1) {
                name = "Sunset Helmet";
                level = 24;
                health = 46;
                defense = 20;
                magicDefense = 20;
                itemID = 3302;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Helmet";
                level = 34;
                health = 68;
                defense = 28;
                magicDefense = 28;
                itemID = 3303;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Helmet";
                level = 44;
                health = 92;
                defense = 36;
                magicDefense = 36;
                itemID = 3304;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Lupy Helmet";
                level = 54;
                health = 120;
                defense = 44;
                magicDefense = 44;
                itemID = 3305;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Fox Helmet";
                level = 64;
                health = 150;
                defense = 54;
                magicDefense = 54;
                itemID = 3306;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Luna Helmet";
                level = 74;
                health = 190;
                defense = 70;
                magicDefense = 70;
                itemID = 3307;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Shadow Helmet";
                level = 84;
                health = 270;
                defense = 118;
                magicDefense = 118;
                itemID = 3308;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Helmet";
                level = 24;
                health = 55;
                defense = 26;
                magicDefense = 26;
                itemID = 3402;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Helmet";
                level = 34;
                health = 80;
                defense = 36;
                magicDefense = 36;
                itemID = 3403;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Helmet";
                level = 44;
                health = 111;
                defense = 47;
                magicDefense = 47;
                itemID = 3404;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Eleaza Helmet";
                level = 54;
                health = 142;
                defense = 58;
                magicDefense = 58;
                itemID = 3405;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Gaia Helmet";
                level = 64;
                health = 174;
                defense = 71;
                magicDefense = 71;
                itemID = 3406;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Holyshion Helmet";
                level = 74;
                health = 220;
                defense = 91;
                magicDefense = 91;
                itemID = 3407;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Khraje Helmet";
                level = 84;
                health = 315;
                defense = 153;
                magicDefense = 153;
                itemID = 3408;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Helmet";
                level = 24;
                health = 62;
                defense = 24;
                magicDefense = 5;
                itemID = 3502;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Helmet";
                level = 34;
                health = 90;
                defense = 32;
                magicDefense = 8;
                itemID = 3503;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Helmet";
                level = 44;
                health = 126;
                defense = 42;
                magicDefense = 10;
                itemID = 3504;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Fury Plate Helmet";
                level = 54;
                health = 162;
                defense = 52;
                magicDefense = 14;
                itemID = 3505;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Ceres Helmet";
                level = 64;
                health = 198;
                defense = 68;
                magicDefense = 17;
                itemID = 3506;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Zakar Helmet";
                level = 74;
                health = 253;
                defense = 82;
                magicDefense = 21;
                itemID = 3507;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Raxes Helmet";
                level = 84;
                health = 360;
                defense = 142;
                magicDefense = 30;
                itemID = 3508;
                material = Material.IRON_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Helmet";
                level = 24;
                health = 56;
                defense = 21;
                magicDefense = 12;
                itemID = 3602;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Helmet";
                level = 34;
                health = 78;
                defense = 30;
                magicDefense = 18;
                itemID = 3603;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Helmet";
                level = 44;
                health = 110;
                defense = 38;
                magicDefense = 22;
                itemID = 3604;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Aqua Helmet";
                level = 54;
                health = 142;
                defense = 48;
                magicDefense = 28;
                itemID = 3605;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Water Spirit Helmet";
                level = 64;
                health = 174;
                defense = 61;
                magicDefense = 36;
                itemID = 3606;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Mera Helmet";
                level = 74;
                health = 220;
                defense = 75;
                magicDefense = 44;
                itemID = 3607;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Atlantean Helmet";
                level = 84;
                health = 315;
                defense = 128;
                magicDefense = 74;
                itemID = 3608;
                material = Material.IRON_HELMET;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor helmet = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats, itemID);
        if (isColorful) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) helmet.getItemStack().getItemMeta();
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
            helmet.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return helmet.getItemStack();
    }
}
