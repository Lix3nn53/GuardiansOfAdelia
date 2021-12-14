package io.github.lix3nn53.guardiansofadelia.menu.main.minigames;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.menu.main.GuiMinigames;
import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.arenas.WinByMostKills;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.text.font.CustomCharacterGui;
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

public class GuiMinigamesJoinWBMK extends GuiGeneric {

    private final HashMap<Integer, Integer> slotToWarInstance = new HashMap<>();

    public GuiMinigamesJoinWBMK() {
        super(27, CustomCharacterGui.MENU_27_FLAT.toString() + ChatPalette.BLACK + "Join Win By Most Kills", 0);

        ItemStack backButton = OtherItems.getBackButton("Minigames Menu");
        this.setItem(0, backButton);

        int i = 9;
        for (WinByMostKills winByMostKills : MiniGameManager.winByMostKillsList) {
            ItemStack room = new ItemStack(Material.LIME_WOOL);
            ItemMeta itemMeta = room.getItemMeta();
            itemMeta.setDisplayName(ChatPalette.GREEN_DARK + winByMostKills.getMinigameName() + " (" + winByMostKills.getPlayersInGameSize() + "/" + winByMostKills.getMaxPlayerSize() + ")");

            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            lore.add(ChatPalette.GREEN_DARK + "Map: " + ChatPalette.WHITE + winByMostKills.getMapName());
            lore.add(ChatPalette.YELLOW + "Level req: " + ChatPalette.WHITE + winByMostKills.getLevelReq());
            lore.add(ChatPalette.GOLD + "Team amount: " + ChatPalette.WHITE + winByMostKills.getTeamAmount());
            lore.add(ChatPalette.GOLD + "Team size: " + ChatPalette.WHITE + winByMostKills.getTeamSize());
            lore.add(ChatPalette.PURPLE_LIGHT + "Game time: " + ChatPalette.WHITE + winByMostKills.getTimeLimitInMinutes() + " minute(s)");
            lore.add("");
            lore.add(ChatPalette.GRAY + "Click to join this room!");
            itemMeta.setLore(lore);

            room.setItemMeta(itemMeta);
            if (winByMostKills.isInGame()) {
                room.setType(Material.RED_WOOL);
            }
            room.setItemMeta(itemMeta);
            this.setItem(i, room);
            slotToWarInstance.put(i, winByMostKills.getInstanceNo());
            i += 2;
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            int slot = event.getSlot();
            if (slot == 0) {
                GuardianData guardianData;
                if (GuardianDataManager.hasGuardianData(player)) {
                    guardianData = GuardianDataManager.getGuardianData(player);
                } else {
                    return;
                }

                GuiMinigames gui = new GuiMinigames(guardianData);
                gui.openInventory(player);
            } else {
                int instanceNo = slotToWarInstance.get(slot);
                boolean joined = MiniGameManager.getWinByMostKills(instanceNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        }
    }
}
