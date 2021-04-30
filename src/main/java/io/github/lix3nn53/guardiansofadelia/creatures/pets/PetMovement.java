package io.github.lix3nn53.guardiansofadelia.creatures.pets;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.Skill;
import io.github.lix3nn53.guardiansofadelia.utilities.EntityUtils;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PetMovement {

    public static void onPetSpawn(Player player, String petCode, LivingEntity pet, int level) {
        PetData petData = PetSkillManager.getPetData(petCode);
        int speed = petData.getSpeed();
        int range = petData.getRange();
        PotionEffect potionEffect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, speed, false, false);
        player.addPotionEffect(potionEffect);

        new BukkitRunnable() {

            Vector previousDirection = new Vector();

            @Override
            public void run() {
                if (!pet.isValid()) {
                    cancel();
                    return;
                }

                Location target = player.getLocation(); // Target location to follow
                Location start = pet.getLocation(); // Current location

                if (!PetManager.petSkillOnCooldown.contains(player)) {
                    List<Entity> nearbyEntities = pet.getNearbyEntities(range, range, range);
                    LivingEntity targetEnemy = determineTarget(player, nearbyEntities);
                    if (targetEnemy != null) {
                        double height = targetEnemy.getHeight() / 2;
                        Location enemyLocation = targetEnemy.getLocation().add(0, height, 0);
                        Vector vectorBetweenPoints = enemyLocation.toVector().subtract(start.toVector());
                        start.setDirection(vectorBetweenPoints);
                        pet.teleport(start);

                        Skill skill = petData.getSkill(level);
                        if (skill != null) {
                            ArrayList<LivingEntity> targets = new ArrayList<>();
                            targets.add(pet);
                            boolean cast = skill.cast(player, 1, targets, 1, 999);

                            if (cast) {
                                PetManager.petSkillOnCooldown.add(player);

                                int cooldown = skill.getCooldown(0);
                                new BukkitRunnable() {

                                    @Override
                                    public void run() {
                                        PetManager.petSkillOnCooldown.remove(player);
                                    }
                                }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), cooldown);

                                PetExperienceManager.giveExperienceToActivePet(player, 1);
                            }

                            return;
                        }
                    }
                }

                // We don't want pets get too close to players so lets add offset
                // Start - Calculate offset
                Vector dirOfTarget = target.getDirection();
                dirOfTarget.setY(0);
                Vector side = dirOfTarget.clone().crossProduct(new Vector(0, 1, 0));
                Vector upward = dirOfTarget.clone().crossProduct(side);
                target.add(dirOfTarget.multiply(-0.8)).subtract(upward.multiply(1.6)).add(side.multiply(1.2));
                // End - Calculate offset

                Vector vectorBetweenPoints = target.toVector().subtract(start.toVector());
                double distance = vectorBetweenPoints.length();

                if (distance < 0.2) return; // Pet is close enough to target location
                if (distance > 24) {
                    pet.teleport(target);
                    return;
                }
                Vector direction = vectorBetweenPoints.normalize();

                Vector nextDirection = previousDirection.add(direction); // Add previousDirection to direction so pet makes a smooth turn
                nextDirection.normalize();

                double travel = distance / 20D; // Use distances to calculate how fast a pet will travel. Longer the distance, faster the pet.
                start.add(nextDirection.multiply(travel));

                start.setDirection(direction); // Make pet look at the same direction as player

                pet.teleport(start); // Finally, teleport the pet

                previousDirection = nextDirection; // Update previousDirection
            }
        }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, 1L);
    }

    protected static LivingEntity determineTarget(final LivingEntity caster, final List<Entity> targets) {
        for (Entity target : targets) {
            if (!(target instanceof LivingEntity)) continue;
            LivingEntity living = (LivingEntity) target;
            if (isValidTarget(caster, living)) {
                return living;
            }
        }

        return null;
    }

    private static boolean isValidTarget(final LivingEntity caster, final LivingEntity target) {
        if (target == null) return false;
        if (CitizensAPI.getNPCRegistry().isNPC(target)) return false;
        EntityType type = target.getType();
        if (type.equals(EntityType.ARMOR_STAND)) {
            return false;
        }

        if (caster == target) return false;

        return EntityUtils.canAttack(caster, target);
    }
}
