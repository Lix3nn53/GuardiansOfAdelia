package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LaunchMechanic extends MechanicComponent {

    private final Relative relative;
    private final double forwardSpeed;
    private final double upwardSpeed;
    private final double rightSpeed;
    private final double upwardPerLevel;
    private Vector up = new Vector(0, 1, 0);

    public LaunchMechanic(Relative relative, double forwardSpeed, double upwardSpeed, double rightSpeed, double upwardPerLevel) {
        this.relative = relative;
        this.forwardSpeed = forwardSpeed;
        this.upwardSpeed = upwardSpeed;
        this.rightSpeed = rightSpeed;
        this.upwardPerLevel = upwardPerLevel;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        double upwardSpeedToUse = upwardSpeed + (upwardPerLevel * skillLevel) - upwardPerLevel;

        for (LivingEntity ent : targets) {
            final Vector dir;

            if (relative.equals(Relative.CASTER)) {
                dir = caster.getLocation().getDirection().setY(0).normalize();
            } else if (relative.equals(Relative.BETWEEN)) {
                dir = ent.getLocation().toVector().subtract(caster.getLocation().toVector()).setY(0).normalize();
            } else {
                dir = ent.getLocation().getDirection().setY(0).normalize();
            }

            final Vector nor = dir.clone().crossProduct(up);
            dir.multiply(forwardSpeed);
            dir.add(nor.multiply(rightSpeed)).setY(upwardSpeedToUse);

            ent.setVelocity(dir);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }

    public enum Relative {
        CASTER,
        BETWEEN,
        TARGET
    }
}
