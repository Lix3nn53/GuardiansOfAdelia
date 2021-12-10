package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;


import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class StatusEffectManager {

    private static final HashMap<LivingEntity, HashMap<StatusEffectType, StatusEffectDuration>> entityToStatusToDuration = new HashMap<>();

    public static void addStatus(LivingEntity target, StatusEffectType effectType, int duration) {
        HashMap<StatusEffectType, StatusEffectDuration> activeStatuses;
        if (entityToStatusToDuration.containsKey(target)) {
            activeStatuses = entityToStatusToDuration.get(target);
        } else {
            activeStatuses = new HashMap<>();
        }

        if (activeStatuses.containsKey(effectType)) {
            StatusEffectDuration statusEffectDuration = activeStatuses.get(effectType);
            int durationLeft = statusEffectDuration.getDurationLeft();
            if (durationLeft >= duration) {
                return; // new effect is shorter so just return
            }

            statusEffectDuration.cancel(); // cancel old timer if new effect duration is longer
        }


        StatusEffectDuration statusEffectDuration = new StatusEffectDuration(effectType, duration);
        statusEffectDuration.startCooldown(target);

        activeStatuses.put(effectType, statusEffectDuration);
        entityToStatusToDuration.put(target, activeStatuses);

        // Custom effects
        if (effectType.equals(StatusEffectType.ROOT) || effectType.equals(StatusEffectType.STUN)) {
            // Custom code for mobs
            if (!(target instanceof Player)) {
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 5));
            }
        }
    }

    public static void removeStatus(LivingEntity livingEntity, StatusEffectType statusEffectType) {
        if (!entityToStatusToDuration.containsKey(livingEntity)) return;

        HashMap<StatusEffectType, StatusEffectDuration> typeToDuration = entityToStatusToDuration.get(livingEntity);

        StatusEffectDuration remove = typeToDuration.remove(statusEffectType);
        if (remove != null) {
            remove.cancel();
        }

        if (typeToDuration.isEmpty()) {
            entityToStatusToDuration.remove(livingEntity);
        }
    }

    public static boolean isSilenced(LivingEntity livingEntity) {
        if (!entityToStatusToDuration.containsKey(livingEntity)) return false;

        HashMap<StatusEffectType, StatusEffectDuration> typeToDuration = entityToStatusToDuration.get(livingEntity);

        if (typeToDuration.containsKey(StatusEffectType.STUN)) {
            if (livingEntity instanceof Player) {
                StatusEffectDuration duration = typeToDuration.get(StatusEffectType.STUN);
                int durationLeft = duration.getDurationLeft();

                Player player = (Player) livingEntity;
                player.sendTitle(ChatColor.WHITE + "", ChatPalette.RED + "Stunned for " + durationLeft, 0, 20, 0);
            }

            return true;
        } else if (typeToDuration.containsKey(StatusEffectType.SILENCE)) {
            if (livingEntity instanceof Player) {
                StatusEffectDuration duration = typeToDuration.get(StatusEffectType.SILENCE);
                int durationLeft = duration.getDurationLeft();

                Player player = (Player) livingEntity;
                player.sendTitle(ChatColor.WHITE + "", ChatPalette.RED + "Silenced for " + durationLeft, 0, 20, 0);
            }

            return true;
        }

        return false;
    }

    public static boolean isDisarmed(LivingEntity livingEntity) {
        if (!entityToStatusToDuration.containsKey(livingEntity)) return false;

        HashMap<StatusEffectType, StatusEffectDuration> typeToDuration = entityToStatusToDuration.get(livingEntity);

        if (typeToDuration.containsKey(StatusEffectType.STUN)) {
            if (livingEntity instanceof Player) {
                StatusEffectDuration duration = typeToDuration.get(StatusEffectType.STUN);
                int durationLeft = duration.getDurationLeft();

                Player player = (Player) livingEntity;
                player.sendTitle(ChatColor.WHITE + "", ChatPalette.RED + "Stunned for " + durationLeft, 0, 20, 0);
            }

            return true;
        } else if (typeToDuration.containsKey(StatusEffectType.DISARM)) {
            if (livingEntity instanceof Player) {
                StatusEffectDuration duration = typeToDuration.get(StatusEffectType.DISARM);
                int durationLeft = duration.getDurationLeft();

                Player player = (Player) livingEntity;
                player.sendTitle(ChatColor.WHITE + "", ChatPalette.RED + "Disarmed for " + durationLeft, 0, 20, 0);
            }

            return true;
        }

        return false;
    }

    public static boolean isRooted(LivingEntity livingEntity) {
        if (!entityToStatusToDuration.containsKey(livingEntity)) return false;

        HashMap<StatusEffectType, StatusEffectDuration> typeToDuration = entityToStatusToDuration.get(livingEntity);

        if (typeToDuration.containsKey(StatusEffectType.STUN)) {
            if (livingEntity instanceof Player) {
                StatusEffectDuration duration = typeToDuration.get(StatusEffectType.STUN);
                int durationLeft = duration.getDurationLeft();

                Player player = (Player) livingEntity;
                player.sendTitle(ChatColor.WHITE + "", ChatPalette.RED + "Stunned for " + durationLeft, 0, 20, 0);
            }

            return true;
        } else if (typeToDuration.containsKey(StatusEffectType.ROOT)) {
            if (livingEntity instanceof Player) {
                StatusEffectDuration duration = typeToDuration.get(StatusEffectType.ROOT);
                int durationLeft = duration.getDurationLeft();

                Player player = (Player) livingEntity;
                player.sendTitle(ChatColor.WHITE + "", ChatPalette.RED + "Rooted for " + durationLeft, 0, 20, 0);
            }

            return true;
        }

        return false;
    }
}
