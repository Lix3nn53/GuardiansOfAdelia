package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class MyPlayerItemHeldEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                RPGClass rpgClass = activeCharacter.getRpgClass();

                if (rpgClass.equals(RPGClass.ARCHER) || rpgClass.equals(RPGClass.HUNTER)) {
                    PlayerInventory inventory = player.getInventory();
                    int newSlot = event.getNewSlot();
                    ItemStack item = inventory.getItem(newSlot);

                    if (item != null) {
                        if (item.getType().equals(Material.BOW) || item.getType().equals(Material.CROSSBOW)) {
                            ItemStack arrow = OtherItems.getArrow(2);
                            inventory.setItemInOffHand(arrow);
                        } else {
                            inventory.setItemInOffHand(new ItemStack(Material.AIR));
                        }
                    } else {
                        inventory.setItemInOffHand(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

}
