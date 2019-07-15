package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.Nearby;
import org.bukkit.ChatColor;
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
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets) {

        List<LivingEntity> nearby = new ArrayList<>();

        for (LivingEntity target : targets) {
            List<LivingEntity> nearbyTarget = Nearby.getLivingNearby(target, radius.get(skillLevel - 1));
            nearby.addAll(nearbyTarget);
        }

        nearby = determineTargets(caster, nearby);

        if (nearby.isEmpty()) return false;

        return executeChildren(caster, skillLevel, nearby);
    }

    @Override
    public List<String> getSkillLoreAdditions(int skillLevel) {
        ArrayList<String> lore = new ArrayList<>();
        if (skillLevel == 0 || skillLevel == radius.size()) {
            lore.add(ChatColor.YELLOW + "Radius: " + radius.get(skillLevel));
        } else {
            lore.add(ChatColor.YELLOW + "Radius: " + radius.get(skillLevel - 1) + " -> " + radius.get(skillLevel));
        }

        return lore;
    }
}
