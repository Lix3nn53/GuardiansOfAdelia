package io.github.lix3nn53.guardiansofadelia.bungeelistener.products;

import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum HelmetSkin {
    WINGS_ANGEL,
    WINGS_DEMON,
    WINGS_DRAGON_DARK,
    WINGS_DRAGON_WHITE,
    CROWN;

    public static Material getHelmetMaterial() {
        return Material.IRON_SHOVEL;
    }

    public ItemStack getItemStack() {
        String s = this.name().toLowerCase().replaceAll("_", " ");
        String itemName = ChatPalette.PURPLE_LIGHT + s + " Helmet Skin";

        List<String> lore = new ArrayList<>();
        lore.add(ChatPalette.GRAY + "Premium");
        lore.add("");
        lore.add(ChatPalette.GOLD + "Usage: ");
        lore.add(ChatPalette.YELLOW + "1 - Right click while you are holding this item.");
        lore.add(ChatPalette.YELLOW + "2 - Place your helmet to empty slot.");
        lore.add(ChatPalette.YELLOW + "3 - Click green wool to confirm and apply skin.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);
        PersistentDataContainerUtil.putString("helmetSkinCode", this.toString(), itemStack);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(3);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public int getHelmetCustomModelData() {
        switch (this) {
            case WINGS_ANGEL:
                return 1;
            case WINGS_DEMON:
                return 2;
            case WINGS_DRAGON_DARK:
                return 3;
            case WINGS_DRAGON_WHITE:
                return 5;
            case CROWN:
                return 9;
        }

        return 1;
    }
}
