package io.github.lix3nn53.guardiansofadelia.rpginventory.slots;


import io.github.lix3nn53.guardiansofadelia.jobs.gathering.GatheringToolType;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ToolSlotAxe extends ToolSlot {

    @Override
    public GatheringToolType getToolType() {
        return GatheringToolType.AXE;
    }

    public ItemStack getFillItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_AXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.setDisplayName(ChatPalette.YELLOW + "Axe Slot");
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("Used for wood cutting");
        itemMeta.setLore(lore);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setCustomModelData(1);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
