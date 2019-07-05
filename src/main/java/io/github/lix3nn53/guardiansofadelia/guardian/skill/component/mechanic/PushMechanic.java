package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class PushMechanic extends MechanicComponent {

    private final PushType type;
    private final double speed;

    public PushMechanic(PushType type, double speed) {
        this.type = type;
        this.speed = speed;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        Location center = caster.getLocation();

        boolean worked = false;
        for (LivingEntity target : targets) {
            final Vector vel = target.getLocation().subtract(center).toVector();
            if (vel.lengthSquared() == 0) {
                continue;
            } else if (type.equals(PushType.INVERSE)) {
                vel.multiply(speed);
            } else if (type.equals(PushType.FIXED)) {
                vel.multiply(speed / vel.length());
            } else { // SCALED
                vel.multiply(speed / vel.lengthSquared());
            }
            vel.setY(vel.getY() / 5 + 0.5);
            target.setVelocity(vel);
            worked = true;
        }
        return worked;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }

    public enum PushType {
        FIXED,
        INVERSE,
        SCALED
    }
}
