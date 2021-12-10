package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.trigger;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.TriggerComponent;
import io.github.lix3nn53.guardiansofadelia.text.ChatPalette;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class AddPiercingToArrowShootFromCrossbowTrigger extends TriggerComponent {

    private final List<Integer> piercingLevel;

    LivingEntity caster;
    int skillLevel;

    public AddPiercingToArrowShootFromCrossbowTrigger(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("piercingLevels")) {
            configLoadError("piercingLevels");
        }

        this.piercingLevel = configurationSection.getIntegerList("piercingLevels");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        this.skillIndex = skillIndex;
        this.caster = caster;
        this.skillLevel = skillLevel;

        AddPiercingToArrowShootFromCrossbowTrigger crossbowTrigger = this;

        for (LivingEntity target : targets) {
            if (target instanceof Player) {
                TriggerListener.add((Player) target, crossbowTrigger);
            }
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        if (!this.addLore) return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);

        if (skillLevel == 0) {
            additions.add(ChatPalette.GOLD + "Pierce amount: " + piercingLevel.get(skillLevel));
        } else if (skillLevel == piercingLevel.size()) {
            additions.add(ChatPalette.GOLD + "Pierce amount: " + piercingLevel.get(skillLevel - 1));
        } else {
            additions.add(ChatPalette.GOLD + "Pierce amount: " + piercingLevel.get(skillLevel - 1) + " -> " + piercingLevel.get(skillLevel));
        }

        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }

    /**
     * The callback when player lands that applies child components
     */
    public void callback(Arrow arrow) {
        arrow.setPierceLevel(piercingLevel.get(skillLevel - 1));
    }
}
