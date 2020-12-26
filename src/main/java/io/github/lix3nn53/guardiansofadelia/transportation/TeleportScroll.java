package io.github.lix3nn53.guardiansofadelia.transportation;

import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TeleportScroll {
    private final Location location;
    private final String name;

    public TeleportScroll(Location location, String name) {
        this.location = location;
        this.name = name;
    }

    public TeleportScroll(String toString) {
        String[] split = toString.split("\\.");
        World world = Bukkit.getWorld(split[0]);
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        int z = Integer.parseInt(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);
        String name = split[6];

        this.location = new Location(world, x, y, z, yaw, pitch);
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(location.getWorld().getName());
        sb.append(".");
        sb.append(location.getBlockX()).append(".");
        sb.append(location.getBlockY()).append(".");
        sb.append(location.getBlockZ()).append(".");
        sb.append(location.getYaw()).append(".");
        sb.append(location.getPitch()).append(".");
        sb.append(name);
        return sb.toString();
    }

    public ItemStack getScroll(int amount, int level) {
        ItemStack scroll = new ItemStack(Material.PAPER, amount);
        ItemMeta itemMeta = scroll.getItemMeta();
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Teleport Scroll " + ChatColor.DARK_PURPLE +
                "(" + ChatColor.AQUA + getName() + ChatColor.DARK_PURPLE + ")");

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(ChatColor.YELLOW + "Required Level: " + level);
        lore.add("");
        lore.add(ChatColor.BLUE + "Right click while holding this");
        lore.add(ChatColor.BLUE + "to start teleporting.");
        lore.add(ChatColor.BLUE + "You will be teleported to");
        lore.add(ChatColor.AQUA + getName() + ChatColor.BLUE + " in 5 seconds.");
        lore.add("");
        lore.add(ChatColor.RED + "If you move, the teleportation will be canceled!");
        itemMeta.setLore(lore);

        scroll.setItemMeta(itemMeta);
        PersistentDataContainerUtil.putInteger("reqLevel", level, scroll);
        PersistentDataContainerUtil.putString("teleportScroll", toString(), scroll);
        return scroll;
    }
}
