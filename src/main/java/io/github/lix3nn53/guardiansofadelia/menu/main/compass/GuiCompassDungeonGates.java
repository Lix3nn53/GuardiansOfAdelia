package io.github.lix3nn53.guardiansofadelia.menu.main.compass;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiBookGeneric;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.CompassManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiCompassDungeonGates extends GuiBookGeneric {

    public GuiCompassDungeonGates() {
        super(ChatPalette.GRAY_DARK + "Compass Dungeon Gates", 0);

        HashMap<String, DungeonTheme> dungeonThemes = MiniGameManager.getDungeonThemes();
        for (String dungeonCode : dungeonThemes.keySet()) {
            ItemStack itemStack = new ItemStack(Material.PURPLE_WOOL);
            ItemMeta itemMeta = itemStack.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to select your compass target!");
            lore.add("");
            lore.add(ChatPalette.GRAY + "If you dont have a compass this will give you one.");
            itemMeta.setLore(lore);

            DungeonTheme dungeonTheme = dungeonThemes.get(dungeonCode);
            itemMeta.setDisplayName(ChatPalette.PURPLE_LIGHT + dungeonTheme.getName() + " #" + dungeonCode);
            itemStack.setItemMeta(itemMeta);

            this.addToFirstAvailableWord(itemStack);
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        ItemStack item = event.getCurrentItem();
        ItemMeta itemMeta = item.getItemMeta();

        String displayName = itemMeta.getDisplayName();
        String[] split = displayName.split("#");
        Location portalLocationOfDungeonTheme = MiniGameManager.getPortalLocationOfDungeonTheme(split[1]);
        CompassManager.setCompassItemLocation(player, split[0], portalLocationOfDungeonTheme);
        player.closeInventory();
    }
}
