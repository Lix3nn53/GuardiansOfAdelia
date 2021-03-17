package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class ValueAddMechanic extends MechanicComponent {

    private final String key;
    private final List<Integer> valueToAdd;

    public ValueAddMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        if (!configurationSection.contains("valueToAdd")) {
            configLoadError("valueToAdd");
        }

        this.key = configurationSection.getString("key");
        this.valueToAdd = configurationSection.getIntegerList("valueToAdd");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter) {
        if (targets.isEmpty()) return false;

        int value = valueToAdd.get(skillLevel - 1);
        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                Player player = (Player) ent;
                player.sendMessage("add +" + value);
            }
            SkillDataManager.addValue(ent, key, value);
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(additions, skillLevel);
    }
}
