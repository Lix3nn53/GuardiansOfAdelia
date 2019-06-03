package io.github.lix3nn53.guardiansofadelia.Items.scrolls;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeleportScroll {

    private final TeleportScrollLocation location;
    private final int level;

    public TeleportScroll(TeleportScrollLocation location, int level) {
        this.location = location;
        this.level = level;
    }

    public ItemStack getScroll(int amount) {
        ItemStack scroll = new ItemStack(Material.PAPER, amount);
        ItemMeta itemMeta = scroll.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport Scroll " + ChatColor.DARK_PURPLE +
                "(" + ChatColor.AQUA + location.getName() + ChatColor.DARK_PURPLE + ")");
        itemMeta.setLore(new ArrayList() {{
            add("");
            add(ChatColor.YELLOW + "Required Level: " + level);
            add("");
            add(ChatColor.BLUE + "Right click while holding this");
            add(ChatColor.BLUE + "to start teleporting.");
            add(ChatColor.BLUE + "You will be teleported to");
            add(ChatColor.AQUA + location.getName() + ChatColor.BLUE + " in 5 seconds.");
            add("");
            add(ChatColor.RED + "If you move, the teleportation will be canceled!");
        }});
        scroll.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putInteger("reqLevel", level, scroll);
        PersistentDataContainerUtil.putString("teleportScroll", location.toString(), scroll);
        return scroll;
    }
}
