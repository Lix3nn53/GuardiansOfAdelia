package io.github.lix3nn53.guardiansofadelia.menu;

import io.github.lix3nn53.guardiansofadelia.economy.EconomyUtils;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiGenericBuy extends GuiGeneric {

    public GuiGenericBuy(int size, String title, int resourceNpcNo) {
        super(size, title, resourceNpcNo);
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        ItemStack current = event.getCurrentItem();
        if (PersistentDataContainerUtil.hasInteger(current, "shopPrice")) {
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();

            boolean didClickBefore = MerchantManager.onSellItemClick(player, slot);
            if (didClickBefore) {
                boolean pay = EconomyUtils.pay(player, current);
                if (pay) {
                    ItemStack itemStack = EconomyUtils.removeShopPrice(current);
                    InventoryUtils.giveItemToPlayer(player, itemStack);
                }
            }
        }
    }
}
