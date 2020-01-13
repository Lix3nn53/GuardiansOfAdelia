package io.github.lix3nn53.guardiansofadelia.quests;

import org.bukkit.Material;
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
        if (this.equals(QuestIconType.NEW)) {
            im.setCustomModelData(1);
        } else if (this.equals(QuestIconType.CURRENT)) {
            im.setCustomModelData(2);
        } else if (this.equals(QuestIconType.COMPLETED)) {
            im.setCustomModelData(3);
        } else if (this.equals(QuestIconType.EMPTY)) {
            im.setCustomModelData(4);
        }
        item.setItemMeta(im);

        return item;
    }
}
