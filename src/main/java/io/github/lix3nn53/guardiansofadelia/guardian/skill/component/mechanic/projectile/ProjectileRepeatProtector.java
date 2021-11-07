package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.projectile;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.utilities.PersistentDataContainerUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

/**
 * Block projectiles fired from same skill-cast to work multiple times on a target
 */
public class ProjectileRepeatProtector {

    private static final HashMap<Entity, String> livingEntityToTookDamageProjectileSkillKey = new HashMap<Entity, String>();

    public static boolean shouldSkillWorkOnProjectileHitToEntity(Entity target, Projectile projectile) {
        if (PersistentDataContainerUtil.hasString(projectile, "skillCastKey")) {
            String skillCastKey = PersistentDataContainerUtil.getString(projectile, "skillCastKey");

            if (livingEntityToTookDamageProjectileSkillKey.containsKey(target)) { //check value if contains
                String s = livingEntityToTookDamageProjectileSkillKey.get(target);
                return !s.equals(skillCastKey);
            } else { //add value and remove after 2 seconds if does not contain already
                livingEntityToTookDamageProjectileSkillKey.put(target, skillCastKey);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        livingEntityToTookDamageProjectileSkillKey.remove(target);
                    }
                }.runTaskLaterAsynchronously(GuardiansOfAdelia.getInstance(), 40);

                return true;
            }
        }

        return true; //return true if there is no skillCastKey
    }
}
