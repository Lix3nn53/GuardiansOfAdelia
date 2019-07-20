package io.github.lix3nn53.guardiansofadelia.quests;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum QuestIconType {
    NEW,
    CURRENT,
    COMPLETED,
    EMPTY;

    public ItemStack getHoloItem() {
        ItemStack item = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta im = item.getItemMeta();
        im.setUnbreakable(true);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (this.equals(QuestIconType.NEW)) {
            im.setCustomModelData(10000001);
        } else if (this.equals(QuestIconType.CURRENT)) {
            im.setCustomModelData(10000002);
        } else if (this.equals(QuestIconType.COMPLETED)) {
            im.setCustomModelData(10000003);
        } else if (this.equals(QuestIconType.EMPTY)) {
            im.setCustomModelData(10000004);
        }
        item.setItemMeta(im);

        return item;
    }
}
