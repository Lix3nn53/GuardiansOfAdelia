package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.list.OtherItems;
import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
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

            if (type.equals(Material.BOW) || type.equals(Material.CROSSBOW)) {
                if (event.getEntity() instanceof Player) {
                    Player player = (Player) event.getEntity();

                    if (GuardianDataManager.hasGuardianData(player)) {
                        GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                        if (guardianData.hasActiveCharacter()) {
                            RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                            String rpgClassStr = activeCharacter.getRpgClassStr();

                            if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClassStr)) return;

                            if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "elementDamage")) {
                                int elementDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "elementDamage");
                                Entity projectile = event.getProjectile();
                                float force = event.getForce();
                                if (force < 0.7) {
                                    player.sendMessage(ChatColor.RED + "Shooting force must be > 0.7 (" + force + ")");
                                    event.setCancelled(true);
                                    return;
                                }
                                elementDamage = (int) ((elementDamage * force) + 0.5);
                                PersistentDataContainerUtil.putInteger("rangedDamage", elementDamage, projectile);
                            }

                            //add arrow
                            ItemStack arrow = OtherItems.getArrow(2);
                            player.getInventory().setItemInOffHand(arrow);

                            Entity projectile = event.getProjectile();
                            if (type.equals(Material.CROSSBOW)) {
                                if (projectile instanceof Arrow) {
                                    TriggerListener.onPlayerShootCrossbow(player, (Arrow) projectile);
                                }
                            }
                        }
                    }
                }/* else {
                    if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "elementDamage")) {
                        int elementDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "elementDamage");
                        Entity projectile = event.getProjectile();
                        PersistentDataContainerUtil.putInteger("rangedDamage", elementDamage, projectile);
                    }
                }*/
            }
        }
    }
}
