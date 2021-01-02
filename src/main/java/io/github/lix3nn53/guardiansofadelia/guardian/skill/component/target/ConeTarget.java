package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TargetComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Applies child components to the closest all nearby entities around
 * each of the current targets.
 */
public class ConeTarget extends TargetComponent {

    private final List<Double> angleList;
    private final List<Double> rangeList;

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
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        List<LivingEntity> cone = new ArrayList<>();

        for (LivingEntity target : targets) {
            List<LivingEntity> coneTargets = TargetHelper.getConeTargets(target, angleList.get(skillLevel - 1), rangeList.get(skillLevel - 1));
            cone.addAll(coneTargets);
        }

        if (cone.isEmpty()) return false;

        cone = determineTargets(caster, cone);

        if (cone.isEmpty()) return false;

        if (super.isKeepCurrent()) {
            if (super.isaddToBeginning()) {
                Collections.reverse(cone);
                for (LivingEntity single : cone) {
                    targets.add(0, single);
                }
            } else {
                targets.addAll(cone);
            }
        } else {
            targets = cone;
        }

        return executeChildren(caster, skillLevel, targets, castCounter);
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        additions.add(ChatColor.YELLOW + "Cone range: " + rangeList);
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
