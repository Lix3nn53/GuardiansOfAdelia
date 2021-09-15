package io.github.lix3nn53.guardiansofadelia.npc.merchant;


import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;

public enum MerchantType {
    WEAPONSMITH,
    ARMORSMITH,
    STORAGE_KEEPER,
    MAGIC_SHOP,
    TOOL_SHOP;

    public String getName() {
        switch (this) {
            case WEAPONSMITH:
                return ChatPalette.RED + "Weaponsmith";
            case ARMORSMITH:
                return ChatPalette.BLUE_LIGHT + "Armorsmith";
            case STORAGE_KEEPER:
                return ChatPalette.GOLD + "Storage Keeper";
            case MAGIC_SHOP:
                return ChatPalette.PURPLE_LIGHT + "Magical Item Shop";
            case TOOL_SHOP:
                return ChatPalette.YELLOW + "Item Shop";
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
