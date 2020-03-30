package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.Nearby;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
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

    public AreaTarget(ConfigurationSection configurationSection) {
        super(configurationSection);

        if (!configurationSection.contains("radiusList")) {
            configLoadError("radiusList");
        }

        this.radius = configurationSection.getDoubleList("radiusList");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> nearby = new ArrayList<>();

        for (LivingEntity target : targets) {
            List<LivingEntity> nearbyTarget = Nearby.getLivingNearby(target, radius.get(skillLevel - 1));
            nearby.addAll(nearbyTarget);
        }

        if (nearby.isEmpty()) return false;

        nearby = determineTargets(caster, nearby);

        if (nearby.isEmpty()) return false;

        return executeChildren(caster, skillLevel, nearby, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (skillLevel == 0) {
            additions.add(ChatColor.YELLOW + "Radius: " + radius.get(skillLevel));
        } else if (skillLevel == radius.size()) {
            additions.add(ChatColor.YELLOW + "Radius: " + radius.get(skillLevel - 1));
        } else {
            additions.add(ChatColor.YELLOW + "Radius: " + radius.get(skillLevel - 1) + " -> " + radius.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
