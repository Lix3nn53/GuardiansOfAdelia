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
        int customModelDataId = 10000001;
        int level = 1;
        int health = 20;
        int defense = 8;
        int magicDefense = 3;

        if (rpgClass.equals(RPGClass.KNIGHT)) {
            if (placementNumber == 2) {
                health = 40;
                defense = 21;
                magicDefense = 8;
            } else if (placementNumber == 3) {
                health = 110;
                defense = 53;
                magicDefense = 21;
            } else if (placementNumber == 4) {
                health = 170;
                defense = 85;
                magicDefense = 34;
            } else if (placementNumber == 5) {
                health = 280;
                defense = 140;
                magicDefense = 55;
            } else if (placementNumber == 6) {
                health = 380;
                defense = 192;
                magicDefense = 77;
            } else if (placementNumber == 7) {
                health = 520;
                defense = 250;
                magicDefense = 102;
            } else if (placementNumber == 8) {
                health = 640;
                defense = 320;
                magicDefense = 128;
            } else if (placementNumber == 9) {
                health = 840;
                defense = 426;
                magicDefense = 171;
            } else if (placementNumber == 10) {
                health = 1100;
                defense = 562;
                magicDefense = 225;
            }
        } else if (rpgClass.equals(RPGClass.PALADIN)) {
            if (placementNumber == 1) {
                health = 15;
                defense = 5;
                magicDefense = 6;
            } else if (placementNumber == 2) {
                health = 30;
                defense = 12;
                magicDefense = 17;
            } else if (placementNumber == 3) {
                health = 80;
                defense = 32;
                magicDefense = 42;
            } else if (placementNumber == 4) {
                health = 130;
                defense = 50;
                magicDefense = 70;
            } else if (placementNumber == 5) {
                health = 220;
                defense = 84;
                magicDefense = 112;
            } else if (placementNumber == 6) {
                health = 310;
                defense = 115;
                magicDefense = 153;
            } else if (placementNumber == 7) {
                health = 410;
                defense = 153;
                magicDefense = 204;
            } else if (placementNumber == 8) {
                health = 510;
                defense = 192;
                magicDefense = 258;
            } else if (placementNumber == 9) {
                health = 680;
                defense = 255;
                magicDefense = 342;
            } else if (placementNumber == 10) {
                health = 900;
                defense = 336;
                magicDefense = 450;
            }
        }

        if (placementNumber == 2) {
            name = "Steel Shield";
            level = 10;
            customModelDataId = 10000002;
        } else if (placementNumber == 3) {
            name = "Bulwark";
            level = 20;
            customModelDataId = 10000003;
        } else if (placementNumber == 4) {
            name = "Battle Shield";
            level = 30;
            customModelDataId = 10000004;
        } else if (placementNumber == 5) {
            name = "Heroic Shield";
            level = 40;
            customModelDataId = 10000005;
        } else if (placementNumber == 6) {
            name = "Shield of Doom";
            level = 50;
            customModelDataId = 10000006;
        } else if (placementNumber == 7) {
            name = "Fire Spirit Shield";
            level = 60;
            customModelDataId = 10000008;
        } else if (placementNumber == 8) {
            name = "Titanic Nature Shield";
            level = 70;
            customModelDataId = 10000010;
        } else if (placementNumber == 9) {
            name = "Water Fairy Shield";
            level = 80;
            customModelDataId = 10000012;
        } else if (placementNumber == 10) {
            name = "Hellas Shield";
            level = 90;
            customModelDataId = 10000014;
        }

        health = (int) ((health * healthBonus) + 0.5);

        final Shield shield = new Shield(name, tier, itemTag, material, customModelDataId, level,
                rpgClass, health,
                defense, magicDefense, minStatValue, maxStatValue, minNumberOfStats);
        return shield.getItemStack();
    }
}
