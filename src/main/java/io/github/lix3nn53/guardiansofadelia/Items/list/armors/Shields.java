package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.Shield;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Shields {

    public static ItemStack get(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, int minNumberOfStats) {
        String name = "Wooden Shield";
        Material material = Material.IRON_SWORD;
        int durability = 5;
        int level = 1;
        int health = 85;
        int defense = 8;
        int magicDefense = 3;
        int itemID = 5000;

        if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 2) {
                name = "Steel Shield";
                level = 10;
                durability = 8;
                health = 214;
                defense = 21;
                magicDefense = 8;
                itemID = 5001;
            } else if (placementNumber == 3) {
                name = "Bulwark";
                level = 20;
                durability = 4;
                health = 535;
                defense = 53;
                magicDefense = 21;
                itemID = 5002;
            } else if (placementNumber == 4) {
                name = "Battle Shield";
                level = 30;
                durability = 1;
                health = 850;
                defense = 85;
                magicDefense = 34;
                itemID = 5003;
            } else if (placementNumber == 5) {
                name = "Heroic Shield";
                level = 40;
                durability = 9;
                health = 1400;
                defense = 140;
                magicDefense = 55;
                itemID = 5004;
            } else if (placementNumber == 6) {
                name = "Shield of Doom";
                level = 50;
                durability = 2;
                health = 1920;
                defense = 192;
                magicDefense = 77;
                itemID = 5005;
            } else if (placementNumber == 7) {
                name = "Fire Spirit Shield";
                level = 60;
                durability = 3;
                health = 2600;
                defense = 250;
                magicDefense = 102;
                itemID = 5006;
            } else if (placementNumber == 8) {
                name = "Titanic Nature Shield";
                level = 70;
                durability = 7;
                health = 3200;
                defense = 320;
                magicDefense = 128;
                itemID = 5007;
            } else if (placementNumber == 9) {
                name = "Water Fairy Shield";
                level = 80;
                durability = 6;
                health = 4200;
                defense = 426;
                magicDefense = 171;
                itemID = 5008;
            } else if (placementNumber == 10) {
                name = "Hellas Shield";
                level = 90;
                durability = 10;
                health = 5550;
                defense = 562;
                magicDefense = 225;
                itemID = 5009;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Wooden Shield";
                level = 1;
                durability = 5;
                health = 70;
                defense = 5;
                magicDefense = 6;
                itemID = 5101;
            } else if (placementNumber == 2) {
                name = "Steel Shield";
                level = 10;
                durability = 8;
                health = 170;
                defense = 12;
                magicDefense = 17;
                itemID = 5102;
            } else if (placementNumber == 3) {
                name = "Bulwark";
                level = 20;
                durability = 11;
                health = 420;
                defense = 32;
                magicDefense = 42;
                itemID = 5103;
            } else if (placementNumber == 4) {
                name = "Battle Shield";
                level = 30;
                durability = 1;
                health = 680;
                defense = 50;
                magicDefense = 70;
                itemID = 5104;
            } else if (placementNumber == 5) {
                name = "Wind Shield";
                level = 40;
                durability = 15;
                health = 1100;
                defense = 84;
                magicDefense = 112;
                itemID = 5105;
            } else if (placementNumber == 6) {
                name = "Shield of Doom";
                level = 50;
                durability = 12;
                health = 1550;
                defense = 115;
                magicDefense = 153;
                itemID = 5106;
            } else if (placementNumber == 7) {
                name = "Volcano Shield";
                level = 60;
                durability = 3;
                health = 2050;
                defense = 153;
                magicDefense = 204;
                itemID = 5107;
            } else if (placementNumber == 8) {
                name = "Titanic Emerald Shield";
                level = 70;
                durability = 13;
                health = 2570;
                defense = 192;
                magicDefense = 258;
                itemID = 5108;
            } else if (placementNumber == 9) {
                name = "Lightbringer Shield";
                level = 80;
                durability = 6;
                health = 3400;
                defense = 255;
                magicDefense = 342;
                itemID = 5109;
            } else if (placementNumber == 10) {
                name = "Guardian Angel Shield";
                level = 90;
                durability = 14;
                health = 4500;
                defense = 336;
                magicDefense = 450;
                itemID = 5110;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final Shield shield = new Shield(name, tier, itemTag, material, durability, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats, itemID);
        return shield.getItemStack();
    }
}
