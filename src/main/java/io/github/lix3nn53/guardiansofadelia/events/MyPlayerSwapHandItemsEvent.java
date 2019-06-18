package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MyPlayerSwapHandItemsEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerSwapHandItemsEvent event) {
        ItemStack mainHandItem = event.getMainHandItem();
        ItemStack offHandItem = event.getOffHandItem();

        if (!isAirOrNull(mainHandItem)) {
            Material mainHandItemType = mainHandItem.getType();

            if (mainHandItemType.equals(Material.SHIELD) || mainHandItemType.equals(Material.DIAMOND_HOE)) {
                Player player = event.getPlayer();

                UUID uuid = player.getUniqueId();
                if (GuardianDataManager.hasGuardianData(uuid)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                        if (!isAirOrNull(offHandItem)) {
                            rpgCharacter.getRpgCharacterStats().onOffhandUnequip(offHandItem);
                        }

                        if (StatUtils.doesCharacterMeetRequirements(mainHandItem, player, rpgCharacter.getRpgClass())) {
                            rpgCharacter.getRpgCharacterStats().onOffhandEquip(mainHandItem);
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            } else {
                event.setCancelled(true);
            }
        } else {
            if (!isAirOrNull(offHandItem)) {
                Material offHandItemType = offHandItem.getType();

                if (offHandItemType.equals(Material.SHIELD) || offHandItemType.equals(Material.DIAMOND_HOE)) {
                    Player player = event.getPlayer();

                    UUID uuid = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uuid)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uuid);

                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter rpgCharacter = guardianData.getActiveCharacter();

                            rpgCharacter.getRpgCharacterStats().onOffhandUnequip(offHandItem);
                        }
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * A utility method to support versions that use null or air ItemStacks.
     */
    private boolean isAirOrNull(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
}
