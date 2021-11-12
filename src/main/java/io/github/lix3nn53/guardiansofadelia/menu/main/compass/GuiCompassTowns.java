package io.github.lix3nn53.guardiansofadelia.menu.main.compass;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiCompassTowns extends GuiBookGeneric {

    public GuiCompassTowns() {
        super(ChatPalette.GRAY_DARK + "Compass Towns", 0);

        HashMap<Integer, Town> towns = TownManager.getTowns();
        for (int key : towns.keySet()) {
            Town town = towns.get(key);
            ItemStack itemStack = new ItemStack(Material.LIGHT_BLUE_WOOL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to select your compass target!");
            lore.add("");
            lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
            itemMeta.setLore(lore);

            itemMeta.setDisplayName(ChatPalette.BLUE_LIGHT + town.getName() + " #" + key);
            itemStack.setItemMeta(itemMeta);

            this.addToFirstAvailableWord(itemStack);
        }
    }

    @Override
    public void onClick(Player player, GuardianData guardianData, String title, int slot) {
        int pageIndex = this.getPageIndex(title);
        GuiGeneric pageInventory = this.getPageInventory(pageIndex);

        ItemStack item = pageInventory.getItem(slot);
        ItemMeta itemMeta = item.getItemMeta();

        String displayName = itemMeta.getDisplayName();
        String[] split = displayName.split("#");
        int i = Integer.parseInt(split[1]);

        Town town = TownManager.getTown(i);
        CompassManager.setCompassItemLocation(player, ChatPalette.BLUE_LIGHT + town.getName(), town.getLocation());
        player.closeInventory();
    }
}
