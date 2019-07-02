package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.immunity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImmunityListener {

    private static HashMap<LivingEntity, List<EntityDamageEvent.DamageCause>> entityToImmunity = new HashMap<>();

    public static void addImmunity(LivingEntity livingEntity, EntityDamageEvent.DamageCause damageCause) {
        if (entityToImmunity.containsKey(livingEntity)) {
            entityToImmunity.get(livingEntity).add(damageCause);
        } else {
            List<EntityDamageEvent.DamageCause> damageCauses = new ArrayList<>();
            damageCauses.add(damageCause);
            entityToImmunity.put(livingEntity, damageCauses);
        }
    }

    public static void removeImmunity(LivingEntity livingEntity, EntityDamageEvent.DamageCause damageCause) {
        if (entityToImmunity.containsKey(livingEntity)) {
            List<EntityDamageEvent.DamageCause> damageCauses = entityToImmunity.get(livingEntity);
            damageCauses.remove(damageCause);
            if (damageCauses.isEmpty()) entityToImmunity.remove(livingEntity);
        }
    }

    public static boolean isImmune(LivingEntity livingEntity, EntityDamageEvent.DamageCause damageCause) {
        if (entityToImmunity.containsKey(livingEntity)) {
            List<EntityDamageEvent.DamageCause> damageCauses = entityToImmunity.get(livingEntity);
            return damageCauses.contains(damageCause);
        }
        return false;
    }
}
