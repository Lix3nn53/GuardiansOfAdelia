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
        int health = 40;
        int defense = 30;
        int magicDefense = 20;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Chestplate";
                level = 18;
                health = 80;
                defense = 40;
                magicDefense = 30;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Chestplate";
                level = 28;
                health = 140;
                defense = 50;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Chestplate";
                level = 38;
                health = 200;
                defense = 90;
                magicDefense = 60;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Chestplate";
                level = 48;
                health = 320;
                defense = 140;
                magicDefense = 90;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Speedy Chestplate";
                level = 58;
                health = 450;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Wolf Chestplate";
                level = 68;
                health = 560;
                defense = 260;
                magicDefense = 170;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Moottalis Chestplate";
                level = 78;
                health = 720;
                defense = 320;
                magicDefense = 210;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 940;
                defense = 430;
                magicDefense = 290;
                material = Material.CHAINMAIL_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Chestplate";
                level = 28;
                health = 240;
                defense = 70;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Chestplate";
                level = 38;
                health = 370;
                defense = 110;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Chestplate";
                level = 48;
                health = 600;
                defense = 180;
                magicDefense = 70;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Chestplate";
                level = 58;
                health = 850;
                defense = 260;
                magicDefense = 100;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Elisa Chestplate";
                level = 68;
                health = 1100;
                defense = 340;
                magicDefense = 130;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Gloria Chestplate";
                level = 78;
                health = 1400;
                defense = 420;
                magicDefense = 170;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 1900;
                defense = 560;
                magicDefense = 230;
                material = Material.DIAMOND_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Chestplate";
                level = 28;
                health = 140;
                defense = 40;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Chestplate";
                level = 38;
                health = 210;
                defense = 60;
                magicDefense = 60;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Chestplate";
                level = 48;
                health = 320;
                defense = 100;
                magicDefense = 100;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Wizardy Chestplate";
                level = 58;
                health = 450;
                defense = 130;
                magicDefense = 130;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Xhar Chestplate";
                level = 68;
                health = 570;
                defense = 170;
                magicDefense = 170;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Chamor Chestplate";
                level = 78;
                health = 720;
                defense = 220;
                magicDefense = 220;
                material = Material.GOLDEN_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Neferti Chestplate";
                level = 88;
                health = 940;
                defense = 290;
                magicDefense = 290;
                material = Material.GOLDEN_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            if (placementNumber == 1) {
                name = "Sunset Chestplate";
                level = 28;
                health = 180;
                defense = 50;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Chestplate";
                level = 38;
                health = 290;
                defense = 90;
                magicDefense = 60;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Chestplate";
                level = 48;
                health = 470;
                defense = 140;
                magicDefense = 90;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Lupy Chestplate";
                level = 58;
                health = 630;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Fox Chestplate";
                level = 68;
                health = 850;
                defense = 260;
                magicDefense = 170;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Luna Chestplate";
                level = 78;
                health = 1050;
                defense = 320;
                magicDefense = 220;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Shadow Chestplate";
                level = 88;
                health = 1400;
                defense = 430;
                magicDefense = 290;
                material = Material.CHAINMAIL_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Chestplate";
                level = 28;
                health = 190;
                defense = 40;
                magicDefense = 60;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Chestplate";
                level = 38;
                health = 290;
                defense = 70;
                magicDefense = 90;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Chestplate";
                level = 48;
                health = 470;
                defense = 110;
                magicDefense = 150;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Eleaza Chestplate";
                level = 58;
                health = 650;
                defense = 150;
                magicDefense = 210;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Gaia Chestplate";
                level = 68;
                health = 900;
                defense = 210;
                magicDefense = 270;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Holyshion Chestplate";
                level = 78;
                health = 1100;
                defense = 260;
                magicDefense = 340;
                material = Material.DIAMOND_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Khraje Chestplate";
                level = 88;
                health = 1400;
                defense = 340;
                magicDefense = 450;
                material = Material.DIAMOND_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Chestplate";
                level = 28;
                health = 240;
                defense = 50;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Chestplate";
                level = 38;
                health = 370;
                defense = 90;
                magicDefense = 60;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Chestplate";
                level = 48;
                health = 610;
                defense = 140;
                magicDefense = 90;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Fury Plate Chestplate";
                level = 58;
                health = 840;
                defense = 200;
                magicDefense = 130;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Ceres Chestplate";
                level = 68;
                health = 1100;
                defense = 260;
                magicDefense = 170;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Zakar Chestplate";
                level = 78;
                health = 1400;
                defense = 330;
                magicDefense = 220;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Raxes Chestplate";
                level = 88;
                health = 1900;
                defense = 430;
                magicDefense = 290;
                material = Material.IRON_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Chestplate";
                level = 28;
                health = 240;
                defense = 50;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Chestplate";
                level = 38;
                health = 370;
                defense = 90;
                magicDefense = 60;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Chestplate";
                level = 48;
                health = 610;
                defense = 140;
                magicDefense = 90;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Aqua Chestplate";
                level = 58;
                health = 840;
                defense = 200;
                magicDefense = 130;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "Water Spirit Chestplate";
                level = 68;
                health = 1100;
                defense = 260;
                magicDefense = 170;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Mera Chestplate";
                level = 78;
                health = 1400;
                defense = 330;
                magicDefense = 220;
                material = Material.IRON_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Atlantean Chestplate";
                level = 88;
                health = 1900;
                defense = 430;
                magicDefense = 290;
                material = Material.IRON_CHESTPLATE;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Chestplate";
                level = 28;
                health = 180;
                defense = 50;
                magicDefense = 40;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Chestplate";
                level = 38;
                health = 290;
                defense = 90;
                magicDefense = 60;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Chestplate";
                level = 48;
                health = 470;
                defense = 140;
                magicDefense = 90;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 4) {
                name = "Speedy Chestplate";
                level = 58;
                health = 630;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 5) {
                name = "White Wolf Chestplate";
                level = 68;
                health = 850;
                defense = 260;
                magicDefense = 170;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 6) {
                name = "Moottalis Chestplate";
                level = 78;
                health = 1050;
                defense = 325;
                magicDefense = 220;
                material = Material.CHAINMAIL_CHESTPLATE;
            } else if (placementNumber == 7) {
                name = "Darkshuter Chestplate";
                level = 88;
                health = 1400;
                defense = 420;
                magicDefense = 290;
                material = Material.CHAINMAIL_CHESTPLATE;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor chestplate = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
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
