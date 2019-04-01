package io.github.lix3nn53.guardiansofadelia.npc.merchant;

import org.bukkit.ChatColor;

public enum MerchantType {
    BLACKSMITH,
    STORAGE_KEEPER,
    MAGIC_SHOP,
    TOOL_SHOP,
    PET_SHOP;

    public String getName() {
        if (this.equals(MerchantType.BLACKSMITH)) {
            return ChatColor.AQUA + "Blacksmith";
        } else if (this.equals(MerchantType.STORAGE_KEEPER)) {
            return ChatColor.GOLD + "Storage Keeper";
        } else if (this.equals(MerchantType.MAGIC_SHOP)) {
            return ChatColor.LIGHT_PURPLE + "Magical Item Shop";
        } else if (this.equals(MerchantType.TOOL_SHOP)) {
            return ChatColor.YELLOW + "Item Shop";
        } else if (this.equals(MerchantType.PET_SHOP)) {
            return ChatColor.YELLOW + "Pet Shop";
        }
        return "???";
    }

    public boolean canCoinConvert() {
        if (this.equals(MerchantType.BLACKSMITH)) {
            return true;
        } else if (this.equals(MerchantType.STORAGE_KEEPER)) {
            return true;
        } else if (this.equals(MerchantType.MAGIC_SHOP)) {
            return true;
        } else if (this.equals(MerchantType.TOOL_SHOP)) {
            return true;
        } else return this.equals(MerchantType.PET_SHOP);
    }

    public boolean canSell() {
        if (this.equals(MerchantType.BLACKSMITH)) {
            return true;
        } else if (this.equals(MerchantType.STORAGE_KEEPER)) {
            return false;
        } else if (this.equals(MerchantType.MAGIC_SHOP)) {
            return true;
        } else if (this.equals(MerchantType.TOOL_SHOP)) {
            return true;
        } else return this.equals(MerchantType.PET_SHOP);
    }
}
