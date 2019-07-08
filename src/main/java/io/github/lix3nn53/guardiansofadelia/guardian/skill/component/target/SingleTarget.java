package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
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
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {

        List<LivingEntity> single = new ArrayList<>();

        for (LivingEntity target : targets) {
            List<LivingEntity> singleTargets = TargetHelper.getLivingTargets(target, range.get(skillLevel - 1), tolerance);
            singleTargets = determineTargets(caster, singleTargets);
            single.addAll(singleTargets);
        }

        if (single.isEmpty()) return false;

        return executeChildren(caster, skillLevel, single, castKey);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Cone range: " + range);
        return lore;
    }
}
