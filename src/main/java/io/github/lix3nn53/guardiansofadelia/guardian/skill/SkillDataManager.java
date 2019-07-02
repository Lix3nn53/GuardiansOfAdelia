package io.github.lix3nn53.guardiansofadelia.guardian.skill;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;

public class SkillDataManager {

    private static HashMap<LivingEntity, Integer> casterToCastNumber = new HashMap<>();

    private static HashMap<String, HashSet<String>> castKeyToSkillFlags = new HashMap<>();
    private static HashMap<String, HashSet<BukkitTask>> castKeyToRepeatTasks = new HashMap<>();

    public static int getNextCastNumber(LivingEntity caster) {
        if (casterToCastNumber.containsKey(caster)) {
            int current = casterToCastNumber.get(caster);
            current++;
            casterToCastNumber.put(caster, current);
            return current;
        }

        casterToCastNumber.put(caster, 1);
        return 1;
    }

    public static String getCastKey(LivingEntity caster, int castNumber) {
        int entityId = caster.getEntityId();
        return entityId + "-" + castNumber;
    }

    public static void addFlag(String castKey, String flag) {
        if (castKeyToSkillFlags.containsKey(castKey)) {
            castKeyToSkillFlags.get(castKey).add(flag);
        } else {
            HashSet<String> flags = new HashSet<>();
            flags.add(flag);
            castKeyToSkillFlags.put(castKey, flags);
        }
    }

    public static boolean hasFlag(String castKey, String flag) {
        if (castKeyToSkillFlags.containsKey(castKey)) {
            return castKeyToSkillFlags.get(castKey).contains(flag);
        }
        return false;
    }

    public static void removeFlag(String castKey, String flag) {
        if (castKeyToSkillFlags.containsKey(castKey)) {
            HashSet<String> strings = castKeyToSkillFlags.get(castKey);
            strings.remove(flag);
            if (strings.isEmpty()) {
                castKeyToSkillFlags.remove(castKey);
            }
        }
    }

    public static void clearFlags(String castKey) {
        castKeyToSkillFlags.remove(castKey);
    }

    public static void onRepeatTaskCreate(String castKey, BukkitTask bukkitTask) {
        if (castKeyToRepeatTasks.containsKey(castKey)) {
            castKeyToRepeatTasks.get(castKey).add(bukkitTask);
        } else {
            HashSet<BukkitTask> tasks = new HashSet<>();
            tasks.add(bukkitTask);
            castKeyToRepeatTasks.put(castKey, tasks);
        }
    }

    public static void removeRepeatTask(String castKey, BukkitTask bukkitTask) {
        if (castKeyToRepeatTasks.containsKey(castKey)) {
            HashSet<BukkitTask> tasks = castKeyToRepeatTasks.get(castKey);
            tasks.remove(bukkitTask);
            if (tasks.isEmpty()) {
                castKeyToRepeatTasks.remove(castKey);
            }
        }
    }

    public static void stopRepeatTasksOfCast(String castKey) {
        if (castKeyToRepeatTasks.containsKey(castKey)) {
            HashSet<BukkitTask> tasks = castKeyToRepeatTasks.get(castKey);

            for (BukkitTask bukkitTask : tasks) {
                bukkitTask.cancel();
            }

            castKeyToRepeatTasks.remove(castKey);
        }
    }

    /**
     * clear player cast number
     *
     * @param player
     */
    public static void onPlayerQuit(Player player) {
        casterToCastNumber.remove(player);
    }

    /**
     * clear mob cast number. Do not use for players.
     *
     * @param livingEntity
     */
    public static void onEntityDeath(LivingEntity livingEntity) {
        casterToCastNumber.remove(livingEntity);
    }
}
