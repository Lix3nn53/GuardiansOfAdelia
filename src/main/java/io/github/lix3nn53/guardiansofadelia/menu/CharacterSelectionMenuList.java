package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class CharacterSelectionMenuList {

    public static GuiGeneric characterSelectionMenu(int charNo) {
        GuiGeneric guiGeneric = new GuiGeneric(36, ChatPalette.GRAY_DARK + "Character " + charNo + " Selection", 0);

        ItemStack lastLocation = new ItemStack(Material.MAGENTA_WOOL);
        ItemMeta itemMeta = lastLocation.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Teleport to your last location");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        lastLocation.setItemMeta(itemMeta);
        guiGeneric.setItem(11, lastLocation);

        int index = 13;
        HashMap<Integer, Town> towns = TownManager.getTowns();
        for (int key : towns.keySet()) {
            Town town = towns.get(key);
            ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_WOOL);
            itemMeta = itemStack.getItemMeta();

            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to start in this location!");
            lore.add("");
            lore.add(ChatPalette.GRAY + "Required Level: " + town.getLevel());
            itemMeta.setLore(lore);

            itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + town.getName() + " #" + key);
            itemStack.setItemMeta(itemMeta);

            guiGeneric.setItem(index, itemStack);
        }


        return guiGeneric;
    }
}
