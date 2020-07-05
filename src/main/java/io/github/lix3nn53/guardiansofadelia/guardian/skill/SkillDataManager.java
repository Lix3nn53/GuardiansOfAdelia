package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SkillDataManager {

    private static HashMap<LivingEntity, Integer> keyEntityToValue = new HashMap<>(); //currently only one value for one entity
    private static HashMap<LivingEntity, HashSet<String>> keyEntityToSkillFlags = new HashMap<>();
    private static HashMap<LivingEntity, HashMap<Integer, BukkitTask>> keyEntityPlusCastCounterToRepeatTask = new HashMap<>();
    private static HashMap<LivingEntity, HashMap<Integer, List<Entity>>> keyEntityPlusCastCounterToSavedEntities = new HashMap<>();

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
        if (keyEntityPlusCastCounterToRepeatTask.containsKey(keyEntity)) {
            HashMap<Integer, BukkitTask> hashMap = keyEntityPlusCastCounterToRepeatTask.get(keyEntity);
            hashMap.put(castCounter, bukkitTask);
        } else {
            HashMap<Integer, BukkitTask> hashMap = new HashMap<>();
            hashMap.put(castCounter, bukkitTask);
            keyEntityPlusCastCounterToRepeatTask.put(keyEntity, hashMap);
        }
    }

    public static void removeRepeatTask(LivingEntity keyEntity, int castCounter) {
        if (keyEntityPlusCastCounterToRepeatTask.containsKey(keyEntity)) {
            HashMap<Integer, BukkitTask> hashMap = keyEntityPlusCastCounterToRepeatTask.get(keyEntity);
            hashMap.remove(castCounter);
            if (hashMap.isEmpty()) {
                keyEntityPlusCastCounterToRepeatTask.remove(keyEntity);
            }
        }
    }

    public static void stopRepeatTasksOfCast(LivingEntity keyEntity, int castCounter) {
        if (keyEntityPlusCastCounterToRepeatTask.containsKey(keyEntity)) {
            HashMap<Integer, BukkitTask> hashMap = keyEntityPlusCastCounterToRepeatTask.get(keyEntity);

            if (hashMap.containsKey(castCounter)) {
                BukkitTask bukkitTask = hashMap.get(castCounter);
                bukkitTask.cancel();

                hashMap.remove(castCounter);

                if (hashMap.isEmpty()) {
                    keyEntityPlusCastCounterToRepeatTask.remove(keyEntity);
                }
            }
        }
    }

    public static void onSkillEntityCreateWithSaveOption(LivingEntity keyEntity, Entity created, int castCounter) {
        if (keyEntityPlusCastCounterToSavedEntities.containsKey(keyEntity)) {
            HashMap<Integer, List<Entity>> hashMap = keyEntityPlusCastCounterToSavedEntities.get(keyEntity);

            List<Entity> entities;
            if (hashMap.containsKey(castCounter)) {
                entities = hashMap.get(castCounter);
            } else {
                entities = new ArrayList<>();
            }

            entities.add(created);
            hashMap.put(castCounter, entities);
        } else {
            HashMap<Integer, List<Entity>> hashMap = new HashMap<>();
            List<Entity> entities = new ArrayList<>();
            entities.add(created);
            hashMap.put(castCounter, entities);
            keyEntityPlusCastCounterToSavedEntities.put(keyEntity, hashMap);
        }
    }

    public static void removeSavedEntities(LivingEntity keyEntity, int castCounter) {
        if (keyEntityPlusCastCounterToSavedEntities.containsKey(keyEntity)) {
            HashMap<Integer, List<Entity>> hashMap = keyEntityPlusCastCounterToSavedEntities.get(keyEntity);
            List<Entity> removedEntities = hashMap.remove(castCounter);
            for (Entity removed : removedEntities) {
                removed.remove();
            }
            if (hashMap.isEmpty()) {
                keyEntityPlusCastCounterToSavedEntities.remove(keyEntity);
            }
        }
    }

    public static void removeSavedEntity(LivingEntity keyEntity, int castCounter, Entity toRemove) {
        if (keyEntityPlusCastCounterToSavedEntities.containsKey(keyEntity)) {
            HashMap<Integer, List<Entity>> hashMap = keyEntityPlusCastCounterToSavedEntities.get(keyEntity);
            List<Entity> entities = hashMap.get(castCounter);
            if (entities.contains(toRemove)) {
                entities.remove(toRemove);
                toRemove.remove();
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
        keyEntityPlusCastCounterToRepeatTask.remove(player);
        keyEntityToValue.remove(player);
    }

    /**
     * clear mob cast number. Do not use for players.
     *
     * @param livingEntity
     */
    public static void onEntityDeath(LivingEntity livingEntity) {
        keyEntityToSkillFlags.remove(livingEntity);
        keyEntityPlusCastCounterToRepeatTask.remove(livingEntity);
        keyEntityToValue.remove(livingEntity);
    }
}
