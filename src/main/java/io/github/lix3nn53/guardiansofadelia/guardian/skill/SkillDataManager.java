package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;

public class SkillDataManager {

    private static HashMap<LivingEntity, Integer> keyEntityToValue = new HashMap<>(); //currently only one value for one entity
    private static HashMap<LivingEntity, HashSet<String>> keyEntityToSkillFlags = new HashMap<>();
    private static HashMap<LivingEntity, HashMap<Integer, BukkitTask>> keyEntityToCastCounterToRepeatTask = new HashMap<>();

    public static void setValue(LivingEntity keyEntity, int value) {
        keyEntityToValue.put(keyEntity, value);
    }

    public static void addValue(LivingEntity keyEntity, int value) {
        int oldValue = 0;
        if (keyEntityToValue.containsKey(keyEntity)) {
            oldValue = keyEntityToValue.get(keyEntity);
        }
        keyEntityToValue.put(keyEntity, oldValue + value);
    }

    public static int getValue(LivingEntity keyEntity) {
        if (keyEntityToValue.containsKey(keyEntity)) {
            return keyEntityToValue.get(keyEntity);
        }
        return 0;
    }

    public static void addFlag(LivingEntity keyEntity, String flag) {
        if (keyEntityToSkillFlags.containsKey(keyEntity)) {
            keyEntityToSkillFlags.get(keyEntity).add(flag);
        } else {
            HashSet<String> flags = new HashSet<>();
            flags.add(flag);
            keyEntityToSkillFlags.put(keyEntity, flags);
        }
    }

    public static boolean hasFlag(LivingEntity keyEntity, String flag) {
        if (keyEntityToSkillFlags.containsKey(keyEntity)) {
            return keyEntityToSkillFlags.get(keyEntity).contains(flag);
        }
        return false;
    }

    public static void removeFlag(LivingEntity keyEntity, String flag) {
        if (keyEntityToSkillFlags.containsKey(keyEntity)) {
            HashSet<String> strings = keyEntityToSkillFlags.get(keyEntity);
            strings.remove(flag);
            if (strings.isEmpty()) {
                keyEntityToSkillFlags.remove(keyEntity);
            }
        }
    }

    public static void clearFlags(LivingEntity keyEntity) {
        keyEntityToSkillFlags.remove(keyEntity);
    }

    public static void onRepeatTaskCreate(LivingEntity keyEntity, BukkitTask bukkitTask, int castCounter) {
        if (keyEntityToCastCounterToRepeatTask.containsKey(keyEntity)) {
            HashMap<Integer, BukkitTask> hashMap = keyEntityToCastCounterToRepeatTask.get(keyEntity);
            hashMap.put(castCounter, bukkitTask);
        } else {
            HashMap<Integer, BukkitTask> hashMap = new HashMap<>();
            hashMap.put(castCounter, bukkitTask);
            keyEntityToCastCounterToRepeatTask.put(keyEntity, hashMap);
        }
    }

    public static void removeRepeatTask(LivingEntity keyEntity, int castCounter) {
        if (keyEntityToCastCounterToRepeatTask.containsKey(keyEntity)) {
            HashMap<Integer, BukkitTask> hashMap = keyEntityToCastCounterToRepeatTask.get(keyEntity);
            hashMap.remove(castCounter);
            if (hashMap.isEmpty()) {
                keyEntityToCastCounterToRepeatTask.remove(keyEntity);
            }
        }
    }

    public static void stopRepeatTasksOfCast(LivingEntity keyEntity, int castCounter) {
        if (keyEntityToCastCounterToRepeatTask.containsKey(keyEntity)) {
            HashMap<Integer, BukkitTask> hashMap = keyEntityToCastCounterToRepeatTask.get(keyEntity);

            if (hashMap.containsKey(castCounter)) {
                BukkitTask bukkitTask = hashMap.get(castCounter);
                bukkitTask.cancel();

                hashMap.remove(castCounter);

                if (hashMap.isEmpty()) {
                    keyEntityToCastCounterToRepeatTask.remove(keyEntity);
                }
            }
        }
    }

    /**
     * clear player cast number
     *
     * @param player
     */
    public static void onPlayerQuit(Player player) {
        keyEntityToSkillFlags.remove(player);
        keyEntityToCastCounterToRepeatTask.remove(player);
        keyEntityToValue.remove(player);
    }

    /**
     * clear mob cast number. Do not use for players.
     *
     * @param livingEntity
     */
    public static void onEntityDeath(LivingEntity livingEntity) {
        keyEntityToSkillFlags.remove(livingEntity);
        keyEntityToCastCounterToRepeatTask.remove(livingEntity);
        keyEntityToValue.remove(livingEntity);
    }
}
