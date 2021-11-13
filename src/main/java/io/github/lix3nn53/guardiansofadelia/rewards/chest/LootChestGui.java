package io.github.lix3nn53.guardiansofadelia.rewards.chest;

import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LootChestGui extends GuiGeneric {

    public LootChestGui(int ordinal, List<ItemStack> loots) {
        super(27, ChatPalette.BLACK + "Loot Chest #" + ordinal, 0);

        for (int i = 0; i < 27; i++) {
            ItemStack itemStack = loots.get(i);
            if (itemStack.getType().equals(Material.AIR)) continue;

            this.setItem(i, itemStack);
        }

        this.setLocked(false);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        ItemStack[] contents = event.getInventory().getContents();
        for (ItemStack content : contents) {
            if (content == null) continue;

            Player player = (Player) event.getPlayer();
            InventoryUtils.giveItemToPlayer(player, content);
        }
    }
}
