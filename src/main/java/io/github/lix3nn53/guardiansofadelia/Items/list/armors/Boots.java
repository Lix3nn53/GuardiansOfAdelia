package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.GearArmor;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

class Boots {

    public static ItemStack get(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        String name = "Leather Boots";
        Material material = Material.LEATHER_BOOTS;
        int level = 2;
        int health = 20;
        int defense = 10;
        int magicDefense = 5;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Boots";
                level = 12;
                health = 30;
                defense = 15;
                magicDefense = 10;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Boots";
                level = 22;
                health = 50;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Boots";
                level = 32;
                health = 80;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Boots";
                level = 42;
                health = 120;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 4) {
                name = "Speedy Boots";
                level = 52;
                health = 170;
                defense = 100;
                magicDefense = 65;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 5) {
                name = "White Wolf Boots";
                level = 62;
                health = 220;
                defense = 130;
                magicDefense = 90;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 6) {
                name = "Moottalis Boots";
                level = 72;
                health = 280;
                defense = 165;
                magicDefense = 110;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Boots";
                level = 82;
                health = 380;
                defense = 220;
                magicDefense = 140;
                material = Material.CHAINMAIL_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Boots";
                level = 22;
                health = 100;
                defense = 35;
                magicDefense = 15;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Boots";
                level = 32;
                health = 150;
                defense = 60;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Boots";
                level = 42;
                health = 250;
                defense = 90;
                magicDefense = 40;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Boots";
                level = 52;
                health = 340;
                defense = 130;
                magicDefense = 50;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 5) {
                name = "Elisa Boots";
                level = 62;
                health = 440;
                defense = 170;
                magicDefense = 70;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 6) {
                name = "Gloria Boots";
                level = 72;
                health = 550;
                defense = 210;
                magicDefense = 90;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Boots";
                level = 82;
                health = 720;
                defense = 280;
                magicDefense = 110;
                material = Material.DIAMOND_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Boots";
                level = 22;
                health = 50;
                defense = 20;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Boots";
                level = 32;
                health = 80;
                defense = 30;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Boots";
                level = 42;
                health = 120;
                defense = 50;
                magicDefense = 50;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 4) {
                name = "Wizardy Boots";
                level = 52;
                health = 170;
                defense = 70;
                magicDefense = 70;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 5) {
                name = "Xhar Boots";
                level = 62;
                health = 220;
                defense = 90;
                magicDefense = 90;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 6) {
                name = "Chamor Boots";
                level = 72;
                health = 280;
                defense = 105;
                magicDefense = 105;
                material = Material.GOLDEN_BOOTS;
            } else if (placementNumber == 7) {
                name = "Neferti Boots";
                level = 82;
                health = 380;
                defense = 150;
                magicDefense = 150;
                material = Material.GOLDEN_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.ROGUE)) {
            if (placementNumber == 1) {
                name = "Sunset Boots";
                level = 22;
                health = 70;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Boots";
                level = 32;
                health = 120;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Boots";
                level = 42;
                health = 180;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 4) {
                name = "Lupy Boots";
                level = 52;
                health = 260;
                defense = 100;
                magicDefense = 65;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 5) {
                name = "White Fox Boots";
                level = 62;
                health = 350;
                defense = 130;
                magicDefense = 90;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 6) {
                name = "Luna Boots";
                level = 72;
                health = 400;
                defense = 165;
                magicDefense = 110;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 7) {
                name = "Shadow Boots";
                level = 82;
                health = 550;
                defense = 220;
                magicDefense = 140;
                material = Material.CHAINMAIL_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Boots";
                level = 22;
                health = 70;
                defense = 20;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Boots";
                level = 32;
                health = 120;
                defense = 35;
                magicDefense = 45;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Boots";
                level = 42;
                health = 190;
                defense = 55;
                magicDefense = 75;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 4) {
                name = "Eleaza Boots";
                level = 52;
                health = 270;
                defense = 80;
                magicDefense = 100;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 5) {
                name = "Gaia Boots";
                level = 62;
                health = 350;
                defense = 100;
                magicDefense = 140;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 6) {
                name = "Holyshion Boots";
                level = 72;
                health = 450;
                defense = 125;
                magicDefense = 170;
                material = Material.DIAMOND_BOOTS;
            } else if (placementNumber == 7) {
                name = "Khraje Boots";
                level = 82;
                health = 600;
                defense = 170;
                magicDefense = 220;
                material = Material.DIAMOND_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Boots";
                level = 22;
                health = 90;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Boots";
                level = 32;
                health = 150;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Boots";
                level = 42;
                health = 240;
                defense = 70;
                magicDefense = 50;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 4) {
                name = "Fury Plate Boots";
                level = 52;
                health = 340;
                defense = 100;
                magicDefense = 60;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 5) {
                name = "Ceres Boots";
                level = 62;
                health = 450;
                defense = 130;
                magicDefense = 90;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 6) {
                name = "Zakar Boots";
                level = 72;
                health = 560;
                defense = 160;
                magicDefense = 110;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 7) {
                name = "Raxes Boots";
                level = 82;
                health = 740;
                defense = 220;
                magicDefense = 140;
                material = Material.IRON_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Boots";
                level = 22;
                health = 90;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Boots";
                level = 32;
                health = 150;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Boots";
                level = 42;
                health = 240;
                defense = 70;
                magicDefense = 50;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 4) {
                name = "Aqua Boots";
                level = 52;
                health = 340;
                defense = 100;
                magicDefense = 60;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 5) {
                name = "Water Spirit Boots";
                level = 62;
                health = 450;
                defense = 125;
                magicDefense = 90;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 6) {
                name = "Mera Boots";
                level = 72;
                health = 560;
                defense = 160;
                magicDefense = 110;
                material = Material.IRON_BOOTS;
            } else if (placementNumber == 7) {
                name = "Atlantean Boots";
                level = 82;
                health = 740;
                defense = 210;
                magicDefense = 140;
                material = Material.IRON_BOOTS;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Boots";
                level = 22;
                health = 70;
                defense = 30;
                magicDefense = 20;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Boots";
                level = 32;
                health = 110;
                defense = 40;
                magicDefense = 30;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Boots";
                level = 42;
                health = 180;
                defense = 70;
                magicDefense = 50;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 4) {
                name = "Speedy Boots";
                level = 52;
                health = 250;
                defense = 100;
                magicDefense = 70;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 5) {
                name = "White Wolf Boots";
                level = 62;
                health = 350;
                defense = 125;
                magicDefense = 90;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 6) {
                name = "Moottalis Boots";
                level = 72;
                health = 410;
                defense = 160;
                magicDefense = 110;
                material = Material.CHAINMAIL_BOOTS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Boots";
                level = 82;
                health = 560;
                defense = 210;
                magicDefense = 140;
                material = Material.CHAINMAIL_BOOTS;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor boots = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        if (isColorful) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) boots.getItemStack().getItemMeta();
            if (rpgClass.equals(RPGClass.ARCHER)) {
                leatherArmorMeta.setColor(Color.fromRGB(0, 188, 0));
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
            boots.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return boots.getItemStack();
    }
}
