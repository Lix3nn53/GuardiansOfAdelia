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
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Helmet";
                level = 14;
                health = 36;
                defense = 14;
                magicDefense = 10;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Helmet";
                level = 24;
                health = 180;
                defense = 27;
                magicDefense = 18;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Helmet";
                level = 34;
                health = 300;
                defense = 42;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Helmet";
                level = 44;
                health = 470;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Speedy Helmet";
                level = 54;
                health = 650;
                defense = 100;
                magicDefense = 65;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Wolf Helmet";
                level = 64;
                health = 870;
                defense = 130;
                magicDefense = 87;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Moottalis Helmet";
                level = 74;
                health = 1100;
                defense = 165;
                magicDefense = 110;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 1450;
                defense = 218;
                magicDefense = 144;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Helmet";
                level = 24;
                health = 357;
                defense = 35;
                magicDefense = 14;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Helmet";
                level = 34;
                health = 570;
                defense = 58;
                magicDefense = 22;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Helmet";
                level = 44;
                health = 940;
                defense = 92;
                magicDefense = 37;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Helmet";
                level = 54;
                health = 1280;
                defense = 128;
                magicDefense = 51;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Elisa Helmet";
                level = 64;
                health = 1720;
                defense = 172;
                magicDefense = 68;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Gloria Helmet";
                level = 74;
                health = 2140;
                defense = 214;
                magicDefense = 85;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 2800;
                defense = 280;
                magicDefense = 114;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Helmet";
                level = 24;
                health = 180;
                defense = 18;
                magicDefense = 18;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Helmet";
                level = 34;
                health = 300;
                defense = 28;
                magicDefense = 28;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Circlet";
                level = 44;
                health = 470;
                defense = 48;
                magicDefense = 48;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 4) {
                name = "Wizardy Circlet";
                level = 54;
                health = 680;
                defense = 64;
                magicDefense = 64;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 5) {
                name = "Xhar Circlet";
                level = 64;
                health = 870;
                defense = 90;
                magicDefense = 90;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 6) {
                name = "Chamor Circlet";
                level = 74;
                health = 1090;
                defense = 105;
                magicDefense = 105;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 7) {
                name = "Neferti Circlet";
                level = 84;
                health = 1450;
                defense = 150;
                magicDefense = 150;
                material = Material.GOLDEN_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            if (placementNumber == 1) {
                name = "Sunset Helmet";
                level = 24;
                health = 272;
                defense = 27;
                magicDefense = 18;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Helmet";
                level = 34;
                health = 440;
                defense = 42;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Helmet";
                level = 44;
                health = 710;
                defense = 70;
                magicDefense = 48;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Lupy Helmet";
                level = 54;
                health = 980;
                defense = 100;
                magicDefense = 65;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Fox Helmet";
                level = 64;
                health = 1320;
                defense = 130;
                magicDefense = 88;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Luna Helmet";
                level = 74;
                health = 1600;
                defense = 165;
                magicDefense = 110;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Shadow Helmet";
                level = 84;
                health = 2180;
                defense = 218;
                magicDefense = 144;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Helmet";
                level = 24;
                health = 280;
                defense = 21;
                magicDefense = 28;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Helmet";
                level = 34;
                health = 450;
                defense = 34;
                magicDefense = 45;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Helmet";
                level = 44;
                health = 740;
                defense = 55;
                magicDefense = 74;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Eleaza Helmet";
                level = 54;
                health = 1020;
                defense = 78;
                magicDefense = 102;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Gaia Helmet";
                level = 64;
                health = 1350;
                defense = 102;
                magicDefense = 138;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Holyshion Helmet";
                level = 74;
                health = 1720;
                defense = 128;
                magicDefense = 172;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Khraje Helmet";
                level = 84;
                health = 2300;
                defense = 170;
                magicDefense = 228;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Helmet";
                level = 24;
                health = 360;
                defense = 27;
                magicDefense = 18;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Helmet";
                level = 34;
                health = 580;
                defense = 44;
                magicDefense = 28;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Helmet";
                level = 44;
                health = 940;
                defense = 70;
                magicDefense = 48;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Fury Plate Helmet";
                level = 54;
                health = 1300;
                defense = 98;
                magicDefense = 64;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Ceres Helmet";
                level = 64;
                health = 1740;
                defense = 130;
                magicDefense = 88;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Zakar Helmet";
                level = 74;
                health = 2180;
                defense = 164;
                magicDefense = 108;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Raxes Helmet";
                level = 84;
                health = 2900;
                defense = 218;
                magicDefense = 144;
                material = Material.IRON_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Helmet";
                level = 24;
                health = 360;
                defense = 27;
                magicDefense = 18;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Helmet";
                level = 34;
                health = 580;
                defense = 44;
                magicDefense = 28;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Helmet";
                level = 44;
                health = 940;
                defense = 70;
                magicDefense = 48;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Aqua Helmet";
                level = 54;
                health = 1300;
                defense = 98;
                magicDefense = 64;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Water Spirit Helmet";
                level = 64;
                health = 1740;
                defense = 130;
                magicDefense = 88;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Mera Helmet";
                level = 74;
                health = 2180;
                defense = 164;
                magicDefense = 108;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Atlantean Helmet";
                level = 84;
                health = 2900;
                defense = 218;
                magicDefense = 144;
                material = Material.IRON_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Helmet";
                level = 24;
                health = 272;
                defense = 27;
                magicDefense = 18;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Helmet";
                level = 34;
                health = 440;
                defense = 42;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Helmet";
                level = 44;
                health = 710;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Speedy Helmet";
                level = 54;
                health = 980;
                defense = 100;
                magicDefense = 65;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Wolf Helmet";
                level = 64;
                health = 1320;
                defense = 130;
                magicDefense = 87;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Moottalis Helmet";
                level = 74;
                health = 1600;
                defense = 165;
                magicDefense = 110;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 2180;
                defense = 218;
                magicDefense = 144;
                material = Material.CHAINMAIL_HELMET;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor helmet = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        if (isColorful) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) helmet.getItemStack().getItemMeta();
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
            helmet.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return helmet.getItemStack();
    }
}
