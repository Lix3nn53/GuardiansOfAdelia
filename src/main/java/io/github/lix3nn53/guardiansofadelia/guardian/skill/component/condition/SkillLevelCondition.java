package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class SkillLevelCondition extends ConditionComponent {

    private final int skillIndex;
    private final int minValue;
    private final int maxValue;

    public SkillLevelCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("skillIndex")) {
            configLoadError("skillIndex");
        }

        if (!configurationSection.contains("minValue")) {
            configLoadError("minValue");
        }

        if (!configurationSection.contains("maxValue")) {
            configLoadError("maxValue");
        }

        this.skillIndex = configurationSection.getInt("skillIndex");
        this.minValue = configurationSection.getInt("minValue");
        this.maxValue = configurationSection.getInt("maxValue");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            if (!(target instanceof Player)) continue;
            Player player = (Player) target;

            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);

                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    SkillBar skillBar = activeCharacter.getSkillBar();
                    int value = skillBar.getInvestedSkillPoints(this.skillIndex);

                    if (value >= minValue && value <= maxValue) {
                        success = executeChildren(caster, skillLevel, targets, castCounter) || success;
                    }
                }
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
