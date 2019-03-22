package io.github.lix3nn53.guardiansofadelia.quests.actions;

import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItemAction implements Action {

    private final ItemStack itemStack;

    public GiveItemAction(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void perform(Player player) {
        InventoryUtils.giveItemToPlayer(player, itemStack);
    }
}
