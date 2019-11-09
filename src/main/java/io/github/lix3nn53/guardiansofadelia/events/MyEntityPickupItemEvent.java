package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropProtectionManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

public class MyEntityPickupItemEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();
        Material type = itemStack.getType();
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();
            if (!DropProtectionManager.canPickUp(player, itemStack)) {
                event.setCancelled(true);
                return;
            }

            //progress collect tasks for quests
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                UUID uniqueId = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uniqueId)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        List<Quest> questList = activeCharacter.getQuestList();
                        for (Quest quest : questList) {
                            quest.progressCollectTasks(player, itemMeta.getDisplayName(), itemStack.getAmount());
                        }
                    }
                }
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
            DropProtectionManager.onItemDespawn(itemStack);
        }
    }

}
