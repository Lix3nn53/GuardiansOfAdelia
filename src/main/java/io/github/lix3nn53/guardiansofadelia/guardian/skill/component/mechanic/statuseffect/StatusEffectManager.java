package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StatusEffectManager {

    public static List<LivingEntity> silenced = new ArrayList<>();
    public static List<LivingEntity> disarmed = new ArrayList<>();
    public static List<Player> rootedXZ = new ArrayList<>();
    public static List<Player> rootedY = new ArrayList<>();

    public static boolean isSilenced(LivingEntity livingEntity) {
        return silenced.contains(livingEntity);
    }

    public static boolean setSilenced(LivingEntity livingEntity) {
        return silenced.add(livingEntity);
    }

    public static boolean removeSilenced(LivingEntity livingEntity) {
        return silenced.remove(livingEntity);
    }

    public static boolean isDisarmed(LivingEntity livingEntity) {
        return disarmed.contains(livingEntity);
    }

    public static boolean setDisarmed(LivingEntity livingEntity) {
        return disarmed.add(livingEntity);
    }

    public static boolean removeDisarmed(LivingEntity livingEntity) {
        return disarmed.remove(livingEntity);
    }

    public static boolean isRootedY(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player)) return false;

        return rootedY.contains(livingEntity);
    }

    public static boolean isRootedXZ(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player)) return false;

        return rootedXZ.contains(livingEntity);
    }

    public static boolean setRooted(LivingEntity livingEntity, boolean lockY) {
        if (!(livingEntity instanceof Player)) return false;

        if (lockY) rootedY.add((Player) livingEntity);
        return rootedXZ.add((Player) livingEntity);
    }

    public static boolean removeRooted(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player)) return false;

        rootedY.remove(livingEntity);
        return rootedXZ.remove(livingEntity);
    }
}
