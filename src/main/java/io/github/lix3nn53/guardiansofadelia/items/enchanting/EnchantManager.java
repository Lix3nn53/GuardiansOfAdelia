package io.github.lix3nn53.guardiansofadelia.items.enchanting;

import io.github.lix3nn53.guardiansofadelia.bungeelistener.BoostPremiumManager;
import io.github.lix3nn53.guardiansofadelia.bungeelistener.products.BoostPremium;
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

    public static float getChance(Player player, int currentEnchantLevel) {
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return 1;
        }

        float chance = 0;
        if (currentEnchantLevel == 0) {
            chance = 0.84f;
        } else if (currentEnchantLevel == 1) {
            chance = 0.8f;
        } else if (currentEnchantLevel == 2) {
            chance = 0.76f;
        } else if (currentEnchantLevel == 3) {
            chance = 0.72f;
        } else if (currentEnchantLevel == 4) {
            chance = 0.67f;
        } else if (currentEnchantLevel == 5) {
            chance = 0.6f;
        } else if (currentEnchantLevel == 6) {
            chance = 0.53f;
        } else if (currentEnchantLevel == 7) {
            chance = 0.47f;
        } else if (currentEnchantLevel == 8) {
            chance = 0.4f;
        } else if (currentEnchantLevel == 9) {
            chance = 0.32f;
        } else if (currentEnchantLevel == 10) {
            chance = 0.26f;
        } else if (currentEnchantLevel == 11) {
            chance = 0.2f;
        }

        if (BoostPremiumManager.isBoostActive(BoostPremium.ENCHANT)) {
            chance = BoostPremium.ENCHANT.applyTo(chance);
        }

        return chance;
    }

    public static float getSellGuiMultiplier(int enchantLevel) {
        if (enchantLevel == 1) {
            return 1;
        } else if (enchantLevel == 2) {
            return 1;
        } else if (enchantLevel == 3) {
            return 1;
        } else if (enchantLevel == 4) {
            return 1.1f;
        } else if (enchantLevel == 5) {
            return 1.2f;
        } else if (enchantLevel == 6) {
            return 1.3f;
        } else if (enchantLevel == 7) {
            return 1.4f;
        } else if (enchantLevel == 8) {
            return 1.6f;
        } else if (enchantLevel == 9) {
            return 1.8f;
        } else if (enchantLevel == 10) {
            return 2;
        } else if (enchantLevel == 11) {
            return 2.25f;
        } else if (enchantLevel == 12) {
            return 2.5f;
        }

        return 1;
    }
}
