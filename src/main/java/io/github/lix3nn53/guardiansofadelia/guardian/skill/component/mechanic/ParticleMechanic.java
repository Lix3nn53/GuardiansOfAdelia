package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleMechanic extends MechanicComponent {

    private static final Vector UP = new Vector(0, 1, 0);

    private final Particle particle;
    private final ArrangementParticle arrangement;
    private final double radiusParticle;
    private final int amountParticle;

    private final double dx;
    private final double dy;
    private final double dz;

    private final double forward;
    private final double upward;
    private final double right;

    private final double speed;

    public ParticleMechanic(Particle particle, ArrangementParticle arrangement, double radiusParticle, int amountParticle, double dx, double dy, double dz, double forward, double upward, double right, double speed) {
        this.particle = particle;
        this.arrangement = arrangement;
        this.radiusParticle = radiusParticle;
        this.amountParticle = amountParticle;
        this.forward = forward;
        this.upward = upward;
        this.right = right;
        this.speed = speed;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            Location location = ent.getLocation();

            Vector dir = location.getDirection().setY(0).normalize();
            Vector side = dir.clone().crossProduct(UP);
            location.add(dir.multiply(forward)).add(0, upward, 0).add(side.multiply(right));

            ParticleUtil.play(location, particle, arrangement, radiusParticle, amountParticle, Direction.XZ, dx, dy, dz, speed);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
