package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

class Leggings {

    public static ItemStack get(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        String name = "Leather Leggings";
        Material material = Material.LEATHER_LEGGINGS;
        int level = 6;
        int health = 24;
        int defense = 20;
        int magicDefense = 10;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Leggings";
                level = 16;
                health = 48;
                defense = 30;
                magicDefense = 20;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Leggings";
                level = 26;
                health = 80;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Leggings";
                level = 36;
                health = 120;
                defense = 70;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Leggings";
                level = 46;
                health = 190;
                defense = 110;
                magicDefense = 70;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Speedy Leggings";
                level = 56;
                health = 250;
                defense = 150;
                magicDefense = 100;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Wolf Leggings";
                level = 66;
                health = 350;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Moottalis Leggings";
                level = 76;
                health = 410;
                defense = 250;
                magicDefense = 160;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 570;
                defense = 320;
                magicDefense = 210;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Leggings";
                level = 26;
                health = 140;
                defense = 60;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Leggings";
                level = 36;
                health = 220;
                defense = 90;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Leggings";
                level = 46;
                health = 360;
                defense = 140;
                magicDefense = 60;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Leggings";
                level = 56;
                health = 500;
                defense = 190;
                magicDefense = 80;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Elisa Leggings";
                level = 66;
                health = 680;
                defense = 250;
                magicDefense = 100;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Gloria Leggings";
                level = 76;
                health = 800;
                defense = 320;
                magicDefense = 120;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 1100;
                defense = 420;
                magicDefense = 170;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Leggings";
                level = 26;
                health = 80;
                defense = 30;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Leggings";
                level = 36;
                health = 120;
                defense = 50;
                magicDefense = 50;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Leggings";
                level = 46;
                health = 190;
                defense = 70;
                magicDefense = 70;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Wizardy Leggings";
                level = 56;
                health = 280;
                defense = 100;
                magicDefense = 100;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Xhar Leggings";
                level = 66;
                health = 360;
                defense = 130;
                magicDefense = 130;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Chamor Leggings";
                level = 76;
                health = 440;
                defense = 160;
                magicDefense = 160;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Neferti Leggings";
                level = 86;
                health = 570;
                defense = 210;
                magicDefense = 210;
                material = Material.GOLDEN_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            if (placementNumber == 1) {
                name = "Sunset Leggings";
                level = 26;
                health = 120;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Leggings";
                level = 36;
                health = 170;
                defense = 70;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Leggings";
                level = 46;
                health = 270;
                defense = 100;
                magicDefense = 70;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Lupy Leggings";
                level = 56;
                health = 370;
                defense = 150;
                magicDefense = 100;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Fox Leggings";
                level = 66;
                health = 490;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Luna Leggings";
                level = 76;
                health = 640;
                defense = 240;
                magicDefense = 160;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Shadow Leggings";
                level = 86;
                health = 880;
                defense = 320;
                magicDefense = 210;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Leggings";
                level = 26;
                health = 120;
                defense = 30;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Leggings";
                level = 36;
                health = 190;
                defense = 50;
                magicDefense = 70;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Leggings";
                level = 46;
                health = 290;
                defense = 90;
                magicDefense = 110;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Eleaza Leggings";
                level = 56;
                health = 400;
                defense = 115;
                magicDefense = 154;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Gaia Leggings";
                level = 66;
                health = 540;
                defense = 160;
                magicDefense = 200;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Holyshion Leggings";
                level = 76;
                health = 670;
                defense = 200;
                magicDefense = 250;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Khraje Leggings";
                level = 86;
                health = 880;
                defense = 250;
                magicDefense = 340;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Leggings";
                level = 26;
                health = 150;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Leggings";
                level = 36;
                health = 240;
                defense = 70;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Leggings";
                level = 46;
                health = 380;
                defense = 110;
                magicDefense = 70;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Fury Plate Leggings";
                level = 56;
                health = 510;
                defense = 140;
                magicDefense = 90;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Ceres Leggings";
                level = 66;
                health = 670;
                defense = 190;
                magicDefense = 130;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Zakar Leggings";
                level = 76;
                health = 800;
                defense = 240;
                magicDefense = 160;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Raxes Leggings";
                level = 86;
                health = 1100;
                defense = 320;
                magicDefense = 210;
                material = Material.IRON_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Leggings";
                level = 26;
                health = 150;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Leggings";
                level = 36;
                health = 230;
                defense = 70;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Leggings";
                level = 46;
                health = 380;
                defense = 110;
                magicDefense = 70;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Aqua Leggings";
                level = 56;
                health = 510;
                defense = 150;
                magicDefense = 100;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Water Spirit Leggings";
                level = 66;
                health = 670;
                defense = 200;
                magicDefense = 130;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Mera Leggings";
                level = 76;
                health = 800;
                defense = 240;
                magicDefense = 160;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Atlantean Leggings";
                level = 86;
                health = 1100;
                defense = 320;
                magicDefense = 210;
                material = Material.IRON_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Leggings";
                level = 26;
                health = 120;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Leggings";
                level = 36;
                health = 170;
                defense = 70;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Leggings";
                level = 46;
                health = 270;
                defense = 110;
                magicDefense = 70;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Speedy Leggings";
                level = 56;
                health = 380;
                defense = 150;
                magicDefense = 100;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Wolf Leggings";
                level = 66;
                health = 490;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Moottalis Leggings";
                level = 76;
                health = 640;
                defense = 240;
                magicDefense = 160;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 880;
                defense = 320;
                magicDefense = 220;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor leggings = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        if (isColorful) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leggings.getItemStack().getItemMeta();
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
            leggings.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return leggings.getItemStack();
    }
}
