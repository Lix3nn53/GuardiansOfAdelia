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
                health = 180;
                defense = 27;
                magicDefense = 18;
                itemID = 3002;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Helmet";
                level = 34;
                health = 300;
                defense = 42;
                magicDefense = 30;
                itemID = 3003;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Helmet";
                level = 44;
                health = 470;
                defense = 70;
                magicDefense = 50;
                itemID = 3004;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Speedy Helmet";
                level = 54;
                health = 650;
                defense = 100;
                magicDefense = 65;
                itemID = 3005;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Wolf Helmet";
                level = 64;
                health = 870;
                defense = 130;
                magicDefense = 87;
                itemID = 3006;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Moottalis Helmet";
                level = 74;
                health = 1100;
                defense = 165;
                magicDefense = 110;
                itemID = 3007;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 1450;
                defense = 218;
                magicDefense = 144;
                itemID = 3008;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Helmet";
                level = 24;
                health = 357;
                defense = 35;
                magicDefense = 14;
                itemID = 3102;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Helmet";
                level = 34;
                health = 570;
                defense = 58;
                magicDefense = 22;
                itemID = 3103;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Helmet";
                level = 44;
                health = 940;
                defense = 92;
                magicDefense = 37;
                itemID = 3104;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Helmet";
                level = 54;
                health = 1280;
                defense = 128;
                magicDefense = 51;
                itemID = 3105;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Elisa Helmet";
                level = 64;
                health = 1720;
                defense = 172;
                magicDefense = 68;
                itemID = 3106;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Gloria Helmet";
                level = 74;
                health = 2140;
                defense = 214;
                magicDefense = 85;
                itemID = 3107;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 2800;
                defense = 280;
                magicDefense = 114;
                itemID = 3108;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Helmet";
                level = 24;
                health = 180;
                defense = 18;
                magicDefense = 18;
                itemID = 3202;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Helmet";
                level = 34;
                health = 300;
                defense = 28;
                magicDefense = 28;
                itemID = 3203;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Circlet";
                level = 44;
                health = 470;
                defense = 48;
                magicDefense = 48;
                itemID = 3204;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 4) {
                name = "Wizardy Circlet";
                level = 54;
                health = 680;
                defense = 64;
                magicDefense = 64;
                itemID = 3205;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 5) {
                name = "Xhar Circlet";
                level = 64;
                health = 870;
                defense = 90;
                magicDefense = 90;
                itemID = 3206;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 6) {
                name = "Chamor Circlet";
                level = 74;
                health = 1090;
                defense = 105;
                magicDefense = 105;
                itemID = 3207;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 7) {
                name = "Neferti Circlet";
                level = 84;
                health = 1450;
                defense = 150;
                magicDefense = 150;
                itemID = 3208;
                material = Material.GOLDEN_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            if (placementNumber == 1) {
                name = "Sunset Helmet";
                level = 24;
                health = 272;
                defense = 27;
                magicDefense = 18;
                itemID = 3302;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Helmet";
                level = 34;
                health = 440;
                defense = 42;
                magicDefense = 30;
                itemID = 3303;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Helmet";
                level = 44;
                health = 710;
                defense = 70;
                magicDefense = 48;
                itemID = 3304;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Lupy Helmet";
                level = 54;
                health = 980;
                defense = 100;
                magicDefense = 65;
                itemID = 3305;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Fox Helmet";
                level = 64;
                health = 1320;
                defense = 130;
                magicDefense = 88;
                itemID = 3306;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Luna Helmet";
                level = 74;
                health = 1600;
                defense = 165;
                magicDefense = 110;
                itemID = 3307;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Shadow Helmet";
                level = 84;
                health = 2180;
                defense = 218;
                magicDefense = 144;
                itemID = 3308;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Helmet";
                level = 24;
                health = 280;
                defense = 21;
                magicDefense = 28;
                itemID = 3402;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Helmet";
                level = 34;
                health = 450;
                defense = 34;
                magicDefense = 45;
                itemID = 3403;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Helmet";
                level = 44;
                health = 740;
                defense = 55;
                magicDefense = 74;
                itemID = 3404;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Eleaza Helmet";
                level = 54;
                health = 1020;
                defense = 78;
                magicDefense = 102;
                itemID = 3405;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Gaia Helmet";
                level = 64;
                health = 1350;
                defense = 102;
                magicDefense = 138;
                itemID = 3406;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Holyshion Helmet";
                level = 74;
                health = 1720;
                defense = 128;
                magicDefense = 172;
                itemID = 3407;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Khraje Helmet";
                level = 84;
                health = 2300;
                defense = 170;
                magicDefense = 228;
                itemID = 3408;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Helmet";
                level = 24;
                health = 360;
                defense = 27;
                magicDefense = 18;
                itemID = 3502;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Helmet";
                level = 34;
                health = 580;
                defense = 44;
                magicDefense = 28;
                itemID = 3503;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Helmet";
                level = 44;
                health = 940;
                defense = 70;
                magicDefense = 48;
                itemID = 3504;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Fury Plate Helmet";
                level = 54;
                health = 1300;
                defense = 98;
                magicDefense = 64;
                itemID = 3505;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Ceres Helmet";
                level = 64;
                health = 1740;
                defense = 130;
                magicDefense = 88;
                itemID = 3506;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Zakar Helmet";
                level = 74;
                health = 2180;
                defense = 164;
                magicDefense = 108;
                itemID = 3507;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Raxes Helmet";
                level = 84;
                health = 2900;
                defense = 218;
                magicDefense = 144;
                itemID = 3508;
                material = Material.IRON_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Helmet";
                level = 24;
                health = 360;
                defense = 27;
                magicDefense = 18;
                itemID = 3602;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Helmet";
                level = 34;
                health = 580;
                defense = 44;
                magicDefense = 28;
                itemID = 3603;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Helmet";
                level = 44;
                health = 940;
                defense = 70;
                magicDefense = 48;
                itemID = 3604;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Aqua Helmet";
                level = 54;
                health = 1300;
                defense = 98;
                magicDefense = 64;
                itemID = 3605;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Water Spirit Helmet";
                level = 64;
                health = 1740;
                defense = 130;
                magicDefense = 88;
                itemID = 3606;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Mera Helmet";
                level = 74;
                health = 2180;
                defense = 164;
                magicDefense = 108;
                itemID = 3607;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Atlantean Helmet";
                level = 84;
                health = 2900;
                defense = 218;
                magicDefense = 144;
                itemID = 3608;
                material = Material.IRON_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Helmet";
                level = 24;
                health = 272;
                defense = 27;
                magicDefense = 18;
                itemID = 3702;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Helmet";
                level = 34;
                health = 440;
                defense = 42;
                magicDefense = 30;
                itemID = 3703;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Helmet";
                level = 44;
                health = 710;
                defense = 70;
                magicDefense = 50;
                itemID = 3704;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Speedy Helmet";
                level = 54;
                health = 980;
                defense = 100;
                magicDefense = 65;
                itemID = 3705;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Wolf Helmet";
                level = 64;
                health = 1320;
                defense = 130;
                magicDefense = 87;
                itemID = 3706;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Moottalis Helmet";
                level = 74;
                health = 1600;
                defense = 165;
                magicDefense = 110;
                itemID = 3707;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 2180;
                defense = 218;
                magicDefense = 144;
                itemID = 3708;
                material = Material.CHAINMAIL_HELMET;
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
            } else if (rpgClass.equals(RPGClass.HUNTER)) {
                leatherArmorMeta.setColor(Color.fromRGB(35, 140, 35));
            }
            helmet.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return helmet.getItemStack();
    }
}
