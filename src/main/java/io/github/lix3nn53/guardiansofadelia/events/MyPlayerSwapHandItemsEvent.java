package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class MyPlayerSwapHandItemsEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);

            if (guardianData.hasActiveCharacter()) {
                RPGCharacter rpgCharacter = guardianData.getActiveCharacter();
                RPGCharacterStats rpgCharacterStats = rpgCharacter.getRpgCharacterStats();
                String rpgClassStr = rpgCharacter.getRpgClassStr();

                ItemStack offHandItem = event.getMainHandItem(); // returns the item switched to mainhand, so it is in offhand before event
                Material offHandItemType = offHandItem.getType();
                boolean offHandUnequip = false;
                if (!InventoryUtils.isAirOrNull(offHandItem)) {
                    Material offType = offHandItem.getType();
                    WeaponGearType weaponGearType = WeaponGearType.fromMaterial(offType);
                    ShieldGearType shieldGearType = ShieldGearType.fromMaterial(offType);
                    if (weaponGearType != null) {
                        offHandUnequip = true;
                    } else if (shieldGearType != null) {
                        offHandUnequip = true;
                    }
                }

                ItemStack mainHandItem = event.getOffHandItem(); // returns the item switched to offhand, so it is in mainhand before event
                if (!InventoryUtils.isAirOrNull(mainHandItem)) {
                    boolean mainHandEquip = false;

                    Material mainType = mainHandItem.getType();
                    WeaponGearType weaponGearType = WeaponGearType.fromMaterial(mainType);
                    ShieldGearType shieldGearType = ShieldGearType.fromMaterial(mainType);
                    if (weaponGearType != null) {
                        mainHandEquip = true;
                        if (!weaponGearType.canEquipToOffHand()) {
                            player.sendMessage(ChatPalette.RED + "You can't equip " + weaponGearType.getDisplayName() + " to offhand.");
                            event.setCancelled(true);
                            return;
                        }
                    } else if (shieldGearType != null) {
                        mainHandEquip = true;
                    }

                    if (mainHandEquip && StatUtils.doesCharacterMeetRequirements(mainHandItem, player, rpgClassStr)) {
                        rpgCharacterStats.onMainHandUnequip(true);
                        if (offHandUnequip) rpgCharacterStats.onMainHandEquip(offHandItem, true);

                        rpgCharacterStats.onOffhandEquip(mainHandItem, true);
                        // if (isMainhandShield) rpgCharacterStats.onMaxHealthChange();
                    } else {
                        event.setCancelled(true);
                    }
                } else if (offHandUnequip) {
                    rpgCharacterStats.onMainHandEquip(offHandItem, true);
                    rpgCharacterStats.onOffhandUnequip(offHandItem);
                    // if (offHandItemType is shield) rpgCharacterStats.onMaxHealthChange();
                }
            }
        }
    }
}
