package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillBar;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class ChangeSkillLevelMechanic extends MechanicComponent {

    private final int index; // index of skill to get skill level of
    private final int value; // value to use if index == - 1

    public ChangeSkillLevelMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("index") && !configurationSection.contains("value")) {
            configLoadError("index or value");
        }

        if (configurationSection.contains("index")) {
            this.index = configurationSection.getInt("index");
        } else {
            index = -1;
        }

        if (configurationSection.contains("value")) {
            this.value = configurationSection.getInt("value");
        } else {
            value = 1;
        }
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        int newSkillLevel = value;
        if (index > -1) {
            if (caster instanceof Player) {
                Player player = (Player) caster;
                if (GuardianDataManager.hasGuardianData(player)) {
                    GuardianData guardianData = GuardianDataManager.getGuardianData(player);

                    if (guardianData.hasActiveCharacter()) {
                        RPGCharacter activeCharacter = guardianData.getActiveCharacter();
                        SkillBar skillBar = activeCharacter.getSkillBar();

                        newSkillLevel = skillBar.getCurrentSkillLevel(index);
                    }
                }
            }
        }

        return executeChildren(caster, newSkillLevel, targets, castCounter, skillIndex);
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {

        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
