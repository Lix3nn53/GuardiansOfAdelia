package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public class MyEntityPickupItemEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();
        Material type = itemStack.getType();
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            if (!DropManager.canPickUp(player, itemStack)) {
                event.setCancelled(true);
                return;
            }

            PlayerInventory inventory = player.getInventory();

            int heldItemSlot = inventory.getHeldItemSlot();
            int firstEmpty = inventory.firstEmpty();

            if (firstEmpty == heldItemSlot) { //pickup and hold
                if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                        || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {

                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                            RPGClass rpgClass = activeCharacter.getRpgClass();

                            activeCharacter.getRpgCharacterStats().setMainHandBonuses(itemStack, rpgClass, true);
                        }
                    }
                }
            }
        } else {
            event.setCancelled(true);
            return;
        }


        if (!event.isCancelled()) {
            DropManager.onItemDespawn(itemStack);
        }
    }

}
