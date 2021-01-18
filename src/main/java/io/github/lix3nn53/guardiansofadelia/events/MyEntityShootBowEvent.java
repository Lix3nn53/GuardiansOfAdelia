package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class MyEntityShootBowEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(EntityShootBowEvent event) {
        ItemStack itemInMainHand = event.getBow();
        if (itemInMainHand != null) {
            Material type = itemInMainHand.getType();

            if (type.equals(Material.TRIDENT) || type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();

                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            String rpgClassStr = activeCharacter.getRpgClassStr();

                            if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClassStr)) return;

                            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "rangedDamage")) {
                                int rangedDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "rangedDamage");
                                Entity projectile = event.getProjectile();
                                float force = event.getForce();
                                if (force < 0.7) {
                                    player.sendMessage(ChatColor.RED + "Shooting force must be higher than 0.7 to shoot an arrow");
                                    event.setCancelled(true);
                                    return;
                                }
                                rangedDamage = (int) ((rangedDamage * force) + 0.5);
                                PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, projectile);
                            }

                            //add arrow
                            if (type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                                ItemStack arrow = OtherItems.getArrow(2);
                                player.getInventory().setItemInOffHand(arrow);
                            }
                        }
                    }
                } else {
                    if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "rangedDamage")) {
                        int rangedDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "rangedDamage");
                        Entity projectile = event.getProjectile();
                        PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, projectile);
                    }
                }
            }
        }
    }
}
