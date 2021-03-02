package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.particles;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleArrangementLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementWithRadius;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ParticleArrangement;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleMechanic extends MechanicComponent {

    private final ParticleArrangement particleArrangement;
    private final List<Double> radiusParticle;

    private final double forward;
    private final double upward;
    private final double right;

    private final boolean rotation;
    private final boolean rotationCenterEye;

    public ParticleMechanic(ConfigurationSection configurationSection) {
        ConfigurationSection particle = configurationSection.getConfigurationSection("particle");
        this.particleArrangement = ParticleArrangementLoader.load(particle);

        if (particleArrangement instanceof ArrangementWithRadius) {
            this.radiusParticle = configurationSection.contains("radius") ? configurationSection.getDoubleList("radius") : null;
        } else {
            this.radiusParticle = null;
        }
        this.forward = configurationSection.contains("forward") ? configurationSection.getDouble("forward") : 0;
        this.upward = configurationSection.contains("upward") ? configurationSection.getDouble("upward") : 0.5;
        this.right = configurationSection.contains("right") ? configurationSection.getDouble("right") : 0;
        this.rotation = configurationSection.contains("rotation") && configurationSection.getBoolean("rotation");
        this.rotationCenterEye = configurationSection.contains("rotationCenterEye") && configurationSection.getBoolean("rotationCenterEye");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        double radius = radiusParticle != null ? radiusParticle.get(skillLevel - 1) : 0;
        for (LivingEntity ent : targets) {
            Location location = ent.getLocation();

            Vector dir = location.getDirection().setY(0).normalize();
            Vector side = dir.clone().crossProduct(UP);
            location.add(dir.multiply(forward)).add(0, upward, 0).add(side.multiply(right));

            if (radius > 0) {
                ArrangementWithRadius arrangementWithRadius = (ArrangementWithRadius) particleArrangement;
                arrangementWithRadius.setRadius(radius);
            }
            if (rotation) {
                Location eyeLocation = ent.getEyeLocation();
                float yaw = eyeLocation.getYaw();
                float pitch = eyeLocation.getPitch();

                if (rotationCenterEye) {
                    particleArrangement.play(eyeLocation, eyeLocation, yaw, pitch);
                } else {
                    particleArrangement.play(location, eyeLocation, yaw, pitch);
                }
            } else {
                particleArrangement.play(location);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
