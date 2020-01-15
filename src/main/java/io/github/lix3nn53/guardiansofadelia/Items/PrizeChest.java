package io.github.lix3nn53.guardiansofadelia.Items;

import io.github.lix3nn53.guardiansofadelia.minigames.dungeon.DungeonTheme;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PrizeChest {

    private final String name;

    public PrizeChest(String name) {
        this.name = ChatColor.GOLD + name + " Prize Chest";
    }

    /**
     * @param dungeonTheme
     * @param type         0 for Weapon, 1 for Jewelry, 2 for Armor
     */
    public PrizeChest(DungeonTheme dungeonTheme, PrizeChestType type) {
        String chestName = ChatColor.GOLD + dungeonTheme.getName() + " Prize Chest";

        if (type.equals(PrizeChestType.WEAPON)) {
            chestName = chestName + " (Weapon)";
        } else if (type.equals(PrizeChestType.ARMOR)) {
            chestName = chestName + " (Armor)";
        } else if (type.equals(PrizeChestType.JEWELRY)) {
            chestName = chestName + " (Jewelry)";
        } else if (type.equals(PrizeChestType.PET)) {
            chestName = chestName + " (Pet)";
        }

        this.name = chestName;
    }

    public String getName() {
        return name;
    }

    public void play(Player player, List<ItemStack> prizePool) {
        InventoryUtils.play(player, prizePool, getName());
    }

    public ItemStack getChest() {
        ItemStack itemStack = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(35);
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.GRAY + "Right click while holding to open!");

        itemMeta.setDisplayName(getName());
        itemMeta.setLore(lore);
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
