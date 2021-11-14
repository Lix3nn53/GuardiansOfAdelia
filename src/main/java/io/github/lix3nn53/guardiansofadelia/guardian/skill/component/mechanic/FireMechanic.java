package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class FireMechanic extends MechanicComponent {

    private final List<Integer> ticks;

    public FireMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("ticks")) {
            configLoadError("ticks");
        }

        this.ticks = configurationSection.getIntegerList("ticks");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (LivingEntity target : targets) {
            int newTicks = ticks.get(skillLevel - 1) <= 0 ? 0 : Math.max(ticks.get(skillLevel - 1), target.getFireTicks());

            target.setFireTicks(newTicks);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.RED + "Burn duration: " + (ticks.get(skillLevel) / 20));
        } else if (skillLevel == ticks.size()) {
            additions.add(ChatPalette.RED + "Burn duration: " + (ticks.get(skillLevel - 1) / 20));
        } else {
            additions.add(ChatPalette.RED + "Burn duration: " + (ticks.get(skillLevel - 1) / 20) + " -> " + (ticks.get(skillLevel) / 20));
        }
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
