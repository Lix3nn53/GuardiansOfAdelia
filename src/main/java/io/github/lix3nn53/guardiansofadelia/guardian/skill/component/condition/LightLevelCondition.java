package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.condition;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.ConditionComponent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;

import java.util.Collections;
import java.util.List;

public class LightLevelCondition extends ConditionComponent {

    private final int min;
    private final int max;

    public LightLevelCondition(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("min")) {
            configLoadError("min");
        }

        if (!configurationSection.contains("max")) {
            configLoadError("max");
        }

        this.min = configurationSection.getInt("min");
        this.max = configurationSection.getInt("max");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        boolean success = false;
        for (LivingEntity target : targets) {
            Block block = target.getLocation().getBlock();
            byte lightLevel = block.getLightLevel();

            Bukkit.getPlayer("Lix3nn").sendMessage("Light level: " + lightLevel);

            if (lightLevel > min && lightLevel <= max) {
                success = executeChildren(caster, skillLevel, Collections.singletonList(target), castCounter, skillIndex) || success;
            }
        }

        return success;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
