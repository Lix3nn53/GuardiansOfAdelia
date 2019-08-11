package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MyPlayerSwapHandItemsEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (GuardianDataManager.hasGuardianData(uuid)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();
                RPGClass rpgClass = rpgCharacter.getRpgClass();

                ItemStack offHandItem = event.getMainHandItem(); //returns the item switched to mainhand, so it is in offhand before event
                Material offHandItemType = offHandItem.getType();
                boolean offHandUnequip = false;
                if (!InventoryUtils.isAirOrNull(offHandItem)) {

                    if (offHandItemType.equals(Material.SHIELD) || offHandItemType.equals(Material.DIAMOND_HOE)) {
                        offHandUnequip = true; //do not call onOffhandUnequip here cuz event might get canceled
                    }
                }

                ItemStack mainHandItem = event.getOffHandItem(); //returns the item switched to offhand, so it is in mainhand before event
                if (!InventoryUtils.isAirOrNull(mainHandItem)) {
                    Material mainHandItemType = mainHandItem.getType();

                    if ((mainHandItemType.equals(Material.SHIELD) || mainHandItemType.equals(Material.DIAMOND_HOE)) && StatUtils.doesCharacterMeetRequirements(mainHandItem, player, rpgClass)) {
                        rpgCharacterStats.removeMainHandBonuses(mainHandItem, rpgClass, true);
                        if (offHandUnequip) rpgCharacterStats.setMainHandBonuses(offHandItem, rpgClass, true);

                        rpgCharacterStats.onOffhandEquip(mainHandItem, true);
                        if (mainHandItemType.equals(Material.SHIELD)) rpgCharacterStats.onMaxHealthChange();
                    } else {
                        event.setCancelled(true);
                    }
                } else if (offHandUnequip) {
                    rpgCharacterStats.setMainHandBonuses(offHandItem, rpgClass, true);
                    rpgCharacterStats.onOffhandUnequip(offHandItem);
                    if (offHandItemType.equals(Material.SHIELD)) rpgCharacterStats.onMaxHealthChange();
                }
            }
        }
    }
}
