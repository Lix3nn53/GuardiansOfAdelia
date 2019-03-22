package io.github.lix3nn53.guardiansofadelia.quests;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
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
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        if (im instanceof Damageable) {
            if (this.equals(QuestIconType.NEW)) {
                ((Damageable) im).setDamage(34);
            } else if (this.equals(QuestIconType.CURRENT)) {
                ((Damageable) im).setDamage(33);
            } else if (this.equals(QuestIconType.COMPLETED)) {
                ((Damageable) im).setDamage(32);
            } else if (this.equals(QuestIconType.EMPTY)) {
                ((Damageable) im).setDamage(35);
            }
        }
        item.setItemMeta(im);

        return item;
    }
}
