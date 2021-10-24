package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic.statuseffect;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class StatusEffectMechanic extends MechanicComponent {

    private final List<StatusEffectType> statusEffectTypes = new ArrayList<>();
    private final List<Integer> durations;

    private final String multiplyDurationValue;

    public StatusEffectMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("durations")) {
            configLoadError("durations");
        }

        this.durations = configurationSection.getIntegerList("durations");
        List<String> statusEffectTypesStr = configurationSection.getStringList("statusEffectTypes");
        for (String str : statusEffectTypesStr) {
            StatusEffectType byName = StatusEffectType.valueOf(str);
            this.statusEffectTypes.add(byName);
        }

        this.multiplyDurationValue = configurationSection.contains("multiplyDurationValue") ? configurationSection.getString("multiplyDurationValue") : null;
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int duration = durations.get(skillLevel - 1);

        for (LivingEntity target : targets) {
            int durationCurrent = duration;

            if (multiplyDurationValue != null) {
                int value = SkillDataManager.getValue(target, multiplyDurationValue);
                if (value > 0) {
                    durationCurrent *= value;
                }
            }

            for (StatusEffectType effectType : statusEffectTypes) {
                StatusEffectManager.addStatus(target, effectType, durationCurrent);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(additions, skillLevel);

        for (StatusEffectType type : statusEffectTypes) {
            if (skillLevel == 0) {
                additions.add(ChatPalette.RED + type.toString() + " duration: " + (int) ((durations.get(skillLevel) / 20) + 0.5) + " seconds");
            } else if (skillLevel == durations.size()) {
                additions.add(ChatPalette.RED + type.toString() + " duration: " + (int) ((durations.get(skillLevel - 1) / 20) + 0.5) + " seconds");
            } else {
                additions.add(ChatPalette.RED + type.toString() + " duration: " + (int) ((durations.get(skillLevel - 1) / 20) + 0.5) + " seconds -> " + (int) ((durations.get(skillLevel) / 20) + 0.5));
            }
        }

        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
