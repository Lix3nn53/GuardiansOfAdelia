package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class EntityTypeCondition extends ConditionComponent {

    private final EntityType entityType;

    public EntityTypeCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("entityType")) {
            configLoadError("entityType");
        }

        this.entityType = EntityType.valueOf(configurationSection.getString("entityType"));
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            if (!target.getType().equals(entityType)) continue;

            success = executeChildren(caster, skillLevel, Collections.singletonList(target), castCounter, skillIndex) || success;
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
