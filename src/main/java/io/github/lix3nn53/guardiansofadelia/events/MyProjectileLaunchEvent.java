package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.Items.stats.StatUtils;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect.StatusEffectManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger.TriggerListener;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import io.github.lix3nn53.guardiansofadelia.utilities.managers.PlayerTridentThrowManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class MyProjectileLaunchEvent implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEvent(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        ProjectileSource shooter = projectile.getShooter();

        if (shooter instanceof LivingEntity) {
            if (StatusEffectManager.isDisarmed((LivingEntity) shooter)) {
                event.setCancelled(true);
                if (shooter instanceof Player) {
                    Player player = (Player) shooter;

                    player.sendTitle("", ChatColor.RED + "Disarmed..", 0, 20, 0);
                }
                return;
            }
        }

        if (shooter instanceof Player) {
            Player player = (Player) shooter;

            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material type = itemInMainHand.getType();

            if (type.equals(Material.TRIDENT)) {
                //trident return cooldown
                if (PlayerTridentThrowManager.canThrow(player)) {
                    PlayerTridentThrowManager.onPlayerTridentThrow(player);
                    player.getInventory().setItemInMainHand(itemInMainHand);
                } else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "You can't throw your spear again before it returns back");
                    return;
                }

                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                        String rpgClassStr = activeCharacter.getRpgClassStr();

                        if (!StatUtils.doesCharacterMeetRequirements(itemInMainHand, player, rpgClassStr)) return;

                        if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "rangedDamage")) {
                            int rangedDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "rangedDamage");
                            PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, projectile);
                        }
                    }
                }
            } else if (type.equals(Material.CROSSBOW)) {
                if (projectile instanceof Arrow) {
                    TriggerListener.onPlayerShootCrossbow(player, (Arrow) projectile);
                }
            }
        } else if (shooter instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) shooter;

            ItemStack itemInMainHand = livingEntity.getEquipment().getItemInMainHand();
            Material type = itemInMainHand.getType();

            if (type.equals(Material.TRIDENT)) {
                if (PersistentDataContainerUtil.hasInteger(itemInMainHand, "rangedDamage")) {
                    int rangedDamage = PersistentDataContainerUtil.getInteger(itemInMainHand, "rangedDamage");
                    PersistentDataContainerUtil.putInteger("rangedDamage", rangedDamage, projectile);
                }
            }
        }
    }

}
