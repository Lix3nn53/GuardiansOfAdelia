package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.arenas.LastOneStanding;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiRPGInventory extends GuiGeneric {

    private final HashMap<Integer, Integer> slotToWarInstance = new HashMap<>();

    public GuiRPGInventory() {
        super(27, ChatPalette.GOLD + "Join Last One Standing", 0);

        int i = 9;
        for (LastOneStanding lastOneStanding : MiniGameManager.lastOneStandingList) {
            ItemStack room = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = room.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GREEN_DARK + lastOneStanding.getMinigameName() + " (" + lastOneStanding.getPlayersInGameSize() + "/" + lastOneStanding.getMaxPlayerSize() + ")");

            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GREEN_DARK + "Map: " + ChatPalette.WHITE + lastOneStanding.getMapName());
            lore.add(ChatPalette.YELLOW + "Level req: " + ChatPalette.WHITE + lastOneStanding.getLevelReq());
            lore.add(ChatPalette.GOLD + "Team amount: " + ChatPalette.WHITE + lastOneStanding.getTeamAmount());
            lore.add(ChatPalette.GOLD + "Team size: " + ChatPalette.WHITE + lastOneStanding.getTeamSize());
            lore.add(ChatPalette.PURPLE_LIGHT + "Game time: " + ChatPalette.WHITE + lastOneStanding.getTimeLimitInMinutes() + " minute(s)");
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to join this room!");
            itemMeta.setLore(lore);

            room.setItemMeta(itemMeta);
            if (lastOneStanding.isInGame()) {
                room.setType(Material.RED_WOOL);
            }
            room.setItemMeta(itemMeta);
            this.setItem(i, room);
            slotToWarInstance.put(i, lastOneStanding.getInstanceNo());
            i += 2;
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            int instanceNo = slotToWarInstance.get(event.getSlot());
            boolean joined = MiniGameManager.getLastOneStanding(instanceNo).joinQueue(player);
            if (joined) {
                player.closeInventory();
            }
        }
    }
}
