package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.minigames.MiniGameManager;
import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonInstance;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class GuiDungeonJoin extends GuiGeneric {

    private final HashMap<Integer, Integer> slotToInstance = new HashMap<>();
    String code;

    public GuiDungeonJoin(String name, String code, List<ItemStack> instanceItems) {
        super(27, "Join dungeon: " + name + " #" + code, 0);
        this.code = code;

        int slotNo = 9;
        for (int i = 0; i < instanceItems.size(); i++) {
            int instanceNo = i + 1;
            DungeonInstance dungeonInstance = MiniGameManager.getDungeonInstance(code, instanceNo);
            if (dungeonInstance == null) {
                break;
            }
            ItemStack itemStack = instanceItems.get(i);
            this.setItem(slotNo, itemStack);
            slotToInstance.put(slotNo, instanceNo);

            slotNo = slotNo + 2;
        }
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory.getType().equals(InventoryType.CHEST)) {
            ItemStack current = event.getCurrentItem();
            Material currentType = current.getType();

            if (currentType.equals(Material.LIME_WOOL)) {
                int instanceNo = slotToInstance.get(event.getSlot());

                boolean joined = MiniGameManager.getDungeonInstance(code, instanceNo).joinQueue(player);
                if (joined) {
                    player.closeInventory();
                }
            }
        }
    }
}
