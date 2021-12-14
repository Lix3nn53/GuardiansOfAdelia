package io.github.lix3nn53.guardiansofadelia.menu.main.compass;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.menu.main.GuiCompass;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
import io.github.lix3nn53.guardiansofadelia.towns.Town;
import io.github.lix3nn53.guardiansofadelia.towns.TownManager;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiCompassTowns extends GuiBookGeneric {

    public GuiCompassTowns() {
        super(CustomCharacterGui.MENU_54_FLAT.toString() + ChatPalette.BLACK + "Compass Towns", 0);

        ItemStack backButton = OtherItems.getBackButton("Compass Menu");
        this.addToFirstAvailableWord(backButton);
        this.disableLine(0, 0);

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
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getSlot() == 0) {
            GuardianData guardianData;
            if (GuardianDataManager.hasGuardianData(player)) {
                guardianData = GuardianDataManager.getGuardianData(player);
            } else {
                return;
            }

            GuiCompass gui = new GuiCompass(guardianData);
            gui.openInventory(player);
        } else {
            ItemStack item = event.getCurrentItem();
            ItemMeta itemMeta = item.getItemMeta();

            String displayName = itemMeta.getDisplayName();
            String[] split = displayName.split("#");
            int i = Integer.parseInt(split[1]);

            Town town = TownManager.getTown(i);
            CompassManager.setCompassItemLocation(player, ChatPalette.BLUE_LIGHT + town.getName(), town.getLocation());
            player.closeInventory();
        }
    }
}
