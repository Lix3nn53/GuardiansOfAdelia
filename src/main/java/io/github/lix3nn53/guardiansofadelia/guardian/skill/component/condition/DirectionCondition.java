package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.target.TargetHelper;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class DirectionCondition extends ConditionComponent {

    private final boolean workWhenInFront;

    public DirectionCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("workWhenInFront")) {
            configLoadError("workWhenInFront");
        }

        this.workWhenInFront = configurationSection.getBoolean("workWhenInFront");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        String worldName = caster.getWorld().getName();
        for (LivingEntity target : targets) {
            World worldTarget = target.getLocation().getWorld();
            if (worldTarget == null) continue;

            if (!worldName.equals(worldTarget.getName())) continue;

            boolean inFront = TargetHelper.isInFront(target, caster);

            if (inFront == workWhenInFront) {
                success = executeChildren(caster, skillLevel, Collections.singletonList(target), castCounter) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
