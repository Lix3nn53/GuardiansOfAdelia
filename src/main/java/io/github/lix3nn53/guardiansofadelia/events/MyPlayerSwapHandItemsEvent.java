package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.ShieldGearType;
import io.github.lix3nn53.guardiansofadelia.Items.RpgGears.WeaponGearType;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
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

                ItemStack offHandItem = event.getMainHandItem(); //returns the item switched to mainhand, so it is in offhand before event
                Material offHandItemType = offHandItem.getType();
                boolean offHandUnequip = false;
                if (!InventoryUtils.isAirOrNull(offHandItem)) {

                    if (PersistentDataContainerUtil.hasString(offHandItem, "gearType")) {
                        String gearTypeString = PersistentDataContainerUtil.getString(offHandItem, "gearType");
                        for (ShieldGearType c : ShieldGearType.values()) {
                            if (c.name().equals(gearTypeString)) {
                                offHandUnequip = true;
                            }
                        }

                        if (!offHandUnequip) {
                            for (WeaponGearType c : WeaponGearType.values()) {
                                if (c.name().equals(gearTypeString)) {
                                    if (c.canEquipToOffHand()) {
                                        offHandUnequip = true;
                                    }
                                }
                            }
                        }
                    }
                }

                ItemStack mainHandItem = event.getOffHandItem(); //returns the item switched to offhand, so it is in mainhand before event
                if (!InventoryUtils.isAirOrNull(mainHandItem) && PersistentDataContainerUtil.hasString(mainHandItem, "gearType")) {
                    String gearTypeString = PersistentDataContainerUtil.getString(offHandItem, "gearType");

                    boolean mainHandEquip = false;
                    boolean isMainhandShield = false;
                    for (ShieldGearType c : ShieldGearType.values()) {
                        if (c.name().equals(gearTypeString)) {
                            mainHandEquip = true;
                            isMainhandShield = true;
                        }
                    }

                    if (!mainHandEquip) {
                        for (WeaponGearType c : WeaponGearType.values()) {
                            if (c.name().equals(gearTypeString)) {
                                if (c.canEquipToOffHand()) {
                                    mainHandEquip = true;
                                }
                            }
                        }
                    }

                    if (mainHandEquip && StatUtils.doesCharacterMeetRequirements(mainHandItem, player, rpgClassStr)) {
                        rpgCharacterStats.removeMainHandBonuses(mainHandItem, rpgClassStr, true);
                        if (offHandUnequip) rpgCharacterStats.setMainHandBonuses(offHandItem, rpgClassStr, true);

                        rpgCharacterStats.onOffhandEquip(mainHandItem, true);
                        if (isMainhandShield) rpgCharacterStats.onMaxHealthChange();
                    } else {
                        event.setCancelled(true);
                    }
                } else if (offHandUnequip) {
                    rpgCharacterStats.setMainHandBonuses(offHandItem, rpgClassStr, true);
                    rpgCharacterStats.onOffhandUnequip(offHandItem);
                    if (offHandItemType.equals(Material.SHIELD)) rpgCharacterStats.onMaxHealthChange();
                }
            }
        }
    }
}
