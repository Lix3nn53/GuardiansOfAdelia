package io.github.lix3nn53.guardiansofadelia.guardian.skill.component.mechanic;

import io.github.lix3nn53.guardiansofadelia.commands.admin.CommandAdmin;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.SkillDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.skill.component.MechanicComponent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class ValueSetMechanic extends MechanicComponent {

    private final String key;
    private final List<Integer> values;

    public ValueSetMechanic(ConfigurationSection configurationSection) {
        super(!configurationSection.contains("addLore") || configurationSection.getBoolean("addLore"));

        if (!configurationSection.contains("key")) {
            configLoadError("key");
        }

        if (!configurationSection.contains("values")) {
            configLoadError("values");
        }

        this.key = configurationSection.getString("key");
        this.values = configurationSection.getIntegerList("values");
    }

    @Override
    public boolean execute(LivingEntity caster, int skillLevel, List<LivingEntity> targets, int castCounter, int skillIndex) {
        if (targets.isEmpty()) return false;

        for (LivingEntity ent : targets) {
            if (ent instanceof Player) {
                if (CommandAdmin.DEBUG_MODE) ent.sendMessage("SetValue: " + values.get(skillLevel - 1));
            }
            SkillDataManager.setValue(ent, key, values.get(skillLevel - 1));
        }

        return true;
    }

    @Override
    public List<String> getSkillLoreAdditions(String lang, List<String> additions, int skillLevel) {
        return getSkillLoreAdditionsOfChildren(lang, additions, skillLevel);
    }
}
