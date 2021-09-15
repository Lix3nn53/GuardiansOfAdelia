package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.creatures.drops.DropProtectionManager;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
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
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
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

            //int heldItemSlot = inventory.getHeldItemSlot();
            int firstEmpty = inventory.firstEmpty();

            if (firstEmpty == 4) { //pickup and hold
                WeaponGearType weaponGearType = WeaponGearType.fromMaterial(type);
                if (weaponGearType != null) { // isWeapon?
                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            activeCharacter.getRpgCharacterStats().onMainHandEquip(itemStack, true);
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
