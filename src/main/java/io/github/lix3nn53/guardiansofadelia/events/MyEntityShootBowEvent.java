package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGClass;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MyEntityShootBowEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(EntityShootBowEvent event) {
        ItemStack itemInMainHand = event.getBow();
        if (itemInMainHand != null) {
            Material type = itemInMainHand.getType();

            if (type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();

                    UUID uniqueId = player.getUniqueId();
                    if (GuardianDataManager.hasGuardianData(uniqueId)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(uniqueId);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "reqLevel")) {
                                int reqLevel = PersistentDataContainerUtil.getInteger(itemInMainHand, "reqLevel");
                                if (player.getLevel() < reqLevel) {
                                    event.setCancelled(true);
                                    player.sendMessage("Required level for this weapon is " + reqLevel);
                                    return;
                                }
                            }

                            RPGClass rpgClass = activeCharacter.getRpgClass();

                            if (PersistentDataContainerUtil.hasString(itemInMainHand, "reqClass")) {
                                String reqClassString = PersistentDataContainerUtil.getString(itemInMainHand, "reqClass");
                                RPGClass reqClass = RPGClass.valueOf(reqClassString);
                                if (!rpgClass.equals(reqClass)) {
                                    event.setCancelled(true);
                                    player.sendMessage("Required class for this weapon is " + reqClass.getClassString());
                                    return;
                                }
                            }

                            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "rangedDamage")) {
                                int rangedDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "rangedDamage");
                                Entity projectile = event.getProjectile();
                                float force = event.getForce();
                                rangedDamage = (int) ((rangedDamage * force) + 0.5);
                                PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, projectile);
                            }
                        }
                    }
                } else {
                    if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "rangedDamage")) {
                        int rangedDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "rangedDamage");
                        Entity projectile = event.getProjectile();
                        float force = event.getForce();
                        rangedDamage = (int) ((rangedDamage * force) + 0.5);
                        PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, projectile);
                    }
                }
            }
        }
    }
}
