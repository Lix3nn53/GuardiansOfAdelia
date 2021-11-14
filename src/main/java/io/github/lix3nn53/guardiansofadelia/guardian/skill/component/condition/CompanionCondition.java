package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.creatures.pets.PetManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CompanionCondition extends ConditionComponent {

    private final Optional<String> mobCode;
    private final int minValue;
    private final int maxValue;

    public CompanionCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("minValue")) {
            configLoadError("minValue");
        }

        if (!configurationSection.contains("maxValue")) {
            configLoadError("maxValue");
        }

        if (configurationSection.contains("mobCode")) {
            this.mobCode = Optional.of(configurationSection.getString("mobCode"));
        } else {
            this.mobCode = Optional.empty();
        }

        this.minValue = configurationSection.getInt("minValue");
        this.maxValue = configurationSection.getInt("maxValue");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            if (!(target instanceof Player)) continue;
            Player owner = (Player) target;

            List<LivingEntity> companions = mobCode.map(s -> PetManager.getCompanions(owner, s)).orElseGet(() -> PetManager.getCompanions(owner));

            int value = companions.size();

            if (value >= minValue && value <= maxValue) {
                success = executeChildren(caster, skillLevel, Collections.singletonList(target), castCounter, skillIndex) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
