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
        int health = 36;
        int defense = 16;
        int magicDefense = 12;
        int itemID = 4300;
        boolean isColorful = false;

        if (rpgClass.equals(RPGClass.NO_CLASS)) {
            if (placementNumber == 2) {
                name = "Hide Leggings";
                level = 16;
                health = 72;
                defense = 24;
                magicDefense = 18;
                itemID = 4301;
            }
        } else if (rpgClass.equals(RPGClass.ARCHER)) {
            if (placementNumber == 1) {
                name = "Leaf Leggings";
                level = 26;
                health = 46;
                defense = 14;
                magicDefense = 24;
                itemID = 4002;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Leggings";
                level = 36;
                health = 68;
                defense = 20;
                magicDefense = 32;
                itemID = 4003;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Leggings";
                level = 46;
                health = 92;
                defense = 24;
                magicDefense = 42;
                itemID = 4004;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Speedy Leggings";
                level = 56;
                health = 120;
                defense = 32;
                magicDefense = 53;
                itemID = 4005;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Wolf Leggings";
                level = 66;
                health = 150;
                defense = 40;
                magicDefense = 68;
                itemID = 4006;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Moottalis Leggings";
                level = 76;
                health = 190;
                defense = 48;
                magicDefense = 82;
                itemID = 4007;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 270;
                defense = 81;
                magicDefense = 142;
                itemID = 4008;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Leggings";
                level = 26;
                health = 78;
                defense = 34;
                magicDefense = 12;
                itemID = 4102;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Leggings";
                level = 36;
                health = 112;
                defense = 50;
                magicDefense = 18;
                itemID = 4103;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Leggings";
                level = 46;
                health = 158;
                defense = 64;
                magicDefense = 21;
                itemID = 4104;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Leggings";
                level = 56;
                health = 202;
                defense = 76;
                magicDefense = 26;
                itemID = 4105;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Elisa Leggings";
                level = 66;
                health = 246;
                defense = 100;
                magicDefense = 33;
                itemID = 4106;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Gloria Leggings";
                level = 76;
                health = 315;
                defense = 124;
                magicDefense = 42;
                itemID = 4107;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 450;
                defense = 206;
                magicDefense = 70;
                itemID = 4108;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Leggings";
                level = 26;
                health = 40;
                defense = 14;
                magicDefense = 24;
                itemID = 4202;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Leggings";
                level = 36;
                health = 58;
                defense = 20;
                magicDefense = 32;
                itemID = 4203;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Leggings";
                level = 46;
                health = 82;
                defense = 24;
                magicDefense = 42;
                itemID = 4204;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Wizardy Leggings";
                level = 56;
                health = 104;
                defense = 32;
                magicDefense = 53;
                itemID = 4205;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Xhar Leggings";
                level = 66;
                health = 124;
                defense = 40;
                magicDefense = 68;
                itemID = 4206;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Chamor Leggings";
                level = 76;
                health = 158;
                defense = 48;
                magicDefense = 82;
                itemID = 4207;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Neferti Leggings";
                level = 86;
                health = 226;
                defense = 81;
                magicDefense = 142;
                itemID = 4208;
                material = Material.GOLDEN_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            if (placementNumber == 1) {
                name = "Sunset Leggings";
                level = 26;
                health = 46;
                defense = 20;
                magicDefense = 20;
                itemID = 4302;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Leggings";
                level = 36;
                health = 68;
                defense = 28;
                magicDefense = 28;
                itemID = 4303;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Leggings";
                level = 46;
                health = 92;
                defense = 36;
                magicDefense = 36;
                itemID = 4304;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Lupy Leggings";
                level = 56;
                health = 120;
                defense = 44;
                magicDefense = 44;
                itemID = 4305;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Fox Leggings";
                level = 66;
                health = 150;
                defense = 54;
                magicDefense = 54;
                itemID = 4306;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Luna Leggings";
                level = 76;
                health = 190;
                defense = 70;
                magicDefense = 70;
                itemID = 4307;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Shadow Leggings";
                level = 86;
                health = 270;
                defense = 118;
                magicDefense = 118;
                itemID = 4308;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Leggings";
                level = 26;
                health = 55;
                defense = 26;
                magicDefense = 26;
                itemID = 4402;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Leggings";
                level = 36;
                health = 80;
                defense = 36;
                magicDefense = 36;
                itemID = 4403;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Leggings";
                level = 46;
                health = 111;
                defense = 47;
                magicDefense = 47;
                itemID = 4404;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Eleaza Leggings";
                level = 56;
                health = 142;
                defense = 58;
                magicDefense = 58;
                itemID = 4405;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Gaia Leggings";
                level = 66;
                health = 174;
                defense = 71;
                magicDefense = 71;
                itemID = 4406;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Holyshion Leggings";
                level = 76;
                health = 220;
                defense = 91;
                magicDefense = 91;
                itemID = 4407;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Khraje Leggings";
                level = 86;
                health = 315;
                defense = 153;
                magicDefense = 153;
                itemID = 4408;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Leggings";
                level = 26;
                health = 62;
                defense = 24;
                magicDefense = 5;
                itemID = 4502;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Leggings";
                level = 36;
                health = 90;
                defense = 32;
                magicDefense = 8;
                itemID = 4503;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Leggings";
                level = 46;
                health = 126;
                defense = 42;
                magicDefense = 10;
                itemID = 4504;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Fury Plate Leggings";
                level = 56;
                health = 162;
                defense = 52;
                magicDefense = 14;
                itemID = 4505;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Ceres Leggings";
                level = 66;
                health = 198;
                defense = 68;
                magicDefense = 17;
                itemID = 4506;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Zakar Leggings";
                level = 76;
                health = 253;
                defense = 82;
                magicDefense = 21;
                itemID = 4507;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Raxes Leggings";
                level = 86;
                health = 360;
                defense = 142;
                magicDefense = 30;
                itemID = 4508;
                material = Material.IRON_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Leggings";
                level = 26;
                health = 112;
                defense = 38;
                magicDefense = 26;
                itemID = 4602;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Leggings";
                level = 36;
                health = 160;
                defense = 53;
                magicDefense = 36;
                itemID = 4603;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Leggings";
                level = 46;
                health = 220;
                defense = 64;
                magicDefense = 45;
                itemID = 4604;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Aqua Leggings";
                level = 56;
                health = 284;
                defense = 82;
                magicDefense = 58;
                itemID = 4605;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Water Spirit Leggings";
                level = 66;
                health = 348;
                defense = 104;
                magicDefense = 72;
                itemID = 4606;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Mera Leggings";
                level = 76;
                health = 440;
                defense = 126;
                magicDefense = 88;
                itemID = 4607;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Atlantean Leggings";
                level = 86;
                health = 630;
                defense = 214;
                magicDefense = 150;
                itemID = 4608;
                material = Material.IRON_LEGGINGS;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final GearArmor leggings = new GearArmor(name, tier, itemTag, material, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats, itemID);
        if (isColorful) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leggings.getItemStack().getItemMeta();
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
            leggings.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return leggings.getItemStack();
    }
}
