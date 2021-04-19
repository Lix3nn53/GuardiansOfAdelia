package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.ParticleShapes;
import io.github.lix3nn53.guardiansofadelia.utilities.particle.arrangement.ArrangementSingle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class ConeTarget extends TargetComponent {

    private final List<Double> angleList;
    private final List<Double> rangeList;
    // PARTICLE
    private final int amount;
    private final int amounty;
    private final ArrangementSingle arrangementSingle;

    public ConeTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("angleList")) {
            configLoadError("angleList");
        }

        if (!configurationSection.contains("rangeList")) {
            configLoadError("rangeList");
        }

        this.angleList = configurationSection.getDoubleList("angleList");
        this.rangeList = configurationSection.getDoubleList("rangeList");

        // PARTICLE
        if (!configurationSection.contains("particle")) {
            configLoadError("particle");
        }

        ConfigurationSection particleSection = configurationSection.getConfigurationSection("particle");

        this.arrangementSingle = new ArrangementSingle(particleSection);
        this.amount = particleSection.getInt("amount");
        this.amounty = particleSection.getInt("amounty");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> cone = new ArrayList<>();
        double angle = angleList.get(skillLevel - 1);
        double range = rangeList.get(skillLevel - 1);
        for (LivingEntity target : targets) {
            List<LivingEntity> coneTargets = TargetHelper.getConeTargets(target, angle, range);

            Location location = target.getEyeLocation();
            float yaw = location.getYaw();
            float pitch = location.getPitch() + 90;
            ParticleShapes.drawCone(location, arrangementSingle, range, amount, amounty, angle, true, yaw, pitch, new Vector());

            cone.addAll(coneTargets);
        }

        if (cone.isEmpty()) return true;

        cone = determineTargets(caster, cone);

        if (cone.isEmpty()) return true;

        List<LivingEntity> targetsNew = new ArrayList<>();
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                cone.addAll(targets);
                targetsNew = cone;
            } else {
                targetsNew.addAll(targets);
                targetsNew.addAll(cone);
            }
        } else {
            targetsNew = cone;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Cone Area: " + rangeList.get(skillLevel));
        } else if (skillLevel == rangeList.size()) {
            additions.add(ChatColor.YELLOW + "Cone Area: " + rangeList.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Cone Area: " + rangeList.get(skillLevel - 1)
                    + " -> " + rangeList.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
