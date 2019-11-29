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
        int health = 20;
        int defense = 10;
        int magicDefense = 5;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Helmet";
                level = 14;
                health = 40;
                defense = 15;
                magicDefense = 10;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Helmet";
                level = 24;
                health = 70;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Helmet";
                level = 34;
                health = 100;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Helmet";
                level = 44;
                health = 150;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Speedy Helmet";
                level = 54;
                health = 210;
                defense = 100;
                magicDefense = 65;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Wolf Helmet";
                level = 64;
                health = 270;
                defense = 130;
                magicDefense = 90;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Moottalis Helmet";
                level = 74;
                health = 350;
                defense = 165;
                magicDefense = 110;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 470;
                defense = 220;
                magicDefense = 140;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Helmet";
                level = 24;
                health = 120;
                defense = 35;
                magicDefense = 15;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Helmet";
                level = 34;
                health = 190;
                defense = 60;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Helmet";
                level = 44;
                health = 310;
                defense = 90;
                magicDefense = 40;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Helmet";
                level = 54;
                health = 420;
                defense = 130;
                magicDefense = 50;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Elisa Helmet";
                level = 64;
                health = 550;
                defense = 170;
                magicDefense = 70;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Gloria Helmet";
                level = 74;
                health = 680;
                defense = 210;
                magicDefense = 90;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 900;
                defense = 280;
                magicDefense = 110;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Helmet";
                level = 24;
                health = 70;
                defense = 20;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Helmet";
                level = 34;
                health = 100;
                defense = 30;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Circlet";
                level = 44;
                health = 150;
                defense = 50;
                magicDefense = 50;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 4) {
                name = "Wizardy Circlet";
                level = 54;
                health = 210;
                defense = 65;
                magicDefense = 65;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 5) {
                name = "Xhar Circlet";
                level = 64;
                health = 270;
                defense = 90;
                magicDefense = 90;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 6) {
                name = "Chamor Circlet";
                level = 74;
                health = 350;
                defense = 105;
                magicDefense = 105;
                material = Material.GOLDEN_HELMET;
            } else if (placementNumber == 7) {
                name = "Neferti Circlet";
                level = 84;
                health = 470;
                defense = 150;
                magicDefense = 150;
                material = Material.GOLDEN_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            if (placementNumber == 1) {
                name = "Sunset Helmet";
                level = 24;
                health = 90;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Helmet";
                level = 34;
                health = 150;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Helmet";
                level = 44;
                health = 220;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Lupy Helmet";
                level = 54;
                health = 320;
                defense = 100;
                magicDefense = 65;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Fox Helmet";
                level = 64;
                health = 430;
                defense = 130;
                magicDefense = 90;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Luna Helmet";
                level = 74;
                health = 500;
                defense = 165;
                magicDefense = 110;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Shadow Helmet";
                level = 84;
                health = 700;
                defense = 220;
                magicDefense = 140;
                material = Material.CHAINMAIL_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Helmet";
                level = 24;
                health = 90;
                defense = 20;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Helmet";
                level = 34;
                health = 150;
                defense = 35;
                magicDefense = 45;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Helmet";
                level = 44;
                health = 240;
                defense = 55;
                magicDefense = 75;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 4) {
                name = "Eleaza Helmet";
                level = 54;
                health = 330;
                defense = 80;
                magicDefense = 100;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 5) {
                name = "Gaia Helmet";
                level = 64;
                health = 430;
                defense = 100;
                magicDefense = 140;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 6) {
                name = "Holyshion Helmet";
                level = 74;
                health = 560;
                defense = 130;
                magicDefense = 170;
                material = Material.DIAMOND_HELMET;
            } else if (placementNumber == 7) {
                name = "Khraje Helmet";
                level = 84;
                health = 740;
                defense = 170;
                magicDefense = 220;
                material = Material.DIAMOND_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Helmet";
                level = 24;
                health = 110;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Helmet";
                level = 34;
                health = 190;
                defense = 45;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Helmet";
                level = 44;
                health = 300;
                defense = 70;
                magicDefense = 50;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Fury Plate Helmet";
                level = 54;
                health = 420;
                defense = 100;
                magicDefense = 65;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Ceres Helmet";
                level = 64;
                health = 560;
                defense = 130;
                magicDefense = 90;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Zakar Helmet";
                level = 74;
                health = 700;
                defense = 160;
                magicDefense = 110;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Raxes Helmet";
                level = 84;
                health = 920;
                defense = 220;
                magicDefense = 140;
                material = Material.IRON_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Helmet";
                level = 24;
                health = 110;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Helmet";
                level = 34;
                health = 190;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Helmet";
                level = 44;
                health = 300;
                defense = 70;
                magicDefense = 50;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 4) {
                name = "Aqua Helmet";
                level = 54;
                health = 420;
                defense = 100;
                magicDefense = 60;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 5) {
                name = "Water Spirit Helmet";
                level = 64;
                health = 560;
                defense = 125;
                magicDefense = 90;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 6) {
                name = "Mera Helmet";
                level = 74;
                health = 700;
                defense = 160;
                magicDefense = 110;
                material = Material.IRON_HELMET;
            } else if (placementNumber == 7) {
                name = "Atlantean Helmet";
                level = 84;
                health = 920;
                defense = 210;
                magicDefense = 140;
                material = Material.IRON_HELMET;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Helmet";
                level = 24;
                health = 90;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Helmet";
                level = 34;
                health = 140;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Helmet";
                level = 44;
                health = 230;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 4) {
                name = "Speedy Helmet";
                level = 54;
                health = 320;
                defense = 100;
                magicDefense = 60;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 5) {
                name = "White Wolf Helmet";
                level = 64;
                health = 430;
                defense = 125;
                magicDefense = 90;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 6) {
                name = "Moottalis Helmet";
                level = 74;
                health = 510;
                defense = 160;
                magicDefense = 110;
                material = Material.CHAINMAIL_HELMET;
            } else if (placementNumber == 7) {
                name = "Darkshuter Helmet";
                level = 84;
                health = 700;
                defense = 210;
                magicDefense = 140;
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
