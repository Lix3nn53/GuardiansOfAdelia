package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import org.bukkit.ChatColor;

public enum MerchantType {
    WEAPONSMITH,
    ARMORSMITH,
    STORAGE_KEEPER,
    MAGIC_SHOP,
    TOOL_SHOP;

    public String getName() {
        switch (this) {
            case WEAPONSMITH:
                return ChatColor.RED + "Weaponsmith";
            case ARMORSMITH:
                return ChatColor.AQUA + "Armorsmith";
            case STORAGE_KEEPER:
                return ChatColor.GOLD + "Storage Keeper";
            case MAGIC_SHOP:
                return ChatColor.LIGHT_PURPLE + "Magical Item Shop";
            case TOOL_SHOP:
                return ChatColor.YELLOW + "Item Shop";
        }

        return "???";
    }

    public boolean canCoinConvert() {
        switch (this) {
            case WEAPONSMITH:
            case TOOL_SHOP:
            case MAGIC_SHOP:
            case STORAGE_KEEPER:
            case ARMORSMITH:
                return true;
        }

        return false;
    }

    public boolean canSell() {
        switch (this) {
            case WEAPONSMITH:
            case TOOL_SHOP:
            case MAGIC_SHOP:
            case STORAGE_KEEPER:
            case ARMORSMITH:
                return true;
        }

        return false;
    }
}
