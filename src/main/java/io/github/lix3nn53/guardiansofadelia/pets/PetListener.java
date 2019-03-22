package io.github.lix3nn53.guardiansofadelia.pets;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;

public class PetListener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) event.getEntity();
            PetManager petManager = GuardiansOfAdelia.getPetManager();
            if (petManager.isPet(livingEntity)) {
                Player owner = petManager.getOwner(livingEntity);

                // Check if the entity has been killed by another entity
                if (event instanceof EntityDamageByEntityEvent) {

                    Entity killerEntity = ((EntityDamageByEntityEvent) event).getDamager();

                    // Check if the damager was a player
                    if (killerEntity instanceof Player) {
                        Player damager = (Player) killerEntity;
                        if (damager == owner) {
                            event.setCancelled(true);
                            return;
                        }
                    }

                    // If the damager was a projectile, take its shooter
                    else if (killerEntity instanceof Projectile) {
                        ProjectileSource shooter = ((Projectile) killerEntity).getShooter();
                        if (shooter instanceof LivingEntity) {
                            if (shooter instanceof Player) {
                                Player damager = (Player) shooter;
                                if (damager == owner) {
                                    event.setCancelled(true);
                                    return;
                                }
                            }
                        }
                    }
                }

                if (event.getFinalDamage() >= livingEntity.getHealth()) {
                    //livingEntity death
                    event.setCancelled(true);
                    livingEntity.remove();

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            GuardiansOfAdelia.getPetManager().spawnPet(owner);
                        }
                    }.runTaskLater(GuardiansOfAdelia.getInstance(), petManager.getRespawnDelay());
                } else {
                    //update health on custom name
                    int oldHealth = (int) (livingEntity.getHealth() + 0.5);
                    String oldHealthString = oldHealth + "/";

                    int newHealth = (int) (oldHealth - event.getFinalDamage() + 0.5);
                    String newHealthString = newHealth + "/";

                    String customName = livingEntity.getCustomName();
                    String replace = customName.replace(oldHealthString, newHealthString);
                    livingEntity.setCustomName(replace);
                }
            } else if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                if (petManager.isPetOwner(player)) {
                    //set pet target

                }
            }
        }
    }
}
