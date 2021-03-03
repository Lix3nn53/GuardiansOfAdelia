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

    private final boolean centerEye;
    private final boolean rotation;
    private final boolean rotationMatchEye;

    private final float yaw;
    private final float pitch;

    private final double offsetx;
    private final double offsety;
    private final double offsetz;

    public ParticleMechanic(ConfigurationSection configurationSection) {
        ConfigurationSection particle = configurationSection.getConfigurationSection("particle");
        this.particleArrangement = ParticleArrangementLoader.load(particle);

        if (particleArrangement instanceof ArrangementWithRadius) {
            this.radiusParticle = configurationSection.contains("radius") ? configurationSection.getDoubleList("radius") : null;
        } else {
            this.radiusParticle = null;
        }
        this.forward = configurationSection.contains("forward") ? configurationSection.getDouble("forward") : 0;
        this.upward = configurationSection.contains("upward") ? configurationSection.getDouble("upward") : 0;
        this.right = configurationSection.contains("right") ? configurationSection.getDouble("right") : 0;

        this.centerEye = configurationSection.contains("centerEye") && configurationSection.getBoolean("centerEye");
        this.rotation = configurationSection.contains("rotation") && configurationSection.getBoolean("rotation");
        this.rotationMatchEye = configurationSection.contains("rotationMatchEye") && configurationSection.getBoolean("rotationMatchEye");

        this.yaw = configurationSection.contains("yaw") ? (float) configurationSection.getDouble("yaw") : 0;
        this.pitch = configurationSection.contains("pitch") ? (float) configurationSection.getDouble("pitch") : 0;

        this.offsetx = configurationSection.contains("offsetx") ? configurationSection.getDouble("offsetx") : 0;
        this.offsety = configurationSection.contains("offsety") ? configurationSection.getDouble("offsety") : 0;
        this.offsetz = configurationSection.contains("offsetz") ? configurationSection.getDouble("offsetz") : 0;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        double radius = radiusParticle != null ? radiusParticle.get(skillLevel - 1) : 0;
        for (LivingEntity ent : targets) {
            Location location = centerEye ? ent.getEyeLocation() : ent.getLocation();

            Vector dir = location.getDirection().normalize();
            Vector side = dir.clone().crossProduct(new Vector(0, 1, 0));
            Vector upwardly = dir.clone().crossProduct(side);
            location.add(dir.multiply(forward)).subtract(upwardly.multiply(upward)).add(side.multiply(right));

            if (radius > 0) {
                ArrangementWithRadius arrangementWithRadius = (ArrangementWithRadius) particleArrangement;
                arrangementWithRadius.setRadius(radius);
            }

            if (rotation) {
                Location eyeLocation = ent.getEyeLocation();
                float yaw = rotationMatchEye ? eyeLocation.getYaw() : this.yaw;
                float pitch = rotationMatchEye ? eyeLocation.getPitch() : this.pitch;

                particleArrangement.play(location, new Vector(offsetx, offsety, offsetz), yaw, pitch);
            } else {
                particleArrangement.play(location, new Vector(offsetx, offsety, offsetz));
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
