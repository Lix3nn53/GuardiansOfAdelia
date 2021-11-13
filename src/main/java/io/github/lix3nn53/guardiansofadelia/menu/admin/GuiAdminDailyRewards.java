package io.github.lix3nn53.guardiansofadelia.menu.admin;

import io.github.lix3nn53.guardiansofadelia.rewards.daily.DailyRewardHandler;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiAdminDailyRewards extends GuiGeneric {

    public GuiAdminDailyRewards() {
        super(9, ChatPalette.YELLOW + "Set Daily Rewards", 0);

        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS);
        ItemMeta itemMeta = filler.getItemMeta();
        itemMeta.setDisplayName("");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        itemMeta.setLore(lore);
        filler.setItemMeta(itemMeta);
        this.setItem(0, filler);
        this.setItem(8, filler);

        ItemStack[] rewards = DailyRewardHandler.getRewards();
        int i = 1;
        for (ItemStack itemStack : rewards) {
            if (itemStack == null) continue;

            this.setItem(i, itemStack);
            i++;
        }

        this.setLocked(false);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        ItemStack[] contents = event.getInventory().getContents();

        for (int i = 1; i < 8; i++) {
            ItemStack content = contents[i];

            DailyRewardHandler.setReward(i, content);
        }
    }
}
