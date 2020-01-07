package io.github.lix3nn53.guardiansofadelia.Items.enchanting;

import io.github.lix3nn53.guardiansofadelia.socket.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.socket.products.BoostPremium;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantManager {

    public static int getEnchantLevel(ItemStack itemStack) {
        ItemMeta im = itemStack.getItemMeta();
        if (im.getDisplayName().contains("+12")) {
            return 12;
        } else if (im.getDisplayName().contains("+11")) {
            return 11;
        } else if (im.getDisplayName().contains("+10")) {
            return 10;
        } else if (im.getDisplayName().contains("+9")) {
            return 9;
        } else if (im.getDisplayName().contains("+8")) {
            return 8;
        } else if (im.getDisplayName().contains("+7")) {
            return 7;
        } else if (im.getDisplayName().contains("+6")) {
            return 6;
        } else if (im.getDisplayName().contains("+5")) {
            return 5;
        } else if (im.getDisplayName().contains("+4")) {
            return 4;
        } else if (im.getDisplayName().contains("+3")) {
            return 3;
        } else if (im.getDisplayName().contains("+2")) {
            return 2;
        } else if (im.getDisplayName().contains("+1")) {
            return 1;
        }
        return 0;
    }

    public static int getRequiredEnchantStoneLevel(int currentEnchantLevel) {
        if (currentEnchantLevel < 3) {
            return 1;
        } else if (currentEnchantLevel < 6) {
            return 2;
        } else if (currentEnchantLevel < 9) {
            return 3;
        }
        return 4;
    }

    public static double getChance(Player player, int currentEnchantLevel) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return 1;
        }

        double chance = 0;
        if (currentEnchantLevel == 0) {
            chance = 0.84;
        } else if (currentEnchantLevel == 1) {
            chance = 0.8;
        } else if (currentEnchantLevel == 2) {
            chance = 0.76;
        } else if (currentEnchantLevel == 3) {
            chance = 0.72;
        } else if (currentEnchantLevel == 4) {
            chance = 0.67;
        } else if (currentEnchantLevel == 5) {
            chance = 0.6;
        } else if (currentEnchantLevel == 6) {
            chance = 0.53;
        } else if (currentEnchantLevel == 7) {
            chance = 0.47;
        } else if (currentEnchantLevel == 8) {
            chance = 0.4;
        } else if (currentEnchantLevel == 9) {
            chance = 0.32;
        } else if (currentEnchantLevel == 10) {
            chance = 0.26;
        } else if (currentEnchantLevel == 11) {
            chance = 0.2;
        }

        if (BoostPremiumManager.isBoostActive(BoostPremium.ENCHANT)) {
            chance += 0.15;
        }

        return chance;
    }
}
