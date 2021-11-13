package io.github.lix3nn53.guardiansofadelia.menu.start;

import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CharacterSelectionScreenManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiCharacterSelect extends GuiGeneric {

    private final int charNo;
    private final HashMap<Integer, Integer> slotNoToTownNo = new HashMap<>();

    public GuiCharacterSelect(int charNo) {
        super(36, ChatPalette.GRAY_DARK + "Character " + charNo + " Selection", 0);
        this.charNo = charNo;

        ItemStack lastLocation = new ItemStack(Material.MAGENTA_WOOL);
        ItemMeta itemMeta = lastLocation.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + "Teleport to your last location");
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        lastLocation.setItemMeta(itemMeta);
        this.setItem(11, lastLocation);

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

            this.setItem(index, itemStack);

            slotNoToTownNo.put(index, key);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();

        if (slot == 11) {
            Location charLocation = CharacterSelectionScreenManager.getCharLocation(player, charNo);
            if (charLocation != null) {
                CharacterSelectionScreenManager.selectCharacter(player, charNo, charLocation);
            } else {
                player.sendMessage(ChatPalette.RED + "Your saved last location is not valid");
            }
        } else {
            if (!slotNoToTownNo.containsKey(slot)) return;
            int townNo = slotNoToTownNo.get(slot);

            Town town = TownManager.getTown(townNo);

            int charLevel = CharacterSelectionScreenManager.getCharLevel(player, charNo);
            if (charLevel < town.getLevel()) return;

            Location location = town.getLocation();

            CharacterSelectionScreenManager.selectCharacter(player, charNo, location);
        }
    }
}
