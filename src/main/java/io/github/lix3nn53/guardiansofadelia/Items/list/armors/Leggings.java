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
        int health = 30;
        int defense = 16;
        int magicDefense = 12;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Leggings";
                level = 16;
                health = 60;
                defense = 24;
                magicDefense = 18;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Leggings";
                level = 26;
                health = 100;
                defense = 40;
                magicDefense = 26;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Leggings";
                level = 36;
                health = 150;
                defense = 64;
                magicDefense = 42;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Leggings";
                level = 46;
                health = 240;
                defense = 105;
                magicDefense = 70;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Speedy Leggings";
                level = 56;
                health = 320;
                defense = 150;
                magicDefense = 100;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Wolf Leggings";
                level = 66;
                health = 440;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Moottalis Leggings";
                level = 76;
                health = 520;
                defense = 245;
                magicDefense = 162;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 720;
                defense = 325;
                magicDefense = 218;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Leggings";
                level = 26;
                health = 180;
                defense = 53;
                magicDefense = 21;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Leggings";
                level = 36;
                health = 280;
                defense = 85;
                magicDefense = 34;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Leggings";
                level = 46;
                health = 450;
                defense = 140;
                magicDefense = 55;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Leggings";
                level = 56;
                health = 640;
                defense = 192;
                magicDefense = 77;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Elisa Leggings";
                level = 66;
                health = 850;
                defense = 250;
                magicDefense = 102;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Gloria Leggings";
                level = 76;
                health = 1000;
                defense = 320;
                magicDefense = 128;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 1400;
                defense = 426;
                magicDefense = 171;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Leggings";
                level = 26;
                health = 100;
                defense = 28;
                magicDefense = 28;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Leggings";
                level = 36;
                health = 150;
                defense = 45;
                magicDefense = 45;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Leggings";
                level = 46;
                health = 240;
                defense = 70;
                magicDefense = 70;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Wizardy Leggings";
                level = 56;
                health = 350;
                defense = 100;
                magicDefense = 100;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Xhar Leggings";
                level = 66;
                health = 450;
                defense = 128;
                magicDefense = 128;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Chamor Leggings";
                level = 76;
                health = 550;
                defense = 164;
                magicDefense = 164;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Neferti Leggings";
                level = 86;
                health = 720;
                defense = 216;
                magicDefense = 216;
                material = Material.GOLDEN_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            if (placementNumber == 1) {
                name = "Sunset Leggings";
                level = 26;
                health = 150;
                defense = 40;
                magicDefense = 27;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Leggings";
                level = 36;
                health = 210;
                defense = 64;
                magicDefense = 44;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Leggings";
                level = 46;
                health = 340;
                defense = 105;
                magicDefense = 70;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Lupy Leggings";
                level = 56;
                health = 470;
                defense = 150;
                magicDefense = 98;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Fox Leggings";
                level = 66;
                health = 610;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Luna Leggings";
                level = 76;
                health = 800;
                defense = 245;
                magicDefense = 164;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Shadow Leggings";
                level = 86;
                health = 1100;
                defense = 325;
                magicDefense = 218;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Leggings";
                level = 26;
                health = 150;
                defense = 32;
                magicDefense = 42;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Leggings";
                level = 36;
                health = 230;
                defense = 51;
                magicDefense = 68;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Leggings";
                level = 46;
                health = 360;
                defense = 84;
                magicDefense = 112;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Eleaza Leggings";
                level = 56;
                health = 500;
                defense = 115;
                magicDefense = 154;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Gaia Leggings";
                level = 66;
                health = 670;
                defense = 155;
                magicDefense = 205;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Holyshion Leggings";
                level = 76;
                health = 840;
                defense = 192;
                magicDefense = 258;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Khraje Leggings";
                level = 86;
                health = 1100;
                defense = 255;
                magicDefense = 342;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Leggings";
                level = 26;
                health = 180;
                defense = 40;
                magicDefense = 28;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Leggings";
                level = 36;
                health = 290;
                defense = 65;
                magicDefense = 44;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Leggings";
                level = 46;
                health = 470;
                defense = 108;
                magicDefense = 70;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Fury Plate Leggings";
                level = 56;
                health = 640;
                defense = 148;
                magicDefense = 98;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Ceres Leggings";
                level = 66;
                health = 830;
                defense = 196;
                magicDefense = 130;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Zakar Leggings";
                level = 76;
                health = 1000;
                defense = 245;
                magicDefense = 164;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Raxes Leggings";
                level = 86;
                health = 1400;
                defense = 328;
                magicDefense = 216;
                material = Material.IRON_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Leggings";
                level = 26;
                health = 180;
                defense = 40;
                magicDefense = 28;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Leggings";
                level = 36;
                health = 290;
                defense = 65;
                magicDefense = 44;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Leggings";
                level = 46;
                health = 470;
                defense = 108;
                magicDefense = 70;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Aqua Leggings";
                level = 56;
                health = 640;
                defense = 148;
                magicDefense = 98;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Water Spirit Leggings";
                level = 66;
                health = 830;
                defense = 196;
                magicDefense = 130;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Mera Leggings";
                level = 76;
                health = 1000;
                defense = 245;
                magicDefense = 164;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Atlantean Leggings";
                level = 86;
                health = 1400;
                defense = 328;
                magicDefense = 216;
                material = Material.IRON_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Leggings";
                level = 26;
                health = 150;
                defense = 40;
                magicDefense = 26;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Leggings";
                level = 36;
                health = 210;
                defense = 64;
                magicDefense = 42;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Leggings";
                level = 46;
                health = 340;
                defense = 105;
                magicDefense = 70;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Speedy Leggings";
                level = 56;
                health = 470;
                defense = 150;
                magicDefense = 100;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Wolf Leggings";
                level = 66;
                health = 610;
                defense = 200;
                magicDefense = 130;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Moottalis Leggings";
                level = 76;
                health = 800;
                defense = 245;
                magicDefense = 162;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 1100;
                defense = 325;
                magicDefense = 218;
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
