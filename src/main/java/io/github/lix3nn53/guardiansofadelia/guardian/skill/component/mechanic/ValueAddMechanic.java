package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class ValueAddMechanic extends MechanicComponent {

    private final String key;
    private final int valueToAdd;

    public ValueAddMechanic(String key, int valueToAdd) {
        this.key = key;
        this.valueToAdd = valueToAdd;
    }

    public ValueAddMechanic(ConfigurationSection configurationSection) {
        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        if (!configurationSection.contains("valueToAdd")) {
            configLoadError("valueToAdd");
        }

        this.key = configurationSection.getString("key");
        this.valueToAdd = configurationSection.getInt("valueToAdd");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            SkillDataManager.addValue(ent, key, valueToAdd);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
