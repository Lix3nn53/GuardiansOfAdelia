package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.ChatColor;
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

    public SingleTarget(boolean allies, boolean enemy, boolean self, int max, List<Double> range, double tolerance) {
        super(allies, enemy, self, max);
        this.range = range;
        this.tolerance = tolerance;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> single = new ArrayList<>();

        for (LivingEntity target : targets) {
            LivingEntity singleTargets = TargetHelper.getLivingTarget(target, range.get(skillLevel - 1), tolerance);
            single.add(singleTargets);
        }

        if (single.isEmpty()) return false;

        single = determineTargets(caster, single);

        if (single.isEmpty()) return false;

        return executeChildren(caster, skillLevel, single, castCounter);
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
