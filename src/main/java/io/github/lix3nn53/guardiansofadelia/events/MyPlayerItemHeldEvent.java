package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.utilities.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MyPlayerItemHeldEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        if (GuardianDataManager.hasGuardianData(player)) {
            GuardianData guardianData = GuardianDataManager.getGuardianData(player);
            if (guardianData.hasActiveCharacter()) {
                RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                int newSlot = event.getNewSlot();
                int previousSlot = event.getPreviousSlot();

                if (newSlot == previousSlot) {
                    return;
                }

                if (newSlot >= 0 && newSlot <= 3) { //skill bar
                    if (previousSlot >= 0 && previousSlot <= 3) { //old slot is also skill bar
                        player.getInventory().setHeldItemSlot(4);
                        return;
                    }

                    boolean success = activeCharacter.getSkillBar().castSkill(newSlot);
                    if (!success) {
                        //play sound special to player on cast fail
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 0.1f, 0.1f);
                    }
                    event.setCancelled(true);
                    return;
                }

                if (previousSlot >= 0 && previousSlot <= 3) { //skill bar
                    return;
                }

                String rpgClassStr = activeCharacter.getRpgClassStr();

                PlayerInventory inventory = player.getInventory();
                //remove old item stats
                /*ItemStack oldItem = inventory.getItem(previousSlot);
                if (oldItem != null) {
                    Material type = oldItem.getType();

                    //manage stats on hold
                    if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                            || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {

                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        rpgCharacterStats.removeMainHandBonuses(oldItem, rpgClass, true);
                    }
                }*/

                //manage new item stats
                ItemStack item = inventory.getItem(newSlot);
                if (item == null) return;
                Material type = item.getType();
                /*boolean isWeapon = type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                        || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW);*/

                //manage arrow in offhand
                if ((type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) && newSlot == 4) { //weapon slot is 4
                    boolean meetRequirements = StatUtils.doesCharacterMeetRequirements(item, player, rpgClassStr);
                    if (meetRequirements) {
                        ItemStack itemInOffHand = player.getInventory().getItem(EquipmentSlot.OFF_HAND);
                        if (!InventoryUtils.isAirOrNull(itemInOffHand)) {
                            InventoryUtils.giveItemToPlayer(player, itemInOffHand);
                        }
                        ItemStack arrow = OtherItems.getArrow(2);
                        inventory.setItemInOffHand(arrow);
                    }
                } else {
                    ItemStack itemInOffHand = inventory.getItemInOffHand();
                    if (itemInOffHand.getType().equals(Material.ARROW)) {
                        inventory.setItemInOffHand(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }

}
