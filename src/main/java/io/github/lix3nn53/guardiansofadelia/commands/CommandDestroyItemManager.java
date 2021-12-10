package io.github.lix3nn53.guardiansofadelia.commands;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class CommandDestroyItemManager {

    private final static HashMap<Player, ItemStack> playerToDestroyItem = new HashMap<>();

    public static void addItemToDestroy(Player player, ItemStack itemStack) {
        playerToDestroyItem.put(player, itemStack);

        new BukkitRunnable() {

            @Override
            public void run() {
                playerToDestroyItem.remove(player);
            }
        }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 20L * 10);
    }

    public static void destroy(Player player) {
        if (playerToDestroyItem.containsKey(player)) {
            ItemStack itemStack = playerToDestroyItem.get(player);
            InventoryUtils.removeItemFromInventory(player.getInventory(), itemStack, itemStack.getAmount());
            playerToDestroyItem.remove(player);
        } else {
            player.sendMessage(ChatPalette.RED + "Expired");
        }
    }
}
