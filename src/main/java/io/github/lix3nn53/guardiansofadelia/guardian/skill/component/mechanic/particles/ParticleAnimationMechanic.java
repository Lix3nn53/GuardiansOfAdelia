package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.particles;

import io.github.lix3nn53.guardiansofadelia.GuardiansOfAdelia;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleArrangementLoader;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ParticleArrangement;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleAnimationMechanic extends MechanicComponent {

    private final ParticleArrangement particleArrangement;

    private final double forward;
    private final double upward;
    private final double right;

    //animation
    private final long frequency;
    private final List<Integer> repeatAmount;

    public ParticleAnimationMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("frequency")) {
            configLoadError("frequency");
        }

        if (!configurationSection.contains("repeatAmount")) {
            configLoadError("repeatAmount");
        }

        ConfigurationSection particle = configurationSection.getConfigurationSection("particle");
        this.particleArrangement = ParticleArrangementLoader.load(particle);

        this.forward = configurationSection.contains("forward") ? configurationSection.getDouble("forward") : 0.5;
        this.upward = configurationSection.contains("upward") ? configurationSection.getDouble("upward") : 0.5;
        this.right = configurationSection.contains("right") ? configurationSection.getDouble("right") : 0.5;

        this.frequency = configurationSection.getInt("frequency");
        this.repeatAmount = configurationSection.getIntegerList("repeatAmount");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            new BukkitRunnable() {

                int counter;

                @Override
                public void run() {
                    Location location = ent.getLocation();

                    Vector dir = location.getDirection().setY(0).normalize();
                    Vector side = dir.clone().crossProduct(UP);
                    location.add(dir.multiply(forward)).add(0, upward, 0).add(side.multiply(right));

                    particleArrangement.play(location);

                    counter++;
                    if (counter >= repeatAmount.get(skillLevel - 1)) {
                        cancel();
                    }
                }
            }.runTaskTimer(GuardiansOfAdelia.getInstance(), 0L, frequency);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
