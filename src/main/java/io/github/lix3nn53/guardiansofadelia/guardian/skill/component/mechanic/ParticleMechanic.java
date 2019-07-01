package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ArrangementParticle;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.Direction;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class ParticleMechanic extends MechanicComponent {

    private final Particle particle;
    private final ArrangementParticle arrangement;
    private final double radiusParticle;
    private final int amountParticle;

    public ParticleMechanic(Particle particle, ArrangementParticle arrangement, double radiusParticle, int amountParticle) {
        this.particle = particle;
        this.arrangement = arrangement;
        this.radiusParticle = radiusParticle;
        this.amountParticle = amountParticle;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            Location location = ent.getLocation();
            ParticleUtil.play(location, particle, arrangement, radiusParticle, amountParticle, Direction.XZ);

        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        List<String> lore = new ArrayList<>();
        return lore;
    }
}
