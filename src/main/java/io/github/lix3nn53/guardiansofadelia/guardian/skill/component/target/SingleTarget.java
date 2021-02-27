package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class SingleTarget extends TargetComponent {

    private final List<Double> range;
    private final double tolerance;

    public SingleTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("ranges")) {
            configLoadError("ranges");
        }

        if (!configurationSection.contains("tolerance")) {
            configLoadError("tolerance");
        }

        this.range = configurationSection.getDoubleList("ranges");
        this.tolerance = configurationSection.getDouble("tolerance");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> singles = new ArrayList<>();

        for (LivingEntity target : targets) {
            LivingEntity singleTargets = TargetHelper.getLivingTarget(target, range.get(skillLevel - 1), tolerance);
            singles.add(singleTargets);
        }

        if (singles.isEmpty()) return false;

        singles = determineTargets(caster, singles);

        if (singles.isEmpty()) return false;

        List<LivingEntity> targetsNew;
        if (super.isKeepCurrent()) {
            if (super.isAddToBeginning()) {
                singles.addAll(targets);
                targetsNew = singles;
            } else {
                targetsNew = targets;
                targetsNew.addAll(singles);
            }
        } else {
            targetsNew = singles;
        }

        return executeChildren(caster, skillLevel, targetsNew, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel));
        } else if (skillLevel == range.size()) {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Range: " + range.get(skillLevel - 1) + " -> " + range.get(skillLevel));
        }
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
