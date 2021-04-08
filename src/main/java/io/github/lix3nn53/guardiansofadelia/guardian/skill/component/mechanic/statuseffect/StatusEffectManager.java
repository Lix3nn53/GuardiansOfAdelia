package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatusEffectManager {

    private static final HashMap<LivingEntity, List<StatusEffectType>> entityToEffects = new HashMap<>();

    public static void addStatus(LivingEntity livingEntity, StatusEffectType statusEffectType) {
        List<StatusEffectType> statusEffectTypes;

        if (entityToEffects.containsKey(livingEntity)) {
            statusEffectTypes = entityToEffects.get(livingEntity);
        } else {
            statusEffectTypes = new ArrayList<>();
        }

        statusEffectTypes.add(statusEffectType);

        entityToEffects.put(livingEntity, statusEffectTypes);
    }

    public static void removeStatus(LivingEntity livingEntity, StatusEffectType statusEffectType) {
        if (!entityToEffects.containsKey(livingEntity)) return;

        List<StatusEffectType> statusEffectTypes = entityToEffects.get(livingEntity);

        statusEffectTypes.remove(statusEffectType);

        if (statusEffectTypes.isEmpty()) {
            entityToEffects.remove(livingEntity);
        }
    }

    public static boolean isSilenced(LivingEntity livingEntity) {
        if (!entityToEffects.containsKey(livingEntity)) return false;

        List<StatusEffectType> statusEffectTypes = entityToEffects.get(livingEntity);

        if (statusEffectTypes.contains(StatusEffectType.STUN)) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                player.sendTitle("", ChatColor.RED + "Stunned..", 0, 20, 0);
            }

            return true;
        } else if (statusEffectTypes.contains(StatusEffectType.SILENCE)) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                player.sendTitle("", ChatColor.RED + "Silenced..", 0, 20, 0);
            }

            return true;
        }

        return false;
    }

    public static boolean isDisarmed(LivingEntity livingEntity) {
        if (!entityToEffects.containsKey(livingEntity)) return false;

        List<StatusEffectType> statusEffectTypes = entityToEffects.get(livingEntity);

        if (statusEffectTypes.contains(StatusEffectType.STUN)) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                player.sendTitle("", ChatColor.RED + "Stunned..", 0, 20, 0);
            }

            return true;
        } else if (statusEffectTypes.contains(StatusEffectType.DISARM)) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                player.sendTitle("", ChatColor.RED + "Disarmed..", 0, 20, 0);
            }

            return true;
        }

        return false;
    }

    public static boolean isRooted(LivingEntity livingEntity) {
        if (!entityToEffects.containsKey(livingEntity)) return false;

        List<StatusEffectType> statusEffectTypes = entityToEffects.get(livingEntity);

        if (statusEffectTypes.contains(StatusEffectType.STUN)) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                player.sendTitle("", ChatColor.RED + "Stunned..", 0, 20, 0);
            }

            return true;
        } else if (statusEffectTypes.contains(StatusEffectType.ROOT)) {
            if (livingEntity instanceof Player) {
                Player player = (Player) livingEntity;
                player.sendTitle("", ChatColor.RED + "Rooted..", 0, 20, 0);
            }

            return true;
        }

        return false;
    }
}
