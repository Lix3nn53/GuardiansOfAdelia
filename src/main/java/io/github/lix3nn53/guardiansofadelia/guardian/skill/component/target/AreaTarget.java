package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.Nearby;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class AreaTarget extends TargetComponent {

    private final List<Double> radius;

    public AreaTarget(boolean allies, boolean enemy, boolean self, int max, List<Double> radius) {
        super(allies, enemy, self, max);
        this.radius = radius;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, String castKey) {

        List<LivingEntity> nearby = new ArrayList<>();

        for (LivingEntity target : targets) {
            List<LivingEntity> nearbyTarget = Nearby.getLivingNearby(target, radius.get(skillLevel - 1));
            nearbyTarget = determineTargets(caster, nearbyTarget);
            nearby.addAll(nearbyTarget);
        }

        if (nearby.isEmpty()) return false;

        return executeChildren(caster, skillLevel, nearby, castKey);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Radius: " + radius);
        return lore;
    }
}
