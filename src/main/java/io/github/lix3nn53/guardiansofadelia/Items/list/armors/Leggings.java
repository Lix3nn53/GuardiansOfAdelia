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
                health = 270;
                defense = 40;
                magicDefense = 26;
                itemID = 4002;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Leggings";
                level = 36;
                health = 440;
                defense = 64;
                magicDefense = 42;
                itemID = 4003;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Leggings";
                level = 46;
                health = 720;
                defense = 105;
                magicDefense = 70;
                itemID = 4004;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Speedy Leggings";
                level = 56;
                health = 980;
                defense = 150;
                magicDefense = 100;
                itemID = 4005;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Wolf Leggings";
                level = 66;
                health = 1340;
                defense = 200;
                magicDefense = 130;
                itemID = 4006;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Moottalis Leggings";
                level = 76;
                health = 1640;
                defense = 245;
                magicDefense = 162;
                itemID = 4007;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 2200;
                defense = 325;
                magicDefense = 218;
                itemID = 4008;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 1) {
                name = "BlueSky Leggings";
                level = 26;
                health = 535;
                defense = 53;
                magicDefense = 21;
                itemID = 4102;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Sky Fairy Leggings";
                level = 36;
                health = 850;
                defense = 85;
                magicDefense = 34;
                itemID = 4103;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Half Plate Leggings";
                level = 46;
                health = 1400;
                defense = 140;
                magicDefense = 55;
                itemID = 4104;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Mighty Plate Leggings";
                level = 56;
                health = 1920;
                defense = 192;
                magicDefense = 77;
                itemID = 4105;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Elisa Leggings";
                level = 66;
                health = 2600;
                defense = 250;
                magicDefense = 102;
                itemID = 4106;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Gloria Leggings";
                level = 76;
                health = 3200;
                defense = 320;
                magicDefense = 128;
                itemID = 4107;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 4200;
                defense = 426;
                magicDefense = 171;
                itemID = 4108;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MAGE)) {
            if (placementNumber == 1) {
                name = "Silk Leggings";
                level = 26;
                health = 270;
                defense = 28;
                magicDefense = 28;
                itemID = 4202;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Eli Leggings";
                level = 36;
                health = 430;
                defense = 45;
                magicDefense = 45;
                itemID = 4203;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Wisdom Leggings";
                level = 46;
                health = 720;
                defense = 70;
                magicDefense = 70;
                itemID = 4204;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Wizardy Leggings";
                level = 56;
                health = 980;
                defense = 100;
                magicDefense = 100;
                itemID = 4205;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Xhar Leggings";
                level = 66;
                health = 1340;
                defense = 128;
                magicDefense = 128;
                itemID = 4206;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Chamor Leggings";
                level = 76;
                health = 1650;
                defense = 164;
                magicDefense = 164;
                itemID = 4207;
                material = Material.GOLDEN_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Neferti Leggings";
                level = 86;
                health = 2200;
                defense = 216;
                magicDefense = 216;
                itemID = 4208;
                material = Material.GOLDEN_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.NINJA)) {
            if (placementNumber == 1) {
                name = "Sunset Leggings";
                level = 26;
                health = 410;
                defense = 40;
                magicDefense = 27;
                itemID = 4302;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Dark Wind Leggings";
                level = 36;
                health = 650;
                defense = 64;
                magicDefense = 44;
                itemID = 4303;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Terra Leggings";
                level = 46;
                health = 1050;
                defense = 105;
                magicDefense = 70;
                itemID = 4304;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Lupy Leggings";
                level = 56;
                health = 1470;
                defense = 150;
                magicDefense = 98;
                itemID = 4305;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Fox Leggings";
                level = 66;
                health = 1960;
                defense = 200;
                magicDefense = 130;
                itemID = 4306;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Luna Leggings";
                level = 76;
                health = 2450;
                defense = 245;
                magicDefense = 164;
                itemID = 4307;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Shadow Leggings";
                level = 86;
                health = 3250;
                defense = 325;
                magicDefense = 218;
                itemID = 4308;
                material = Material.CHAINMAIL_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Sunrise Leggings";
                level = 26;
                health = 420;
                defense = 32;
                magicDefense = 42;
                itemID = 4402;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "August Leggings";
                level = 36;
                health = 680;
                defense = 51;
                magicDefense = 68;
                itemID = 4403;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Rhythm Leggings";
                level = 46;
                health = 1100;
                defense = 84;
                magicDefense = 112;
                itemID = 4404;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Eleaza Leggings";
                level = 56;
                health = 1540;
                defense = 115;
                magicDefense = 154;
                itemID = 4405;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Gaia Leggings";
                level = 66;
                health = 2050;
                defense = 155;
                magicDefense = 205;
                itemID = 4406;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Holyshion Leggings";
                level = 76;
                health = 2580;
                defense = 192;
                magicDefense = 258;
                itemID = 4407;
                material = Material.DIAMOND_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Khraje Leggings";
                level = 86;
                health = 3400;
                defense = 255;
                magicDefense = 342;
                itemID = 4408;
                material = Material.DIAMOND_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.WARRIOR)) {
            if (placementNumber == 1) {
                name = "Magma Leggings";
                level = 26;
                health = 550;
                defense = 40;
                magicDefense = 28;
                itemID = 4502;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Fire Spirit Leggings";
                level = 36;
                health = 870;
                defense = 65;
                magicDefense = 44;
                itemID = 4503;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Light Plate Leggings";
                level = 46;
                health = 1420;
                defense = 108;
                magicDefense = 70;
                itemID = 4504;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Fury Plate Leggings";
                level = 56;
                health = 1950;
                defense = 148;
                magicDefense = 98;
                itemID = 4505;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Ceres Leggings";
                level = 66;
                health = 2620;
                defense = 196;
                magicDefense = 130;
                itemID = 4506;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Zakar Leggings";
                level = 76;
                health = 3270;
                defense = 245;
                magicDefense = 164;
                itemID = 4507;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Raxes Leggings";
                level = 86;
                health = 4380;
                defense = 328;
                magicDefense = 216;
                itemID = 4508;
                material = Material.IRON_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.MONK)) {
            if (placementNumber == 1) {
                name = "Seaweed Leggings";
                level = 26;
                health = 550;
                defense = 40;
                magicDefense = 28;
                itemID = 4602;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Kelp Leggings";
                level = 36;
                health = 870;
                defense = 65;
                magicDefense = 44;
                itemID = 4603;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Coral Leggings";
                level = 46;
                health = 1420;
                defense = 108;
                magicDefense = 70;
                itemID = 4604;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Aqua Leggings";
                level = 56;
                health = 1950;
                defense = 148;
                magicDefense = 98;
                itemID = 4605;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "Water Spirit Leggings";
                level = 66;
                health = 2620;
                defense = 196;
                magicDefense = 130;
                itemID = 4606;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Mera Leggings";
                level = 76;
                health = 3270;
                defense = 245;
                magicDefense = 164;
                itemID = 4607;
                material = Material.IRON_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Atlantean Leggings";
                level = 86;
                health = 4380;
                defense = 328;
                magicDefense = 216;
                itemID = 4608;
                material = Material.IRON_LEGGINGS;
            }
        } else if (rpgClass.equals(RPGClass.HUNTER)) {
            if (placementNumber == 1) {
                name = "Leaf Leggings";
                level = 26;
                health = 410;
                defense = 40;
                magicDefense = 26;
                itemID = 4702;
                isColorful = true;
            } else if (placementNumber == 2) {
                name = "Forest Fairy Leggings";
                level = 36;
                health = 650;
                defense = 64;
                magicDefense = 42;
                itemID = 4703;
                isColorful = true;
            } else if (placementNumber == 3) {
                name = "Hunter Leggings";
                level = 46;
                health = 1050;
                defense = 105;
                magicDefense = 70;
                itemID = 4704;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 4) {
                name = "Speedy Leggings";
                level = 56;
                health = 1470;
                defense = 150;
                magicDefense = 100;
                itemID = 4705;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 5) {
                name = "White Wolf Leggings";
                level = 66;
                health = 1960;
                defense = 200;
                magicDefense = 130;
                itemID = 4706;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 6) {
                name = "Moottalis Leggings";
                level = 76;
                health = 2450;
                defense = 245;
                magicDefense = 162;
                itemID = 4707;
                material = Material.CHAINMAIL_LEGGINGS;
            } else if (placementNumber == 7) {
                name = "Darkshuter Leggings";
                level = 86;
                health = 3250;
                defense = 325;
                magicDefense = 218;
                itemID = 4708;
                material = Material.CHAINMAIL_LEGGINGS;
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
            } else if (rpgClass.equals(RPGClass.HUNTER)) {
                leatherArmorMeta.setColor(Color.fromRGB(35, 140, 35));
            }
            leggings.getItemStack().setItemMeta(leatherArmorMeta);
        }
        return leggings.getItemStack();
    }
}
