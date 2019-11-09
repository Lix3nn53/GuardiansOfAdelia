package io.github.lix3nn53.guardiansofadelia.creatures.drops;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DropProtectionManager {

    private static HashMap<ItemStack, List<Player>> droppedItemOwners = new HashMap<>();

    public static boolean canPickUp(Player player, ItemStack itemStack) {
        if (droppedItemOwners.containsKey(itemStack)) {
            List<Player> players = droppedItemOwners.get(itemStack);
            return players.contains(player);
        }
        return true;
    }

    public static boolean onItemDespawn(ItemStack itemStack) {
        droppedItemOwners.remove(itemStack);
        return true;
    }

    public static void setItem(ItemStack itemStack, List<Player> players) {
        droppedItemOwners.put(itemStack, players);
        startItemTimer(itemStack);
    }

    public static void setItem(ItemStack itemStack, Player player) {
        List<Player> players = new ArrayList<>();
        players.add(player);
        droppedItemOwners.put(itemStack, players);
        startItemTimer(itemStack);
    }

    private static void startItemTimer(ItemStack itemStack) {
        new BukkitRunnable() {
            @Override
            public void run() {
                droppedItemOwners.remove(itemStack);
            }
        }.runTaskLater(GuardiansOfAdelia.getInstance(), 20 * 30L);
    }
}
