package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacterStats;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
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

                //remove old item stats
                PlayerInventory inventory = player.getInventory();
                int oldSlow = event.getPreviousSlot();
                ItemStack oldItem = inventory.getItem(oldSlow);
                if (oldItem != null) {
                    boolean meetsTheRequirements = true;
                    if (PersistentDataContainerUtil.hasInteger(oldItem, "reqLevel")) {
                        int reqLevel = PersistentDataContainerUtil.getInteger(oldItem, "reqLevel");
                        if (player.getLevel() < reqLevel) {
                            meetsTheRequirements = false;
                        }
                    }

                    if (PersistentDataContainerUtil.hasString(oldItem, "reqClass")) {
                        String reqClassString = PersistentDataContainerUtil.getString(oldItem, "reqClass");
                        RPGClass reqClass = RPGClass.valueOf(reqClassString);
                        if (!rpgClass.equals(reqClass)) {
                            meetsTheRequirements = false;
                        }
                    }

                    if (meetsTheRequirements) {
                        Material type = oldItem.getType();

                        //manage stats on hold
                        if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                                || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                            RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                            if (PersistentDataContainerUtil.hasInteger(oldItem, "fire")) {
                                int bonus = PersistentDataContainerUtil.getInteger(oldItem, "fire");
                                rpgCharacterStats.getFire().removeBonus(bonus, rpgCharacterStats);
                            }
                            if (PersistentDataContainerUtil.hasInteger(oldItem, "water")) {
                                int bonus = PersistentDataContainerUtil.getInteger(oldItem, "water");
                                rpgCharacterStats.getWater().removeBonus(bonus, rpgCharacterStats);
                            }
                            if (PersistentDataContainerUtil.hasInteger(oldItem, "earth")) {
                                int bonus = PersistentDataContainerUtil.getInteger(oldItem, "earth");
                                rpgCharacterStats.getEarth().removeBonus(bonus, rpgCharacterStats);
                            }
                            if (PersistentDataContainerUtil.hasInteger(oldItem, "lightning")) {
                                int bonus = PersistentDataContainerUtil.getInteger(oldItem, "lightning");
                                rpgCharacterStats.getLightning().removeBonus(bonus, rpgCharacterStats);
                            }
                            if (PersistentDataContainerUtil.hasInteger(oldItem, "wind")) {
                                int bonus = PersistentDataContainerUtil.getInteger(oldItem, "wind");
                                rpgCharacterStats.getWind().removeBonus(bonus, rpgCharacterStats);
                            }
                        }
                    }
                }

                //manage new item stats
                int newSlot = event.getNewSlot();
                ItemStack item = inventory.getItem(newSlot);

                if (item != null) {
                    if (PersistentDataContainerUtil.hasInteger(item, "reqLevel")) {
                        int reqLevel = PersistentDataContainerUtil.getInteger(item, "reqLevel");
                        if (player.getLevel() < reqLevel) {
                            return;
                        }
                    }

                    if (PersistentDataContainerUtil.hasString(item, "reqClass")) {
                        String reqClassString = PersistentDataContainerUtil.getString(item, "reqClass");
                        RPGClass reqClass = RPGClass.valueOf(reqClassString);
                        if (!rpgClass.equals(reqClass)) {
                            return;
                        }
                    }

                    Material type = item.getType();

                    //manage arrow in offhand
                    if (rpgClass.equals(RPGClass.ARCHER) || rpgClass.equals(RPGClass.HUNTER)) {
                        if (type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                            ItemStack arrow = OtherItems.getArrow(2);
                            inventory.setItemInOffHand(arrow);
                        } else {
                            ItemStack itemInOffHand = inventory.getItemInOffHand();
                            if (itemInOffHand.getType().equals(Material.ARROW)) {
                                inventory.setItemInOffHand(new ItemStack(Material.AIR));
                            }
                        }
                    }

                    //manage stats on hold
                    if (type.equals(Material.DIAMOND_SWORD) || type.equals(Material.DIAMOND_HOE) || type.equals(Material.DIAMOND_SHOVEL) || type.equals(Material.DIAMOND_AXE)
                            || type.equals(Material.DIAMOND_PICKAXE) || type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                        RPGCharacterStats rpgCharacterStats = activeCharacter.getRpgCharacterStats();
                        if (PersistentDataContainerUtil.hasInteger(item, "fire")) {
                            int bonus = PersistentDataContainerUtil.getInteger(item, "fire");
                            rpgCharacterStats.getFire().addBonus(bonus, rpgCharacterStats);
                        }
                        if (PersistentDataContainerUtil.hasInteger(item, "water")) {
                            int bonus = PersistentDataContainerUtil.getInteger(item, "water");
                            rpgCharacterStats.getWater().addBonus(bonus, rpgCharacterStats);
                        }
                        if (PersistentDataContainerUtil.hasInteger(item, "earth")) {
                            int bonus = PersistentDataContainerUtil.getInteger(item, "earth");
                            rpgCharacterStats.getEarth().addBonus(bonus, rpgCharacterStats);
                        }
                        if (PersistentDataContainerUtil.hasInteger(item, "lightning")) {
                            int bonus = PersistentDataContainerUtil.getInteger(item, "lightning");
                            rpgCharacterStats.getLightning().addBonus(bonus, rpgCharacterStats);
                        }
                        if (PersistentDataContainerUtil.hasInteger(item, "wind")) {
                            int bonus = PersistentDataContainerUtil.getInteger(item, "wind");
                            rpgCharacterStats.getWind().addBonus(bonus, rpgCharacterStats);
                        }
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
