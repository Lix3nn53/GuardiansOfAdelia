package io.github.lix3nn53.guardiansofadelia.bungeelistener.products;

import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.MythicMob;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum PetSkin {
    ICE_CREAM,
    FOX_RED,
    FOX_SNOW,
    VEX,
    MINI_DRAGON,
    BEE;

    public ItemStack getItemStack() {
        String skinName = name();
        MythicMob mythicMob = MythicMobs.inst().getMobManager().getMythicMob(skinName);

        String displayName = mythicMob.getDisplayName().get();
        String itemName = displayName + ChatPalette.PURPLE_LIGHT + " Pet Skin";

        List<String> lore = new ArrayList<>();
        lore.add(ChatPalette.GRAY + "Premium");
        lore.add("");
        lore.add(ChatPalette.GOLD + "Usage: ");
        lore.add(ChatPalette.YELLOW + "1 - Equip the pet you want to apply this skin to.");
        lore.add(ChatPalette.YELLOW + "2 - Right click to your pet when you are holding this.");

        ItemStack itemStack = new ItemStack(Material.BLACK_DYE);
        PersistentDataContainerUtil.putString("petSkinCode", skinName, itemStack);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(1);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
