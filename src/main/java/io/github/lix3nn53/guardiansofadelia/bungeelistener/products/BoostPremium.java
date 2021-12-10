package io.github.lix3nn53.guardiansofadelia.bungeelistener.products;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum BoostPremium {
    EXPERIENCE,
    LOOT,
    ENCHANT,
    GATHER;

    public ItemStack getItemStack(int amount) {
        String input = this.name();
        String s = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        String itemName = getChatPalette() + s + " Boost";

        List<String> lore = new ArrayList<>();
        lore.add(ChatPalette.GRAY + "Boost");
        lore.add("");
        lore.add(ChatPalette.GOLD + "Usage: ");
        lore.add(ChatPalette.YELLOW + "1 - Right click while you are holding this item.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);
        PersistentDataContainerUtil.putString("boostCode", this.name(), itemStack);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(getCustomModelData());
        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        return itemStack;
    }

    public ChatPalette getChatPalette() {
        switch (this) {
            case EXPERIENCE:
                return ChatPalette.PURPLE_LIGHT;
            case LOOT:
                return ChatPalette.YELLOW;
            case ENCHANT:
                return ChatPalette.BLUE_LIGHT;
            case GATHER:
                return ChatPalette.GREEN_DARK;
        }

        return ChatPalette.GRAY;
    }

    public int getCustomModelData() {
        switch (this) {
            case EXPERIENCE:
                return 5;
            case LOOT:
                return 6;
            case ENCHANT:
                return 7;
            case GATHER:
                return 8;
        }

        return 0;
    }

    public float applyTo(float value) {
        switch (this) {
            case EXPERIENCE:
            case LOOT:
                return (float) (value * 1.2); // +20% bonus value
            case ENCHANT:
                return (float) (value + 0.15); // +15% bonus chance
            case GATHER:
                return value / 2; // +50 faster gathering
        }

        return value;
    }
}
