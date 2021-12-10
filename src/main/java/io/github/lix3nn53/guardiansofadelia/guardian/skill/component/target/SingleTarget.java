package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementSingle;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class SingleTarget extends TargetComponent {

    private final List<Float> range;
    private final float tolerance;
    // PARTICLE
    private final float gap;
    private final ArrangementSingle arrangementSingle;

    public SingleTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("ranges")) {
            configLoadError("ranges");
        }

        if (!configurationSection.contains("tolerance")) {
            configLoadError("tolerance");
        }

        this.range = configurationSection.getFloatList("ranges");
        this.tolerance = (float) configurationSection.getDouble("tolerance");

        // PARTICLE
        if (!configurationSection.contains("particle")) {
            configLoadError("particle");
        }

        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");

        this.arrangementSingle = new ArrangementSingle(particleSection);
        this.gap = particleSection.contains("gap") ? (float) particleSection.getDouble("gap") : 0;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> singles = new ArrayList<>();

        for (LivingEntity target : targets) {
            LivingEntity singleTarget = TargetHelper.getLookingTarget(target, range.get(skillLevel - 1), tolerance);

            if (singleTarget != null) {
                singles.add(singleTarget);

                Location eyeLocation = target.getEyeLocation();

                float v = (float) (singleTarget.getHeight()) / 2f;
                Location targetLocation = singleTarget.getLocation().add(0, v, 0);

                ParticleShapes.drawLineBetween(eyeLocation.getWorld(), eyeLocation.toVector(), arrangementSingle, targetLocation.toVector(), gap);
            }
        }

        if (singles.isEmpty()) return false;

        singles = determineTargets(caster, singles);

        if (singles.isEmpty()) return false;

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                singles.addAll(targets);
                targetsNew = singles;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(singles);
            }
        } else {
            targetsNew = singles;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.YELLOW + "Range: " + range.get(skillLevel));
        } else if (skillLevel == range.size()) {
            additions.add(ChatPalette.YELLOW + "Range: " + range.get(skillLevel - 1));
        } else {
            additions.add(ChatPalette.YELLOW + "Range: " + range.get(skillLevel - 1) + " -> " + range.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
