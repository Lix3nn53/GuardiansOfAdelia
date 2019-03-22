package io.github.lix3nn53.guardiansofadelia.Items.list.armors;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ItemTier;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.Shield;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Shields {

    public static ItemStack get(RPGClass rpgClass, int placementNumber, ItemTier tier, String itemTag, double healthBonus, int minStatValue,
                                int maxStatValue, double chanceToGetEachStat, int minNumberOfStats) {
        String name = "Wooden Shield";
        Material material = Material.IRON_SWORD;
        int durability = 5;
        int level = 1;
        int health = 15;
        int defense = 15;
        int magicDefense = 5;
        int itemID = 5000;

        if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 2) {
                name = "Steel Shield";
                level = 10;
                durability = 8;
                health = 30;
                defense = 30;
                magicDefense = 10;
                itemID = 5001;
            } else if (placementNumber == 3) {
                name = "Bulwark";
                level = 20;
                durability = 4;
                health = 65;
                defense = 45;
                magicDefense = 15;
                itemID = 5002;
            } else if (placementNumber == 4) {
                name = "Battle Shield";
                level = 30;
                durability = 1;
                health = 100;
                defense = 70;
                magicDefense = 20;
                itemID = 5003;
            } else if (placementNumber == 5) {
                name = "Heroic Shield";
                level = 40;
                durability = 9;
                health = 150;
                defense = 100;
                magicDefense = 30;
                itemID = 5004;
            } else if (placementNumber == 6) {
                name = "Shield of Doom";
                level = 50;
                durability = 2;
                health = 210;
                defense = 130;
                magicDefense = 40;
                itemID = 5005;
            } else if (placementNumber == 7) {
                name = "Fire Spirit Shield";
                level = 60;
                durability = 3;
                health = 270;
                defense = 160;
                magicDefense = 50;
                itemID = 5006;
            } else if (placementNumber == 8) {
                name = "Titanic Nature Shield";
                level = 70;
                durability = 7;
                health = 320;
                defense = 200;
                magicDefense = 70;
                itemID = 5007;
            } else if (placementNumber == 9) {
                name = "Water Fairy Shield";
                level = 80;
                durability = 6;
                health = 420;
                defense = 260;
                magicDefense = 90;
                itemID = 5008;
            } else if (placementNumber == 10) {
                name = "Hellas Shield";
                level = 90;
                durability = 10;
                health = 600;
                defense = 450;
                magicDefense = 150;
                itemID = 5009;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                name = "Wooden Shield";
                level = 1;
                durability = 5;
                health = 15;
                defense = 12;
                magicDefense = 12;
                itemID = 5101;
            } else if (placementNumber == 2) {
                name = "Steel Shield";
                level = 10;
                durability = 8;
                health = 25;
                defense = 22;
                magicDefense = 22;
                itemID = 5102;
            } else if (placementNumber == 3) {
                name = "Bulwark";
                level = 20;
                durability = 11;
                health = 45;
                defense = 34;
                magicDefense = 34;
                itemID = 5103;
            } else if (placementNumber == 4) {
                name = "Battle Shield";
                level = 30;
                durability = 1;
                health = 74;
                defense = 54;
                magicDefense = 54;
                itemID = 5104;
            } else if (placementNumber == 5) {
                name = "Wind Shield";
                level = 40;
                durability = 15;
                health = 100;
                defense = 81;
                magicDefense = 81;
                itemID = 5105;
            } else if (placementNumber == 6) {
                name = "Shield of Doom";
                level = 50;
                durability = 12;
                health = 150;
                defense = 100;
                magicDefense = 100;
                itemID = 5106;
            } else if (placementNumber == 7) {
                name = "Volcano Shield";
                level = 60;
                durability = 3;
                health = 200;
                defense = 120;
                magicDefense = 120;
                itemID = 5107;
            } else if (placementNumber == 8) {
                name = "Titanic Emerald Shield";
                level = 70;
                durability = 13;
                health = 240;
                defense = 150;
                magicDefense = 150;
                itemID = 5108;
            } else if (placementNumber == 9) {
                name = "Lightbringer Shield";
                level = 80;
                durability = 6;
                health = 300;
                defense = 190;
                magicDefense = 190;
                itemID = 5109;
            } else if (placementNumber == 10) {
                name = "Guardian Angel Shield";
                level = 90;
                durability = 14;
                health = 420;
                defense = 330;
                magicDefense = 330;
                itemID = 5110;
            }
        }

        health = (int) ((health * healthBonus) + 0.5);

        final Shield shield = new Shield(name, tier, itemTag, material, durability, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, chanceToGetEachStat, minNumberOfStats, itemID);
        return shield.getItemStack();
    }
}
