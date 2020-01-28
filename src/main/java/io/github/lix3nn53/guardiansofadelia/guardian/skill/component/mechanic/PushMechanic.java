package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class PushMechanic extends MechanicComponent {

    private final PushType type;
    private final List<Double> speed;
    private final boolean centerSelf;

    /**
     * @param type
     * @param speed
     * @param centerSelf center = caster, if false: center = first target
     */
    public PushMechanic(PushType type, List<Double> speed, boolean centerSelf) {
        this.type = type;
        this.speed = speed;
        this.centerSelf = centerSelf;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        Location center;
        if (centerSelf) {
            center = caster.getLocation();
        } else {
            center = targets.get(0).getLocation();
            targets.remove(0);
        }

        for (LivingEntity target : targets) {
            final Vector vel = target.getLocation().subtract(center).toVector();
            if (vel.lengthSquared() == 0) {
                continue;
            } else if (type.equals(PushType.INVERSE)) {
                vel.multiply(speed.get(skillLevel - 1));
            } else if (type.equals(PushType.FIXED)) {
                vel.multiply(speed.get(skillLevel - 1) / vel.length());
            } else { // SCALED
                vel.multiply(speed.get(skillLevel - 1) / vel.lengthSquared());
            }
            target.setVelocity(vel);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.AQUA + "Push speed: " + speed.get(skillLevel));
        } else if (skillLevel == speed.size()) {
            additions.add(ChatColor.AQUA + "Push speed: " + speed.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.AQUA + "Push speed: " + speed.get(skillLevel - 1) + " -> " + speed.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public enum PushType {
        FIXED,
        INVERSE,
        SCALED
    }
}
