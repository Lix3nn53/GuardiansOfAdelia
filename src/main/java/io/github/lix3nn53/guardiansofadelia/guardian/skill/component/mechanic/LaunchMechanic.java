package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class LaunchMechanic extends MechanicComponent {

    private final Relative relative;
    private final List<Double> forwardSpeed;
    private final List<Double> upwardSpeed;
    private final List<Double> rightSpeed;

    public LaunchMechanic(Relative relative, List<Double> forwardSpeed, List<Double> upwardSpeed, List<Double> rightSpeed) {
        this.relative = relative;
        this.forwardSpeed = forwardSpeed;
        this.upwardSpeed = upwardSpeed;
        this.rightSpeed = rightSpeed;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            final Vector dir;

            if (relative.equals(Relative.CASTER)) {
                dir = caster.getLocation().getDirection().setY(0).normalize();
            } else if (relative.equals(Relative.BETWEEN)) {
                dir = ent.getLocation().toVector().subtract(caster.getLocation().toVector()).setY(0).normalize();
            } else {
                dir = ent.getLocation().getDirection().setY(0).normalize();
            }

            final Vector nor = dir.clone().crossProduct(UP);
            dir.multiply(forwardSpeed.get(skillLevel - 1));
            dir.add(nor.multiply(rightSpeed.get(skillLevel - 1))).setY(upwardSpeed.get(skillLevel - 1));

            ent.setVelocity(dir);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.AQUA + "Launch forward: " + forwardSpeed.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch upward: " + upwardSpeed.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch right: " + rightSpeed.get(skillLevel));
        } else if (skillLevel == upwardSpeed.size()) {
            additions.add(ChatColor.AQUA + "Launch forward: " + forwardSpeed.get(skillLevel - 1));
            additions.add(ChatColor.AQUA + "Launch upward: " + upwardSpeed.get(skillLevel - 1));
            additions.add(ChatColor.AQUA + "Launch right: " + rightSpeed.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.AQUA + "Launch forward: " + forwardSpeed.get(skillLevel - 1) + " -> " + forwardSpeed.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch upward: " + upwardSpeed.get(skillLevel - 1) + " -> " + upwardSpeed.get(skillLevel));
            additions.add(ChatColor.AQUA + "Launch right: " + rightSpeed.get(skillLevel - 1) + " -> " + rightSpeed.get(skillLevel));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }

    public enum Relative {
        CASTER,
        BETWEEN,
        TARGET
    }
}
