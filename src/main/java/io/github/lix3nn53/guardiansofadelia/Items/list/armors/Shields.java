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
        Material material = Material.SHIELD;
        int customModelDataId = 1;
        int level = 1;
        int health = 24;
        int defense = 10;
        int magicDefense = 5;

        if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 2) {
                health = 56;
                defense = 20;
                magicDefense = 10;
            } else if (placementNumber == 3) {
                health = 140;
                defense = 50;
                magicDefense = 20;
            } else if (placementNumber == 4) {
                health = 220;
                defense = 90;
                magicDefense = 40;
            } else if (placementNumber == 5) {
                health = 360;
                defense = 140;
                magicDefense = 60;
            } else if (placementNumber == 6) {
                health = 490;
                defense = 190;
                magicDefense = 80;
            } else if (placementNumber == 7) {
                health = 670;
                defense = 250;
                magicDefense = 100;
            } else if (placementNumber == 8) {
                health = 800;
                defense = 320;
                magicDefense = 120;
            } else if (placementNumber == 9) {
                health = 1100;
                defense = 420;
                magicDefense = 170;
            } else if (placementNumber == 10) {
                health = 1450;
                defense = 550;
                magicDefense = 220;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                health = 20;
                defense = 5;
                magicDefense = 10;
            } else if (placementNumber == 2) {
                health = 40;
                defense = 15;
                magicDefense = 20;
            } else if (placementNumber == 3) {
                health = 100;
                defense = 30;
                magicDefense = 40;
            } else if (placementNumber == 4) {
                health = 170;
                defense = 50;
                magicDefense = 70;
            } else if (placementNumber == 5) {
                health = 280;
                defense = 90;
                magicDefense = 110;
            } else if (placementNumber == 6) {
                health = 400;
                defense = 120;
                magicDefense = 150;
            } else if (placementNumber == 7) {
                health = 520;
                defense = 160;
                magicDefense = 200;
            } else if (placementNumber == 8) {
                health = 650;
                defense = 200;
                magicDefense = 260;
            } else if (placementNumber == 9) {
                health = 880;
                defense = 270;
                magicDefense = 340;
            } else if (placementNumber == 10) {
                health = 1200;
                defense = 340;
                magicDefense = 450;
            }
        }

        if (placementNumber == 2) {
            name = "Steel Shield";
            level = 10;
            customModelDataId = 2;
        } else if (placementNumber == 3) {
            name = "Bulwark";
            level = 20;
            customModelDataId = 3;
        } else if (placementNumber == 4) {
            name = "Battle Shield";
            level = 30;
            customModelDataId = 4;
        } else if (placementNumber == 5) {
            name = "Heroic Shield";
            level = 40;
            customModelDataId = 5;
        } else if (placementNumber == 6) {
            name = "Shield of Doom";
            level = 50;
            customModelDataId = 6;
        } else if (placementNumber == 7) {
            name = "Fire Spirit Shield";
            level = 60;
            customModelDataId = 8;
        } else if (placementNumber == 8) {
            name = "Titanic Nature Shield";
            level = 70;
            customModelDataId = 10;
        } else if (placementNumber == 9) {
            name = "Water Fairy Shield";
            level = 80;
            customModelDataId = 12;
        } else if (placementNumber == 10) {
            name = "Hellas Shield";
            level = 90;
            customModelDataId = 14;
        }

        health = (int) ((health * healthBonus) + 0.5);

        final Shield shield = new Shield(name, tier, itemTag, material, customModelDataId, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        return shield.getItemStack();
    }
}
