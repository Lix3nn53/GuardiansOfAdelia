package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
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
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {
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
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        if (skillLevel == 0 || skillLevel == forwardSpeed.size()) {
            lore.add("Forward speed: " + forwardSpeed.get(skillLevel));
            lore.add("Upward speed: " + upwardSpeed.get(skillLevel));
        } else {
            lore.add("Forward speed: " + forwardSpeed.get(skillLevel - 1) + " -> " + forwardSpeed.get(skillLevel));
            lore.add("Upward speed: " + upwardSpeed.get(skillLevel - 1) + " -> " + upwardSpeed.get(skillLevel));
        }
        return lore;
    }

    public enum Relative {
        CASTER,
        BETWEEN,
        TARGET
    }
}
