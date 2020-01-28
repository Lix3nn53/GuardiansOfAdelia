package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.Items.Consumable;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyPlayerItemConsumeEvent implements Listener {

    private final static HashMap<Player, List<String>> playerToConsumableOnCooldowns = new HashMap<>();

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.hasItemMeta()) {
            if (PersistentDataContainerUtil.hasString(item, "customConsumable")) {
                event.setCancelled(true);
                String customConsumable = PersistentDataContainerUtil.getString(item, "customConsumable");

                if (playerToConsumableOnCooldowns.containsKey(player)) {
                    List<String> strings = playerToConsumableOnCooldowns.get(player);

                    if (strings.contains(customConsumable)) {
                        player.sendMessage(ChatColor.RED + "This consumable is on cooldown");
                        return;
                    }

                    strings.add(customConsumable);
                } else {
                    List<String> strings = new ArrayList<>();
                    strings.add(customConsumable);
                    playerToConsumableOnCooldowns.put(player, strings);
                }

                int consumableLevel = PersistentDataContainerUtil.getInteger(item, "consumableLevel");
                Consumable consumable = Consumable.valueOf(customConsumable);
                consumable.consume(player, consumableLevel, player.getInventory().getItemInMainHand());

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (playerToConsumableOnCooldowns.containsKey(player)) {
                            List<String> strings = playerToConsumableOnCooldowns.get(player);

                            if (strings.contains(customConsumable)) {
                                strings.remove(customConsumable);
                                if (strings.isEmpty()) {
                                    playerToConsumableOnCooldowns.remove(player);
                                }
                            }
                        }
                    }
                }.runTaskLater(GuardiansOfAdelia.getInstance(), 80L);
            }
        }
    }
}
