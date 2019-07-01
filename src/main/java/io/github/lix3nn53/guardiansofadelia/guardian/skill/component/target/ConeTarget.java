package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class ConeTarget extends TargetComponent {

    private final double angle;
    private final double range;

    public ConeTarget(boolean allies, boolean enemy, boolean self, int max, double angle, double range) {
        super(allies, enemy, self, max);
        this.angle = angle;
        this.range = range;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {

        List<LivingEntity> cone = new ArrayList<>();

        for (LivingEntity target : targets) {
            List<LivingEntity> coneTargets = TargetHelper.getConeTargets(target, angle, range);
            coneTargets = determineTargets(caster, coneTargets);
            cone.addAll(coneTargets);
        }

        if (cone.isEmpty()) return false;

        return executeChildren(caster, skillLevel, cone, castKey);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Cone range: " + range);
        return lore;
    }
}
